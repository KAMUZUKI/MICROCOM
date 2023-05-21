<template>
  <a-list style="max-width:1000px;min-width: 700px" item-layout="vertical" size="large" :pagination="pagination"
    :data-source="initDataList">
    <template #renderItem="{ item }">
      <a-list-item key="item.title" class="article">
        <template #actions>
          <span v-for="{ type, id } in actions" :key="type">
            <template v-if="id == 0">
              <component :is="type[0]" style="margin-right: 8px" />
              {{ item.colCnt[id] }}
            </template>
            <template v-else-if="id == 1">
              <component :is="type[hasExisted(item.id)]" style="margin-right: 8px" @click="clickModel(item.id, id);" />
              {{ item.colCnt[id] }}
            </template>
            <template v-else>
              <component :is="type[0]" style="margin-right: 8px" @click="clickModel(item.id, id)" />
              {{ item.colCnt[id] }}
            </template>
          </span>
        </template>
        <template #extra>
          <img width="272" alt="logo" :src="(item.titleImgs==''?'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png':item.titleImgs==' '?'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png':item.titleImgs)" />
        </template>
        <a-list-item-meta :description=utils.parseDateToPast(item.createTime)>
          <template #title>
            <router-link class="nav-link" :to="'/article/' + item.id" @click="pushToDetail(item)">
              {{ item.title }}
            </router-link>
          </template>
          <template #avatar>
            <a-avatar :src="'http://q1.qlogo.cn/g?b=qq&nk='+item.head+'&s=100'" />
          </template>
        </a-list-item-meta>
        {{ item.content.replace(/#*.*#/g, '').replace(/[^(\u4e00-\u9fa5)(，。（）【】{}！,\-!)]/g, '').substring(0, 200) +
    "....."
        }}
      </a-list-item>
    </template>
  </a-list>
</template>

<style>
.article {
  margin-top: 10px;
  border-radius: 10px;
  border-radius: 10px;
  transition: 2s;
}

.article:hover{
  border-radius: 10px;
  background-image: linear-gradient(120deg, #fdfbfb 0%, #ebedee 100%);
  box-shadow:  30px 30px 26px #cfcece,
               -30px -30px 26px #f3f2f2;
  transition: all 2s;
}

</style>

<script>
import { EyeOutlined, LikeOutlined, LikeFilled, MessageOutlined } from '@ant-design/icons-vue';
import { defineComponent, onMounted, ref, toRaw, onBeforeUnmount, watch } from 'vue';
import { message } from 'ant-design-vue';
import { useStore } from 'vuex' // 引入useStore 方法
import { useRouter } from 'vue-router'
import utils from '@/js/utils/utils'
import axios from 'axios'
export default defineComponent({
  setup() {

    const listDataTmp = ref([]);
    const router = useRouter()
    const listData = ref([])
    const clickLimitCount = ref()
    const initDataList = ref([])
    const newData = ref({})
    const store = useStore()  // 该方法用于返回store 实例
    const likeList = ref([])  //用户点赞的文章id
    const keywords = ref([])
    const keywordSet = new Set(keywords.value)
    const likeSet = new Set(likeList.value)

    const initData = () => {
      likeList.value = JSON.parse(localStorage.getItem('likeList'))??''
      if (likeList.value.length > 0) {
        likeList.value.forEach(item => {
          likeSet.add(item)
        })
      }
      // TODO:获取文章列表   listData
      var params = new URLSearchParams();
      axios.post(store.state.path + '/article/getAllArticle', params)
        .then(res => {
          console.log(res)
          if (res.data.code == 200) {
            listDataTmp.value = res.data.data
            for (const [key, item] of Object.entries(listDataTmp.value)) {
              key
              item.colCnt = [item.readCnt, item.agreeCnt, item.commentCnt]
              listData.value.push(item);
              item.label.split(',')
                .forEach(element => {
                  keywordSet.add(element)
                });
            }
            localStorage.setItem('keywords', JSON.stringify(Array.from(keywordSet)))
            initDataByCategory('all')
          } else {
            message.error("无法拉取文章" + res.data.msg);
          }
        })
        .catch(function (error) {
          message.error("获取文章异常" + error);
        });
    }

    const pushToDetail = (item) => {
      store.state.detail = JSON.stringify(item)
      localStorage.setItem("articleDetail", JSON.stringify(item));
    }

    const initDataByCategory = (type) => {
      if (type == 'all') {
        for (let i = 0; i < listData.value.length; i++) {
          initDataList.value.push(listData.value[i]);
        }
      } else {
        for (let i = 0; i < listData.value.length; i++) {
          if (listData.value[i].categoryId == type) {
            initDataList.value.push(listData.value[i]);
          }
        }
      }
    }

    const initDataByKeyword = (type) => {
      for (let i = 0; i < listData.value.length; i++) {
        if (listData.value[i].label.split(',').includes(type)) {
          initDataList.value.push(listData.value[i]);
        }
      }
    }

    const pagination = {
      onChange: page => {
        console.log("pages" + page);
      },
      simple: true,
      pageSize: 5,
    };

    // 1.建立链接 -- 携带cookie参数
    var ws = new WebSocket(
      store.state.wspath + `/websocket`
    );

    // 3.服务器每次返回信息都会执行一次onmessage方法
    ws.onmessage = function (res) {
      //获取最新数据
      if (res.data !== null) {
        initDataList.value = []
        newData.value = JSON.parse(res.data)
        listData.value.unshift(JSON.parse(newData.value))
        initDataByCategory('all')
      } else {
        return
      }
    };

    // var i = 1
    onMounted(() => {
      initData()
      initDataByCategory('all')
      //用于清除用户当前时间段点击次数
      setInterval(() => {
        clickLimitCount.value = 0
      }, 3000)
    });

    watch(clickLimitCount, (newValue) => {
      //监听用户点击次数
      if (newValue > 4) {
        message.warning('请不要频繁点击');
      }
    })

    onBeforeUnmount(() => {
      initDataList.value = [];
      ws.onclose = function () {
        console.log("Connection closed.");
      };
    });

    const changeContentByCategory = (type) => {
      initDataList.value = [];
      initDataByCategory(type);
    }

    const changeContentByKeyword = (type) => {
      initDataList.value = [];
      initDataByKeyword(type);
    }

    const actions = ref([{
      id: 0,
      flag: 0,
      type: ['EyeOutlined'],
      method: 'clickStar',
    }, {
      id: 1,
      flag: 0,
      type: ['LikeOutlined', 'LikeFilled'],
      method: 'clickLike',
    }, {
      id: 2,
      flag: 0,
      type: ['MessageOutlined'],
      method: 'clickMessage',
    }]);

    const clickModel = (articleId, mode) => {
      if (localStorage.getItem("user")) {
        //登录执行点击事件
        clickLimitCount.value++
        if (clickLimitCount.value > 4) {
          return
        }
        if (mode == 1) {
          var params = new URLSearchParams();
          params.append('articleId', articleId);
          params.append('userId', JSON.parse(localStorage.getItem("user")).id);
          axios.post(store.state.path + '/article/changeData', params)
            .then(res => {
              if (res.data.code == 200) {
                //点赞执行成功
                initDataList.value.forEach((item) => {
                  if (item.id === articleId) {
                    if (hasExisted(articleId)) {
                      //用户已经点赞
                      //取消点赞
                      item.colCnt[1] -= 1;
                      likeSet.delete(item.id)
                    } else {
                      //点赞
                      likeSet.add(item.id)
                      item.colCnt[1] += 1;
                    }
                    actions.value[1].flag ^= 1
                  }
                })
              } else {
                message.error(res.data.msg)
              }
            })
            .catch(function (error) {
              message.error(error);
            });
        } else {
          router.push({
            path: '/article/' + articleId,
            query: {
              mode: 1
            }
          })
        }
      } else {
        message.warning('请先登录');
        return
      }
    }

    const handleStar = () => {
      actions.value[0].type = 'StarFilled';
    }

    const handleLike = () => {
      actions.value[2].type = 'LikeFiller';
    }

    const hasExisted = (id) => {
      if (likeSet.has(id)) {
        return 1
      } else {
        return 0
      }
    }

    return {
      utils,
      actions,
      likeList,
      listData,
      pagination,
      initDataList,
      toRaw,
      handleStar,
      handleLike,
      clickModel,
      hasExisted,  //判断文章是否被用户点赞
      pushToDetail,
      initDataByKeyword,
      initDataByCategory,
      changeContentByCategory,
      changeContentByKeyword,
    };
  },
  components: {
    EyeOutlined,
    LikeOutlined,
    LikeFilled,
    MessageOutlined,
  },
});
</script>