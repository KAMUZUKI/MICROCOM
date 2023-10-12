<template>
  <div style="text-align:left;max-width: 1000px;" v-if="isShow" :key='indexKey'>
    <PageHeader :details="details"></PageHeader>
    <v-md-preview :text="details.content"></v-md-preview>
    <a-anchor-link href="#comment" title="评论" />
    <CommentComponent :articleId="details.id"></CommentComponent>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted, watch } from "vue"
import { useStore } from 'vuex' // 引入useStore 方法
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import CommentComponent from "@/components/Comment/CommentComponent.vue"
import PageHeader from "@/components/Article/PageHeader.vue"
import { getArticleById } from '@/js/api/article'
export default defineComponent({
  name: 'ArticleComponent',
  setup() {
    const store = useStore()  // 该方法用于返回store 实例
    const articleId = ref()
    const route = useRoute()
    const router = useRouter()
    const isShow = ref(true)
    const detail = ref({
      id: Number,
      author: '',
      title: '',
      avatar: '',
      description: '',
      content: '',
      keywords: [],
      categorys: [],
      createTime: '',
      colCnt: [],
    })

    const judgeMode = (mode) => {
      if (route.query.mode == 1 || mode == 1) {
        // const info = route.path
        const articleId = route.path.replace('/article/', '')
        isShow.value = !isShow.value

        //TODO:通过articleId获取文章详情
        var params = new URLSearchParams();
        params.append('articleId', articleId);
        // axios.post(store.state.path+"/article/getArticleById",params)
        //   .then(res => {
        //     console.log(res)
        //     if (res.data.code == 200) {
        //       detail.value = res.data.data
        //       sessionStorage.setItem("articleDetail", JSON.stringify(detail.value));
        //     } else {
        //       console.log(res.data.msg)
        //     }
        //   })
        //   .catch(function (error) {
        //     console.log(error);
        //   });
        getArticleById(params).then(res => {
          if (res.data.code == 200) {
            detail.value = res.data.data
            sessionStorage.setItem("articleDetail", JSON.stringify(detail.value));
          } else {
            console.log(res.data.msg)
          }
        }).catch(function (error) {
          console.log(error);
        });

        setTimeout(() => {
          isShow.value = !isShow.value
        }, 100);
        // detail.value = {
        //   id: id,
        //   author: 'zhangsan',
        //   title: `zhangsan part:` + info,
        //   avatar: 'https://joeschmoe.io/api/v1/random',
        //   description: 'GO JAVA',
        //   content: info+'# 111111Marked in the browser  Marked in the browser  Marked in the browser\n\nRendered by **marked**.\n\nRendered by **marked**.\n\nRendered by **marked**.\n\nRendered by **marked**.\n\nRendered by **marked**.\n\nRendered by **marked**.\n\nRendered by **marked**.\n\nRendered by **marked**.',
        //   keywords: ['GO', 'JAVA'],
        //   categorys: ['GO', 'PYTHON', 'JAVA'],
        //   createTime: '2015-07-23 15:23:05',
        //   colCnt: [234, 34, 43],
        // }
        // alert("articleDetail")
        // setTimeout(() => {
        //   isShow.value = !isShow.value
        // }, 1000);
      }
    }

    //刷新页面时，store无数据从sessionStorage中获取数据
    if (typeof (store.state.detail) == "undefined") {
      detail.value = JSON.parse(sessionStorage.getItem("articleDetail"))
      if (detail.value == null) {
        judgeMode()
      }
    } else {
      detail.value = JSON.parse(store.state.detail);
    }

    watch(() => router.currentRoute.value, (newVal, oldVal) => {
      if (newVal != oldVal) {
        // alert('路由发生变化new' + newVal.path + 'old' + oldVal.path)
        router.push({ path: newVal.path })
        judgeMode(1)
        isShow.value = !isShow.value
        isShow.value = !isShow.value
      }
    })

    onMounted(() => {
      judgeMode()
    })

    const details = ref(detail)
    // articleId.value = detail.value.id

    return {
      details,
      articleId,
      isShow,
      indexKey: 0
    }
  },
  components: {
    CommentComponent,
    PageHeader
  }
})
</script>