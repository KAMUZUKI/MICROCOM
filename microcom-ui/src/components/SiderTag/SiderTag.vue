<template>
  <a-card class="card" hoverable>
    <div>
      <a-tag style="margin-top:10px" v-for="item of tags" :key="item.color" class="item-tag" :color="item.color">
        <router-link @click.prevent="showContentBykeyword(item.tag)" to="/">
          {{ item.tag }}
        </router-link>
      </a-tag>
    </div>
  </a-card>
</template>

<script>
import { defineComponent, reactive,ref,onMounted,onUnmounted } from 'vue'
export default defineComponent({
  name: 'SiderTag',
  setup(props) {

    const colors = ['pink', 'red', 'orange', 'yellow', 'green', 'cyan', 'blue', 'purple']
    const items = ref([])
    const tags = reactive([])

    const initTags = () => {
      items.value = JSON.parse(sessionStorage.getItem('keywords'))?? []
      for (let i = 0; i < 30; i++) {
        let j = Math.floor(Math.random() * 7);
        if(items.value[i]!==undefined&&items.value[i]!==''){
          tags.push({ color: colors[j], tag: items.value[i] })
        }
      }
    }

    const destroyTags = () => {
      tags.splice(0, tags.length)
    }

    onMounted(() => {
      setTimeout(() => {
        initTags()
      }, 200)
    })

    onUnmounted(() => {
      destroyTags()
    })

    const showContentBykeyword = (type) => {
      props.showContentByKeyword(type);
    }
    return {
      tags,
      showContentBykeyword
    }
  },
  props: {
    showContentByKeyword: {
      type: Function,
    }
  },
})
</script>

<style>
.card{
  width: 300px;
  background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
  border-radius: 10px;
  box-shadow:  19px 19px 37px #bebebe,
             -19px -19px 37px #ffffff;
}
</style>