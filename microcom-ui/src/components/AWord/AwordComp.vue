<template>
    <div class="q-pa-md">
    <q-card class="my-card">
      <q-parallax
        src="https://cdn.quasar.dev/img/parallax1.jpg"
        :height="150"
        :width="300"
      />

      <q-card-section>
        <div class="text-subtitle2">{{AWord.data}}</div>
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup>
import {reactive,onMounted } from 'vue'
import axios from 'axios'
const AWord = reactive({
    data:'白茶清欢无别事'
});

const getAWord = ()=>{
    console.log('getAWord')
    axios.get('https://api.uixsj.cn/hitokoto/get').then(res=>{
        AWord.data = res.data
    })
}

onMounted(()=>{
    getAWord()
})  
</script>
