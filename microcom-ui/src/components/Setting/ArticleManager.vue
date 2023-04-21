<template>
  <div class="container">
    <a-list item-layout="horizontal" :pagination="pagination" :data-source="listData">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-list-item-meta :description=item.content>
            <template #title>
              <a>{{ item.title }}</a>
            </template>
            <template #avatar>
              <a-avatar style="margin-left:30px;width:45px;height:45px"
                :src="'http://q1.qlogo.cn/g?b=qq&nk=' + item.img + '&s=100'" />
            </template>
          </a-list-item-meta>
          <a-button class="button" type="primary" @click="modify(item.id)">修改</a-button>
          <a-popconfirm title="确定删除该文章?" ok-text="确认" cancel-text="取消" @confirm="confirm(item.id)" @cancel="cancel">
            <a-button class="button" type="primary" danger>删除</a-button>
          </a-popconfirm>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>
  
<script>
import { defineComponent, ref, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router';
import axios from 'axios'
import { useStore } from 'vuex';
export default defineComponent({
  setup() {
    const store=useStore();
    const router = useRouter()
    const originData = ref([])
    const listDataTmp = ref([])
    const listData = ref([])

    const pagination = {
      onChange: page => {
        console.log(page);
      },
      pageSize: 10,
    };

    const initDataList = () => {
      //TODO: 从后端获取数据
      axios.post(store.state.path + '/article/getAllArticle')
        .then(res => {
          console.log(res)
          if (res.data.code == 1) {
            listDataTmp.value = res.data.data
            for (const [key, item] of Object.entries(listDataTmp.value)) {
              console.log(key)
              originData.value.push(item);
            }
            originData.value.forEach((item) => {
              item.content = item.content.replace(/#*.*#/g, '').replace(/[^(\u4e00-\u9fa5)(，。（）【】{}！,\-!)]/g, '').substring(0, 100) + "....."
              listData.value.push(item)
            })
          } else {
            console.log(res.data.msg)
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    const modify = (articleId) => {
      //根据id修改文章,跳转至编辑页面
      router.push({
        path: '/editor',
        query: {
          articleId: articleId
        }
      })
    }

    const confirm = articleId => {
      //TODO:根据id删除文章
      var params = new URLSearchParams();
      params.append('articleId', articleId);
      axios.post(store.state.path+'/article/deleteArticle', params)
        .then(res => {
          if (res.data.code == 1) {
            message.success('删除成功');
            listData.value.pop(articleId)
          } else {
            message.error('删除失败');
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    const cancel = e => {
      console.log(e);
      message.error('取消删除');
    }

    onMounted(() => {
      initDataList()
    })

    return {
      listData,
      pagination,
      current: ref(2),
      modify,
      confirm,
      cancel,
    };
  },
});
</script>

<style scoped>
.flink {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}

.container {
  min-height: 80vh;
  min-width: 1000px;
}

.button {
  margin-right: 20px;
}
</style>