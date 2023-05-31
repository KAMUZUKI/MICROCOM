<template>
  <div class="WAL bg-grey-4" :style="style">
    <q-layout view="lHh Lpr lFf" class="WAL__layout shadow-3" container>
      <q-header elevated>
        <q-toolbar class="bg-grey-3 text-black">
          <q-btn round flat icon="keyboard_arrow_left" class="WAL__drawer-open q-mr-sm" @click="toggleLeftDrawer" />

          <q-btn round flat>
            <q-avatar>
              <img :src=currentConversation.head>
            </q-avatar>
          </q-btn>

          <span class="q-subtitle-1 q-pl-md">
            {{ currentConversation.name }}
          </span>

          <q-space />

          <q-btn round flat icon="search" />
          <q-btn round flat>
            <q-icon name="attachment" class="rotate-135" />
          </q-btn>
          <q-btn round flat icon="more_vert">
            <q-menu auto-close :offset="[110, 0]">
              <q-list style="min-width: 150px">
                <q-item clickable>
                  <q-item-section>Contact data</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>Block</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>Select messages</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>Silence</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>Clear messages</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>Erase messages</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </q-toolbar>
      </q-header>

      <q-drawer v-model="leftDrawerOpen" show-if-above bordered :breakpoint="690">
        <q-toolbar class="bg-grey-3">
          <h5>聊天</h5>
          <q-space />

          <q-btn round flat icon="message" />

          <q-btn round flat icon="close" class="WAL__drawer-close" @click="freshOnline()" />
        </q-toolbar>

        <q-toolbar class="bg-grey-2">
          <q-input rounded outlined dense class="WAL__field full-width" bg-color="white" v-model="search"
            placeholder="Search or start a new conversation">
            <template v-slot:prepend>
              <q-icon name="search" />
            </template>
          </q-input>
        </q-toolbar>

        <q-scroll-area style="height: calc(100% - 100px)">
          <q-list>
            <div v-for="(conversation, index) in conversations" :key="conversation.id">
              <q-item clickable v-ripple @click="setCurrentConversation(index, conversation.id)">
                <q-item-section avatar>
                  <q-avatar>
                    <img :src="conversation.head">
                  </q-avatar>
                </q-item-section>

                <q-item-section>
                  <q-item-label lines="1">
                    {{ conversation.name }}
                  </q-item-label>
                  <q-item-label class="conversation__summary" caption>
                    <q-icon name="check" v-if="conversation.sent" />
                    <q-icon name="not_interested" v-if="conversation.deleted" />
                    {{ conversation.caption }}
                  </q-item-label>
                </q-item-section>

                <q-item-section side>
                  <q-item-label caption>
                    {{ conversation.time }}
                  </q-item-label>
                  <q-icon name="keyboard_arrow_down" />
                </q-item-section>
              </q-item>
            </div>
          </q-list>
        </q-scroll-area>
      </q-drawer>

      <q-page-container class="bg-grey-2">
        <div class="message-container">
          <div class="q-pa-md row justify-center">
            <div style="width: 100%" >
              <q-chat-message label="Sunday, 19th" />
              <div v-for="message in messages" :key="message.id">
                <q-chat-message :name="message.from.name" :avatar="message.from.head" :text="[message.message]" :sent="message.from.id==store.state.user.id"
                  :stamp="message.time" />
              </div>
            </div>
          </div>
          <q-footer>
            <q-toolbar class="bg-grey-3 text-black row">
              <V3Emoji class="emoji" v-model="newCommentText" size="mid" @click-emoji="appendText" :recent="true"
                fixPos="upright" />
              <q-input rounded outlined dense class="WAL__field col-grow q-mr-sm" bg-color="white" v-model="inputMessage"
                placeholder="Type a message" />
              <q-btn round flat @click="commitMessage()">
                <svg width="30" height="30" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M42 6L4 20.1383L24 24.0083L29.0052 44L42 6Z" stroke="#414141" stroke-width="4"
                    stroke-linejoin="round" />
                  <path d="M24.0083 24.0084L29.6651 18.3516" stroke="#414141" stroke-width="4" stroke-linecap="round"
                    stroke-linejoin="round" />
                </svg>
              </q-btn>
            </q-toolbar>
          </q-footer>
        </div>
      </q-page-container>
    </q-layout>
  </div>
</template>
  
<script setup>
import { useQuasar } from 'quasar'
import { ref, computed, onBeforeMount, onMounted, watch, onUnmounted, nextTick } from 'vue'
import { useStore } from 'vuex'
import { message } from 'ant-design-vue'
import V3Emoji from 'vue3-emoji'
import 'vue3-emoji/dist/style.css'
import mockData from '@/js/data/mockData'
import chat from '@/js/api/chat'
import utils from '@/js/utils/utils'

const $q = useQuasar()
//搜索关键字
const search = ref('')
//用户列表
const conversations = ref([])
//当前激活窗口 : 上方显示的聊天信息
const currentConversationIndex = ref(0)
//当前聊天窗口
const currentChatId = ref(0)
const store = useStore()
const messages = ref()
const inputMessage = ref('')
var websocket = null

const appendText = (value) => {
  inputMessage.value += value;
}

const currentConversation = computed(() => {
  return conversations.value[currentConversationIndex.value]
})

const style = computed(() => ({
  height: $q.screen.height - 80 + 'px'
}))

const setCurrentConversation = (index, chatId) => {
  currentConversationIndex.value = index
  currentChatId.value = chatId
}

const commitMessage = () => {
  if (inputMessage.value == null || inputMessage.value.trim() == '') {
    message.warn('请输入消息内容')
    return;
  } else {
    // 发送消息
    const message = {
      from: {
          "id": store.state.user.id,
          "name": store.state.user.name,
          "head": store.state.user.head
      },
      message: inputMessage.value,
      to: {
          "id": currentConversation.value.id,
          "name": currentConversation.value.name,
          "head": currentConversation.value.head
      },
      time: new Date().getTime()
    }
    chat.pushId(message)
    message.time = utils.parseDateToPast(message.time)
    messages.value.push(message)
    inputMessage.value = ''
    messageScroll(100)
  }
}

const initUser = async () => {
  //加载在线用户列表
  nextTick(() => {
    freshOnline()
  })

  //加入聊天室
  if(store.state.user!=null&&typeof store.state.user!=undefined) {
    chat.join({
      "id": store.state.user.id,
      'name': store.state.user.name,
      'head': store.state.user.head
    })
  }
}

const freshOnline = async () => {
  //加载聊天记录
  conversations.value = await chat.getOnline()
}

const initWebSocket = () => {
  websocket = new WebSocket(chat.websocket(store.state.user.id))
  //链接发送错误时调用
  websocket.onerror = function () {
    message.error('WebSocket链接错误')
  }
  //链接成功时调用
  websocket.onopen = function () {
    message.success('WebSocket链接成功')
  }
  //接收到消息时回调
  websocket.onmessage = function (event) {
    let entity = JSON.parse(event.data);

    //上线提醒
    if (entity.data == undefined) {
      nextTick(()=>{
        freshOnline()
      })
      message.info(entity.msg)
      return;
    }

    //消息接收
    let data = JSON.parse(event.data).data
    if (data.to != undefined) {
      //单个窗口发送，仅推送到指定的窗口
      if (data.from.id == currentChatId.value) {
        messages.value.push(data)
        messageScroll(100)
      }
    } else {
      //群发，推送到官方群组窗口
      messages.value.push(data)
    }
  }
  //链接关闭时调用
  websocket.onclose = function () {
    message.warn('WebSocket链接关闭')
  }
}

const messageScroll = (height) => {
  //定时器
  nextTick(() => {
    let messageBox = document.getElementsByClassName("scroll")[1]
      if(height==undefined||height==null){
        let messageContainer = document.getElementsByClassName("message-container")[0].scrollHeight
        if (messageBox == undefined || messageBox == "null") {
          return
        }
        //滚动条滚动到底部
        messageBox.scrollTop = messageContainer
      }else{
        messageBox.scrollTop += height
      }
  }, 100)
}

onBeforeMount(() => {
  conversations.value = mockData.getConversations()
  initUser()
})

onMounted(() => {
  initWebSocket()
})

onUnmounted(() => {
  websocket.close()
  chat.logout(store.state.user.id)
})

watch(() => currentChatId.value,async (newVal) => {
  messages.value = await chat.getSelf(store.state.user.id,newVal)
  nextTick(() => {
    messageScroll()
  });
})
</script>
  
<style lang="sass">
  .emoji 
      height: 30px
      width: 30px
      bottom: 5px
  

  .WAL
    width: 100%
    height: 100%
    padding-top: 20px
    padding-bottom: 20px
  
    &:before
      content: ''
      height: 127px
      position: fixed
      top: 0
      width: 100%
      background-color: #009688
  
    &__layout
      margin: 0 auto
      height: 100%
      width: 90%
      max-width: 950px
      border-radius: 5px
  
    &__field.q-field--outlined .q-field__control:before
      border: none
  
    .q-drawer--standard
      .WAL__drawer-close
        display: none
  
  @media (max-width: 850px)
    .WAL
      padding: 0
      &__layout
        width: 100%
        border-radius: 0
  
  @media (min-width: 691px)
    .WAL
      &__drawer-open
        display: none
  
  .conversation__summary
    margin-top: 4px
  
  .conversation__more
    margin-top: 0!important
    font-size: 1.4rem
  </style>