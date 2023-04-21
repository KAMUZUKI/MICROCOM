<template>
    <a-result status="404" title="404" sub-title="Sorry, the page you visited does not exist.">
      <template #extra>
        <a-button type="primary" @click="back">Back Home</a-button>
      </template>
    </a-result>
  </template>

 <script>
 import { defineComponent } from 'vue'
 import { useRouter } from 'vue-router'
 export default defineComponent({
    setup() {
        const router = useRouter()
        const back = ()=>{
            router.push({path:'/'})
        }

        return {
            back
        }
    },
 })
 </script>
 