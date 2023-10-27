<template>
  <a-list style="max-width:1000px;min-width: 700px" item-layout="vertical" size="large" :pagination="paginationOpt" :data-source="initDataList">
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
              {{ item.id }} + {{ item.title }}
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


<script>
import { EyeOutlined, LikeOutlined, LikeFilled, MessageOutlined } from '@ant-design/icons-vue';
import { defineComponent, onMounted, ref, toRaw, onBeforeUnmount, watch } from 'vue';
import { message } from 'ant-design-vue';
import { useStore } from 'vuex' // 引入useStore 方法
import { useRouter } from 'vue-router'
import { getArticleByPage, userLike } from '@/js/api/article'
import utils from '@/js/utils/utils'
export default defineComponent({
  setup() {

    const listDataTmp = ref([]);
    const router = useRouter()
    const listData = ref([])
    const clickLimitCount = ref()
    const initDataList = ref([])
    const store = useStore()  // 该方法用于返回store 实例
    const likeList = ref([])  //用户点赞的文章id
    const keywords = ref([])
    const keywordSet = new Set(keywords.value)
    const likeSet = new Set(likeList.value)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    const initData = () => {
      listData.value = []
      initDataList.value = []
      likeList.value = JSON.parse(localStorage.getItem('likeList'))??''
      if (likeList.value.length > 0) {
        likeList.value.forEach(item => {
          likeSet.add(item)
        })
      }
      // TODO:获取文章列表   listData
      const { defaultCurrent, defaultPageSize } = paginationOpt;
      const params = {
        'currentPage': defaultCurrent,
        'pageSize': defaultPageSize,
      }
      getArticleByPage(params).then(res => {
          if (res.code == 200) {
            listDataTmp.value = res.data.records
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
            paginationOpt.total = res.data.total
            initDataByCategory('all')
          } else {
            message.error("无法拉取文章" + res.msg);
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

    const paginationOpt = {
        defaultCurrent: 1, // 默认当前页数
        defaultPageSize: 5, // 默认当前页显示数据的大小
        total: 0, // 总数，必须先有
        showSizeChanger: true,
        showQuickJumper: true,
        pageSizeOptions: ["5", "10", "20"],
        showTotal: (total) => `共 ${total} 条`, // 显示总数
        onShowSizeChange: (current, pageSize) => {
          paginationOpt.defaultCurrent = 1;
          paginationOpt.defaultPageSize = pageSize;
          initData(); //显示列表的接口名称
        },
        // 改变每页数量时更新显示
        onChange: (current, size) => {
          paginationOpt.defaultCurrent = current;
          paginationOpt.defaultPageSize = size;
          initData();
        },
      }

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
          let userId = store.state.user.id
          userLike(articleId, userId).then(res => {
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
      total,
      pageSize,
      currentPage,
      initDataList,
      paginationOpt,
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