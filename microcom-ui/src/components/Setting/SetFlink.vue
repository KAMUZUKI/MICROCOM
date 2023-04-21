<template>
  <div class="wrap_box"><h1>友链管理</h1>
    
    <div class="flink">
      <template v-for="item in flinkList" :key="item.id">
        <a-popconfirm title="操作" ok-text="删除" cancel-text="取消" @confirm="confirm(item.id)" @cancel="cancel">
          <a-card hoverable style="width: 300px;margin-top:20px">
            <a-card-meta :title="item.name + (item.status == 1 ? '[已启用]' : '[已禁用]')" :description=item.description>
              <template #avatar>
                <a-avatar :src="'http://q1.qlogo.cn/g?b=qq&nk=' + item.img + '&s=100'" />
              </template>
            </a-card-meta>
          </a-card>
        </a-popconfirm>
      </template>
      <a-card hoverable style="width: 300px;height:97px;margin-top:20px" @click="showDrawer">
        <a-card-meta title="添加友链">
          添加友链
        </a-card-meta>
      </a-card>
      <a-drawer title="添加友链" :width="500" :visible="visible" :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }" @close="onClose">
        <a-form ref="formRef" name="custom-validation" :model="formState" :rules="rules" v-bind="layout"
          @finish="handleFinish" @validate="handleValidate" @finishFailed="handleFinishFailed"
          :validate-messages="validateMessages">
          <h2 style="text-align:center;">添加友链</h2>
          <a-form-item label="用户名" name="name" required>
            <a-input v-model:value="formState.name" :rules="[{ required: true }]">
              <template #prefix>
                <UserOutlined class="site-form-item-icon" />
              </template>
            </a-input>
          </a-form-item>
          <a-form-item :name="['img']" label="邮箱" :rules="[{ required: true }, { type: 'email' }]">
            <a-input v-model:value="formState.img" />
          </a-form-item>
          <a-form-item :name="['url']" label="网址" :rules="[{ required: true }]">
            <a-input v-model:value="formState.url" />
          </a-form-item>
          <a-form-item :name="['description']" label="简介" :rules="[{ required: true }]">
            <a-textarea v-model:value="formState.description" />
          </a-form-item>
          <a-form-item :name="['status']" label="是否启用">
            <a-switch v-model:checked="formState.status" />
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 16, offset: 6 }">
            <a-button type="primary" html-type="submit">添加</a-button>
            <a-button style="margin-left: 10px" @click="onClose">退出</a-button>
          </a-form-item>
        </a-form>
        <notification-component ref="openNotification"></notification-component>
      </a-drawer>
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
  max-width: 1000px;
}
</style>
<script>

import { defineComponent, ref,onMounted } from 'vue';
import { message } from 'ant-design-vue';
import axios from 'axios'
import { useStore } from 'vuex';
export default defineComponent({
  setup() {
    const formRef = ref()
    const store=useStore()
    const flinkList = ref([])
    const tmpFlinkList = ref([])

    const initFlink = () => {
      axios.post(store.state.path+'flink/getFlink')
      .then(res => {
          if (res.data.code == 1) {
            tmpFlinkList.value = res.data.data
            tmpFlinkList.value.forEach(item => {
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

    //FORM BEGIN
    const formState = ref({
      name: '',
      img: '',
      url: '',
      description: '',
      status: true,
    })

    const confirm = id => {
      deleteById(id)
    };

    const deleteById = id => {
      var params = new URLSearchParams();
      params.append('op', 'deleteFlinkById');
      params.append('id', id);
      axios.post(store.state.path+'/flink/deleteFlinkById', params)
      .then(res => {
          if (res.data.code == 1) {
            flinkList.value.pop(id)
            message.success('删除成功');
          }else {
            message.error('删除失败');
          }
      })
      .catch(function (error) {
          console.log(error);
      });
    }

    const cancel = e => {
      console.log(e);
      message.error('操作取消');
    };

    const validateMessages = {
      required: '${label} 是必要的!',
      types: {
        email: '${label} 是无效的邮箱!',
        number: '${label} 是无效的数字!',
      }
    };
    //FORM END

    const handleFinish = values => {
      var params = new URLSearchParams();
      params.append('name', values.name);
      params.append('url', values.url);
      params.append('img', values.img);
      params.append('description', values.description);
      params.append('status', values.status==true ? 1 : 0);
      axios.post(store.state.path+'/flink/addFlink', params)
      .then(res => {
          if (res.data.code == 1) {
            formState.value = {
              name: values.name,
              url: values.url,
              img: values.img,
              description: values.description,
              status: values.status ? 1 : 0,
            }
            flinkList.value.push(formState.value)
            formState.value = {
              name: '',
              url: '',
              img: '',
              description: '',
              status: true,
            }
            message.success(res.data.msg);
            onClose()
          } else {
            onClose()
            message.error(res.data.msg);
          }
      })
      .catch(function (error) {
          console.log(error);
      });
    };

    const handleFinishFailed = errors => {
      console.log(errors);
    };

    const resetForm = () => {
      formRef.value.resetFields();
    };

    const handleValidate = (...args) => {
      console.log(args);
    };


    const layout = {
      labelCol: {
        span: 6,
      },
      wrapperCol: {
        span: 16,
      },
    };

    //DRAWER BEGIN
    const visible = ref(false);

    const showDrawer = () => {
      visible.value = true;
    };

    const onClose = () => {
      visible.value = false;
    };

    //DRAWER END
    return {
      layout,
      visible,
      flinkList,
      formState,
      validateMessages,
      cancel,
      confirm,
      onClose,
      resetForm,
      showDrawer,
      handleFinish,
      handleValidate,
      handleFinishFailed,
    };
  }
});
</script>