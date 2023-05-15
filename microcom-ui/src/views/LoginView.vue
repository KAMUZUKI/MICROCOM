<template>
  <div id="components-modal-demo-position">
    <a-button type="primary" v-if="!isLogin" @click="modal1Visible = true"> 登录 </a-button>
    <q-item clickable v-ripple v-if="isLogin">
      <q-item-section side>
        <q-avatar round size="48px">
          <img :src=user.head />
          <q-badge floating color="teal">new</q-badge>
        </q-avatar>
      </q-item-section>
      <q-item-section>
        <q-item-label>{{ user.name }}</q-item-label>
        <q-item-label caption>{{ user.value }}</q-item-label>
      </q-item-section>
      <q-menu auto-close>
          <q-list style="min-width: 130px">
            <q-item clickable>
              <q-item-section @click="logout">退出</q-item-section>
            </q-item>
            <q-separator />
            <q-item clickable>
              <q-item-section @click="jumpTo('/')">文章</q-item-section>
            </q-item>
            <q-item clickable>
              <q-item-section @click="jumpTo('timeline')">历程</q-item-section>
            </q-item>
            <q-item clickable>
              <q-item-section @click="jumpTo('setting')">设置</q-item-section>
            </q-item>
          </q-list>
        </q-menu>
    </q-item>
    <a-modal v-model:visible="modal1Visible" :footer="null" centered>
      <div class="flip-card">
        <div class="flip-card-inner" :style="{ transform: cardRotate }">
          <div class="flip-card-front">
            <RegisterForm :changeLogin="changeLogin"></RegisterForm>
          </div>
          <div class="flip-card-back">
            <LoginForm :changeRegister="changeRegister" :setModal1Visible="setModal1Visible"></LoginForm>
          </div>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { message } from 'ant-design-vue'
import { useStore } from 'vuex'
import { useRouter } from "vue-router";
import RegisterForm from "@/components/Login/RegisterForm.vue";
import LoginForm from "@/components/Login/LoginForm.vue";
const modal1Visible = ref(false)
const isLogin = ref(false)
const cardRotate = ref()
const store = useStore()
const router = useRouter()
const user = ref()
const changeLogin = () => {
  cardRotate.value = 'rotateY(180deg)'
}
const changeRegister = () => {
  cardRotate.value = 'rotateY(0deg)'
}

const setModal1Visible = (visible) => {
  modal1Visible.value = visible;
};

const jumpTo = (path) => {
  router.push({
    name: path,
  });
}

const logout = () => {
  localStorage.removeItem("user");
  localStorage.removeItem("articleDetail");
  localStorage.removeItem("likeList");
  localStorage.removeItem("OAuthToken");
  store.state.isCertified = false;
  store.state.isLogin = false;
  message.success("退出成功");
};

watch(
  () => store.state.isLogin,     // 一个函数，返回监听属性
  (newVal) => {
    isLogin.value = newVal;
    user.value = store.state.user;
  },
  {
    immediate: true,     // 立即执行，默认为false
    deep: true     // 深度监听，默认为false
  }
)

</script>

<style>
#components-modal-demo-position {
  position: relative;
  float: right;
}

.flip-card {
  height: 660px;
  width: 476px;
  background-color: transparent;
  perspective: 1000px;
  font-family: sans-serif;
}

.title {
  font-size: 1.5em;
  font-weight: 900;
  text-align: center;
  margin: 0;
}

.flip-card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
}

.flip-card-inner {
  transform: rotateY(180deg);
}

.flip-card-front,
.flip-card-back {
  box-shadow: 0 8px 14px 0 rgba(0, 0, 0, 0.2);
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
}

.flip-card-back {
  transform: rotateY(180deg);
}
</style>