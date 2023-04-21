<template>
  <a-layout>
    <a-layout-content v-if="!refresh">
      <a-space :size="small" direction="horizontal" align="start">
        <a-col style="float: left; height: 800px" flex="fix">
          <MenuComponent
            :showContentByCategory="showContentByCategory"
          ></MenuComponent>
        </a-col>
        <a-col
          style="background-color: #fff; min-width: 600px; max-width: 1300px"
          flex="fix"
        >
          <router-view #default="{ route, Component }">
            <transition
              :enter-active-class="`animate__animated ${route.meta.transition_enter}`"
              :leave-active-class="`animate__animated ${route.meta.transition_leave}`"
              mode="out-in"
            >
              <component :is="Component" ref="transContent"></component>
            </transition>
          </router-view>
        </a-col>
        <a-col style="float: left" flex="fix">
          <a-space :size="40" direction="vertical" align="start">
            <AvatarComponent v-if="avatarVisible" style="width: 100%">
            </AvatarComponent>
            <AwordComp></AwordComp>
            <CarouselElement></CarouselElement>
            <SiderTag
              :showContentByKeyword="showContentByKeyword"
              :showContent="showContent"
            ></SiderTag>
          </a-space>
        </a-col>
      </a-space>
    </a-layout-content>
  </a-layout>
  <NotificationComponent ref="openNotification"></NotificationComponent>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import AvatarComponent from "../components/Avatar/AvatarComponent.vue";
import MenuComponent from "../components/Menu/MenuComponent.vue";
import SiderTag from "../components/SiderTag/SiderTag.vue";
import AwordComp from "../components/AWord/AwordComp.vue";
import CarouselElement from "../components/Carousel/CarouselElement.vue";
import NotificationComponent from "../components/tools/NotificationComponent.vue";
import { useStore } from "vuex"; // 引入useStore 方法
const transContent = ref();
const refresh = ref(true);
const store = useStore();
const openNotification = ref();

const showContentByCategory = (type) => {
  transContent.value.changeContentByCategory(type);
  refresh.value = !refresh.value;
  refresh.value = !refresh.value;
};

const showContentByKeyword = (type) => {
  transContent.value.changeContentByKeyword(type);
  refresh.value = !refresh.value;
  refresh.value = !refresh.value;
};

const avatarVisible = ref(false);

const showAvatar = (isLogin) => {
  avatarVisible.value = isLogin;
};

onMounted(() => {
  if (sessionStorage.getItem("user") != null) {
    store.state.user = JSON.parse(sessionStorage.getItem("user"));
    store.state.isLogin = true;
    store.state.isCertified = true;
    showAvatar(true);
    openNotification.value.openNotificationWithIcon(
      "success",
      "登录",
      "自动登录成功"
    );
  }
});

watch(store.state.isLogin, (newVal) => {
  showAvatar(newVal);
});

</script>

<style>
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

#components-layout-demo-basic > .code-box-demo > .ant-layout + .ant-layout {
  margin-top: 48px;
}
</style>

