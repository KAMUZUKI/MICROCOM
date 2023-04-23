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
          <q-input rounded outlined dense class="WAL__field col-grow q-mr-sm" bg-color="white" v-model="message"
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
import { ref, defineProps, onMounted, watch } from "vue"
import mockData from "@/js/data/mockData.js"
import axiosRequest from "@/js/api/axiosRequest"

const props = defineProps({
  currentChatId: {
    type: Number,
    required: true
  }
})

const messages = ref()
const message = ref('')
const user = JSON.parse(sessionStorage.getItem("user")??"")

const commitMessage = () => {
  if (message.value) {
    const messageInfo = { 
      name: user.name, 
      avatar: "https://cdn.quasar.dev/img/avatar.png", 
      text: [message.value], 
      sent: true, 
      stamp: new Date().getTime()
    }
    messages.value.push(messageInfo)
    axiosRequest("post",'',{
      chatId: props.currentChatId,
      message: messageInfo
    })
    message.value = ''
  }
}

onMounted(() => {
  messages.value = mockData.getChatMessage(1)
})

watch(() => props.currentChatId, (newVal) => {
  messages.value = mockData.getChatMessage(newVal)
})

</script>
  
<style scoped>
.container {
  width: 100%;
  height: 100%;
}
</style>