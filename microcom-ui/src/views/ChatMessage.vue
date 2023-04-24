<template>
  <div class="container" ref="sendMessage">
    <div class="q-pa-md row justify-center">
      <div style="width: 100%">
        <q-chat-message label="Sunday, 19th" />
        <div v-for="message in messages" :key="message.id">
          <q-chat-message :name="message.name" :avatar="message.avatar" :text="message.text" :sent="message.sent"
            :stamp="utils.parseDateToPast(message.stamp)" />
        </div>
      </div>
    </div>
    <q-footer>
        <q-toolbar class="bg-grey-3 text-black row">
          <q-btn round flat icon="insert_emoticon" class="q-mr-sm" />
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
</template>
  
<script setup>
import utils from "@/js/utils/utils.js"
import { ref, defineProps, toRefs } from "vue"
import { message } from "ant-design-vue"
import chat from "@/js/api/chat"

const props = defineProps({
  messages: {
    type: Object,
    required: true
  }
})


const inputMessage = ref('')
const messages = toRefs(props.messages)
const user = JSON.parse(sessionStorage.getItem("user")??"")

const commitMessage = () => {
  if (inputMessage.value == null || inputMessage.value.trim() == '') {
      message.warn('请输入消息内容')
      return;
  }else{
    // 发送消息
    const messageInfo = { 
      name: user.name, 
      avatar: "https://cdn.quasar.dev/img/avatar.png", 
      text: [inputMessage.value], 
      sent: true, 
      stamp: new Date().getTime()
    }
    const message = {
      from: user.name,
      text: inputMessage.value,
      to: props.currentChatId,
      time: new Date().getTime()
    }
    chat.pushId(message)
    messages.value.push(messageInfo)
    inputMessage.value = ''
  }
}

// const //推送消息
//   send() {
//       if (this.form.message == null || this.form.message.trim() == '') {
//           this._message('请输入消息内容', 'warning')
//           return;
//       }
//       if (!this.current_window_id) {
//           this.websocket.send(this.form.message.replace(/[\r\n]/g,""))
//           this.initCommonMessage()
//       } else {
//           let data = {
//               message: this.form.message,
//               from: this.user
//           }
//           this.$http.post(api.pushId(this.current_window_id), JSON.stringify(data)).then(response => {
//               if (response.body.code == 200) {
//                   this.initSelfMessage()
//                   this.clean()
//                   this._notify('提醒', '消息推送成功', 'success')
//               } else {
//                   this._notify('提醒', response.body.msg, 'error')
//               }
//           })
//       }
//       this.scroll()
//   },

</script>
  
<style scoped>
.container {
  width: 100%;
  height: 100%;
}
</style>