<template>
  <div>
    <a-page-header class="demo-page-header" style="border: 1px solid rgb(235, 237, 240)" :title=details.title
      :sub-title=details.description @back="back">
      <a-descriptions size="small" :column="3">
        <a-descriptions-item label="作者">{{ details.author }}</a-descriptions-item>
        <!-- <a-descriptions-item label="社交">
          <a>{{ details.createTime }}</a>
        </a-descriptions-item> -->
        <a-descriptions-item label="发布时间">{{ utils.parseDate(details.createTime) }}</a-descriptions-item>
        <a-descriptions-item label="关键词">
          <a-tag v-for="item of tags" :key="item.color" class="item-tag" :color="item.color">
            <router-link @click.prevent="showContentBykeyword(item.tag)" to="/">
              {{ item.tag }}
            </router-link>
          </a-tag>
        </a-descriptions-item>
      </a-descriptions>
    </a-page-header>
  </div>
</template>
  
<style scoped>
.demo-page-header :deep(tr:last-child td) {
  padding-bottom: 0;
}
</style>

<script setup>
import { reactive, onMounted, onUnmounted, defineProps } from 'vue'
import { useRouter } from 'vue-router'
import utils from '@/js/utils/utils'
const props = defineProps({
  details: {
    type: Object,
    default: () => {
      return {}
    }
  }
})

const router = useRouter()
const colors = ['pink', 'red', 'orange', 'yellow', 'green', 'cyan', 'blue', 'purple'];
const tags = reactive([
])

const initTags = () => {
  if (props.details.label !== null && props.details.label !== '' && props.details.label !== undefined) {
    var keywords = props.details.label.split(',') ?? false
    if (keywords.length > 0) {
      for (let i = 0; i < keywords.length; i++) {
        let j = Math.floor(Math.random() * 7);
        tags.push({ color: colors[j], tag: keywords[i] })
      }
    }
  }
}

const destroyTags = () => {
  tags.splice(0, tags.length)
}

const back = () => {
  router.go(-1)
}

onMounted(() => {
  initTags()
})

onUnmounted(() => {
  destroyTags()
})

</script>