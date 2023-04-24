<template>
  <q-layout view="hHh lpR fFf">
    <q-header reveal class="header-back bg-primary text-dark">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar style="float: left;">
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo.svg">
          </q-avatar>
          <q-item class="logo">MICROCOM</q-item>
          <head-right></head-right>
        </q-toolbar-title>
        <login-view/>
        <q-btn dense flat round icon="menu" @click="toggleRightDrawer" />
      </q-toolbar>
    </q-header>
    <q-drawer class="drawer" v-model="rightDrawerOpen" side="right" overlay>
      <personal-menu></personal-menu>
    </q-drawer>
    <q-page-container class="page-container">
      <transition :enter-active-class="`animate__animated animate__fadeInLeft`"
        :leave-active-class="`animate__animated animate__fadeOutLeft`" mode="out-in">
        <router-view />
      </transition>
    </q-page-container>
    <a-col flex="1 1 200px"></a-col>
    <a-col flex="auto">copyright 2022 By MUZUKI</a-col>
    <a-col flex="0 1 300px"></a-col>
  </q-layout>
</template>

<script setup>
import { onMounted, ref } from "vue"
import { useStore } from 'vuex'
import PersonalMenu from "@/components/Personal/PersonalMenu.vue"
import HeadRight from "@/components/Header/HeadRight.vue"
import LoginView from "@/views/LoginView.vue"

const rightDrawerOpen = ref(false)
const store = useStore()
const toggleRightDrawer = () => {
  rightDrawerOpen.value = !rightDrawerOpen.value
}

onMounted(() => {
  if (sessionStorage.getItem("user") != null) {
    store.state.user = JSON.parse(sessionStorage.getItem("user"));
    store.state.isLogin = true;
    store.state.isCertified = true;
  }
})

</script>

<style>
.q-drawer {
  backdrop-filter: blur(16px) saturate(180%) !important;
  -webkit-backdrop-filter: blur(16px) saturate(180%) !important;
  background-color: rgba(245, 245, 245, 0.45) !important;
  border-radius: 12px !important;
  border: 1px solid rgba(209, 213, 219, 0.3) !important;
}

.page-container {
  margin: 0 auto;
  padding-top: 55px !important; 
  background-color: #f0f2f5;
}
</style>

<style scoped>

.bg-primary {
  z-index: 999 !important;
}

.logo {
  float: left;
}

.q-header {
  backdrop-filter: blur(16px) saturate(180%) !important;
  -webkit-backdrop-filter: blur(16px) saturate(180%) !important;
  background-color: rgba(245, 245, 245, 0.45) !important;
  border-radius: 12px !important;
  border: 1px solid rgba(209, 213, 219, 0.3) !important;
}

#components-layout-demo-top-side .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}

.animate__fadeInLeft {
  --animation-duration: 0.5s;
  --animate-delay: 2s;
}

.animate__fadeOutLeft {
  animation-duration: 0.5s;
  animation-fill-mode: both;
}

.a-row {
  background-color: #d9d9d9;
  margin-top: 20px;
}

#components-layout-demo-basic .code-box-demo {
  text-align: center;
}

#components-layout-demo-basic .ant-layout-header,
#components-layout-demo-basic .ant-layout-footer {
  color: #fff;
  background: #7dbcea;
}

[data-theme="light"] #components-layout-demo-basic .ant-layout-header {
  background: #6aa0c7;
}

[data-theme="light"] #components-layout-demo-basic .ant-layout-footer {
  background: #6aa0c7;
}

#components-layout-demo-basic .ant-layout-footer {
  line-height: 1.5;
}

#components-layout-demo-basic .ant-layout-sider {
  color: #fff;
  line-height: 120px;
  background: #3ba0e9;
}

[data-theme="light"] #components-layout-demo-basic .ant-layout-sider {
  background: #3499ec;
}

#components-layout-demo-basic .ant-layout-content {
  min-height: 120px;
  color: #fff;
  line-height: 120px;
  background: rgba(16, 142, 233, 1);
}

[data-theme="light"] #components-layout-demo-basic .ant-layout-content {
  background: #107bcb;
}

#components-layout-demo-basic>.code-box-demo>.ant-layout+.ant-layout {
  margin-top: 48px;
}
</style>