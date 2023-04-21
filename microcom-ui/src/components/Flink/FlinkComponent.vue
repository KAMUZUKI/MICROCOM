<template>
  <div class="wrap_box">
    <div class="flink">
      <template v-for="item in flinkList" :key="item.id">
        <a :href=item.url>
          <a-card hoverable style="width: 300px;margin-top:20px">
            <a-card-meta :title=item.name :description=item.description>
              <template #avatar>
                <a-avatar :src="'http://q1.qlogo.cn/g?b=qq&nk=' + item.img + '&s=100'" />
              </template>
            </a-card-meta>
          </a-card>
        </a>
      </template>
    </div>
  </div>
</template>

<style scoped>
.flink {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}

.wrap_box {
  min-height: 80vh;  
}
</style>
<script>
import { defineComponent, ref,onMounted } from 'vue';
import axios from 'axios'
import {message} from 'ant-design-vue'
import {useStore} from 'vuex'
export default defineComponent({
  setup() {
    const store=useStore();
    const flinkList = ref([])
    const tmpFlinkList = ref([])

    const initFlink = () => {
      var params = new URLSearchParams();
      axios.post(store.state.path+'/flink/getFlink', params)
      .then(res => {
          if (res.data.code == 1) {
            tmpFlinkList.value = res.data.data
            tmpFlinkList.value.forEach(item => {
              item.url = 'http://' + item.url
              flinkList.value.push(item)
            })
          }else {
            message.error('获取友链失败');
          }
      })
      .catch(function (error) {
          console.log(error);
      });
    }
    
    onMounted(() => {
      initFlink()
    })

    return {
      flinkList
    };
  },
});
</script>