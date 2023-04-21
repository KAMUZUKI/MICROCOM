<template>
  <a-card bordered style="width: 300px;height: 200px;border-radius: 20px;margin-top:20px" hoverable>
    <a-form :model="formState" name="normal_login" class="login-form" @finish="onFinish" @finishFailed="onFinishFailed">
      <a-form-item style="margin-bottom:10px;width: 220px" label="用户" name="username"
        :rules="[{ required: true, message: 'Please input your username!' }]">
        <a-input v-model:value="formState.username" style="border-radius:10px">
          <template #prefix>
            <UserOutlined class="site-form-item-icon" />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item style="margin-bottom:10px;width: 220px" label="密码" name="password"
        :rules="[{ required: true, message: 'Please input your password!' }]">
        <a-input-password v-model:value="formState.password" style="border-radius:10px">
          <template #prefix>
            <LockOutlined class="site-form-item-icon" />
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item style="margin-bottom:10px;">
        <a-form-item name="remember" no-style>
          <a-checkbox v-model:checked="formState.remember">记住</a-checkbox>
        </a-form-item>
        <a class="login-form-forgot" href="">忘记密码</a>
      </a-form-item>

      <a-form-item>
        <a-button :disabled="disabled" style="border-radius:10px" type="primary" html-type="submit"
          class="login-form-button" @click="login()">
          Log in
        </a-button>
        Or
        <a @click="showDrawer">register now!</a>
      </a-form-item>
    </a-form>
  </a-card>
  <NotificationComponent ref="openNotification"></NotificationComponent>
</template>
<script>
import { defineComponent, reactive, computed, onMounted, ref } from 'vue';
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import NotificationComponent from '../tools/NotificationComponent.vue';
import { useStore } from 'vuex' // 引入useStore 方法
import axios from 'axios'
export default defineComponent({
  name: 'LoginForm',
  components: {
    UserOutlined,
    LockOutlined,
    NotificationComponent
  },

  setup(props) {
    const openNotification = ref()
    const store = useStore();
    const user = ref()
    const likeList = ref([])

    const login = () => {
      var params = new URLSearchParams();
      params.append('account', formState.username);
      params.append('password', formState.password);
      //TODO: Login
      axios.post(store.state.path + '/user/login', params)
        .then(res => {
          if (res.data.code == 1) {
            store.state.isLogin = true
            store.state.isCertified = true
            user.value = {
              id: res.data.data.id,
              username: res.data.data.username,
              email: res.data.data.email,
              head: 'http://q1.qlogo.cn/g?b=qq&nk=' + res.data.data.head + '&s=100',
              type: res.data.data.type
            }
            likeList.value = res.data.data.likeList
            sessionStorage.setItem('likeList', JSON.stringify(likeList.value))
            store.state.user = user.value
            sessionStorage.setItem("user", JSON.stringify(user.value));
            props.showAvatar()
            openNotification.value.openNotificationWithIcon('success', '登录', '登录成功')
          } else {
            openNotification.value.openNotificationWithIcon('error', '登录', '登录失败')
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    const formState = reactive({
      id: 1,
      username: '10001',
      password: 'a',
      remember: true,
    });

    const onFinish = values => {
      console.log('Success:', values);
    };

    const onFinishFailed = errorInfo => {
      console.log('Failed:', errorInfo);
    };

    const disabled = computed(() => {
      return !(formState.username && formState.password);
    });

    onMounted(() => {
      if (sessionStorage.getItem("user") !== null) {
        var userId = JSON.parse(sessionStorage.getItem("user")).id
        var params = new URLSearchParams();
        params.append('userId', userId);
        //TODO: 获取用户喜欢的列表
        axios.post(store.state.path + '/user/getLikeList', params)
          .then(res => {
            if (res.data.code == 1) {
              likeList.value = res.data.data
              store.state.isLogin = true
              store.state.user = JSON.parse(sessionStorage.getItem("user"))
              store.state.isCertified = true
              props.showAvatar()
              sessionStorage.setItem('likeList', JSON.stringify(likeList.value))
              openNotification.value.openNotificationWithIcon('success', '登录', '自动登录成功')
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    });
    return {
      formState,
      onFinish,
      onFinishFailed,
      disabled,
      login,
      openNotification
    };
  }, 
  props: {
    showDrawer: {
      type: Function,
      required: true,
    },
    showAvatar: {
      type: Function,
      required: true,
    }
  },
});
</script>
<style>
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}

#components-form-demo-normal-login .login-form-forgot {
  float: right;
}

#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
</style>