<template>
  <div class="WAL bg-grey-4" :style="style">
    <q-layout view="lHh Lpr lFf" class="WAL__layout shadow-3" container>
      <q-header elevated>
        <q-toolbar class="bg-grey-3 text-black">
          <q-btn round flat icon="keyboard_arrow_left" class="WAL__drawer-open q-mr-sm" @click="toggleLeftDrawer" />

          <q-btn round flat>
            <q-avatar>
              <img :src="currentConversation.avatar">
            </q-avatar>
          </q-btn>

          <span class="q-subtitle-1 q-pl-md">
            {{ currentConversation.person }}
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

          <q-btn round flat icon="close" class="WAL__drawer-close" @click="toggleLeftDrawer" />
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
                    <img :src="conversation.avatar">
                  </q-avatar>
                </q-item-section>

                <q-item-section>
                  <q-item-label lines="1">
                    {{ conversation.person }}
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
        <chat-message ref="sendMessage" :currentChatId="currentChatId" />
      </q-page-container>


    </q-layout>
  </div>
</template>
  
<script setup>
import ChatMessage from '@/views/ChatMessage.vue'
import { useQuasar } from 'quasar'
import { ref, computed, onBeforeMount, onMounted } from 'vue'
import axios from 'axios'
import mockData from '@/js/data/mockData'
import api from '@/js/api/chat'

const $q = useQuasar()

const leftDrawerOpen = ref(false)
//搜索关键字
const search = ref('')
//用户列表
const conversations = ref([])
//当前激活窗口 : 上方显示的聊天信息
const currentConversationIndex = ref(0)
//当前聊天窗口
const currentChatId = ref(0)
const sendMessage = ref()

const currentConversation = computed(() => {
  return conversations.value[currentConversationIndex.value]
})

const style = computed(() => ({
  height: $q.screen.height - 80 + 'px'
}))

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

const setCurrentConversation = (index, chatId) => {
  currentConversationIndex.value = index
  currentChatId.value = chatId
}

const initUser = () => {
  //加载当前用户信息
  axios.get(api.getUser(this.form.id)).then(response => {
    this.user = response.body.data
  })

  //加载在线用户列表
  this.$http.get(api.getOnline()).then(response => {
    let data = response.body.data;
    if (data.length > 0) {
      this.userList = data;
      this.online = this.userList.length
    }
  })
}

const initWebSocket = () => {
  let $this = this;
  this.websocket = new WebSocket(api.websocket(this.form.id))
  //链接发送错误时调用
  this.websocket.onerror = function () {
    $this._notify('提醒', 'WebSocket链接错误', 'error')
  }
  //链接成功时调用
  this.websocket.onopen = function () {
    $this._notify('提醒', 'WebSocket链接成功', 'success')
  }
  //接收到消息时回调
  this.websocket.onmessage = function (event) {
    $this.clean()
    let entity = JSON.parse(event.data);

    //上线提醒
    if (entity.data == undefined) {
      $this.initUser()
      $this._notify('消息', entity.msg, 'info')
      $this.scroll()
      return;
    }

    //消息接收
    let data = JSON.parse(event.data).data
    if (data.to != undefined) {
      //单个窗口发送，仅推送到指定的窗口
      if (data.from.id == $this.current_window_id) {
        $this.messageList.push(data)
      }
    } else {
      //群发，推送到官方群组窗口
      $this.messageList.push(data)
    }
    $this.scroll()
  }
  //链接关闭时调用
  this.websocket.onclose = function () {
    $this._notify('提醒', 'WebSocket链接关闭', 'info')
  }
}

onBeforeMount(() => {
  conversations.value = mockData.getConversations()
})

onMounted(() => {
  initUser()
  initWebSocket()
})
</script>
  
<style lang="sass">
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