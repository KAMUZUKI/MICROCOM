<template>
  <!-- <div style="background: #ececec; padding: 0px"> -->
  <a-card size="small" style="width: 300px" hoverable="true">
    <div style="background: #ececec">
      <a-avatar shape="circle" :size="48" :src="userInfo.url">
        <template #icon>
          <UserOutlined />
        </template>
      </a-avatar>
      <span>{{ userInfo.username }}</span>
      <a-button @click="logout()">退出</a-button><br />
      <a-tag color="#55acee">
        <template #icon>
          <twitter-outlined />
        </template>
        Twitter
      </a-tag>
      <a-tag color="#cd201f">
        <template #icon>
          <youtube-outlined />
        </template>
        Youtube
      </a-tag>
      <a-tag color="#3b5999">
        <template #icon>
          <facebook-outlined />
        </template>
        Facebook
      </a-tag>
    </div>
  </a-card>
  <NotificationComponent ref="openNotification"></NotificationComponent>
  <!-- </div> -->
</template>

<script>
import { UserOutlined } from "@ant-design/icons-vue";
import { defineComponent, reactive, ref } from "vue";
import NotificationComponent from "../tools/NotificationComponent.vue";
import {
  TwitterOutlined,
  YoutubeOutlined,
  FacebookOutlined,
} from "@ant-design/icons-vue";
import { useStore } from "vuex"; // 引入useStore 方法
export default defineComponent({
  name: "AvatarComponent",
  components: {
    UserOutlined,
    NotificationComponent,
    TwitterOutlined,
    YoutubeOutlined,
    FacebookOutlined,
  },
  setup() {
    const store = useStore();

    const openNotification = ref();

    const userInfo = reactive({
      username: store.state.user.username,
      url: store.state.user.head,
      Motto: "即使再小的帆也能远航",
    });

    const logout = () => {
      sessionStorage.removeItem("user");
      sessionStorage.removeItem("articleDetail");
      sessionStorage.removeItem("likeList");
      store.state.isCertified = false;
      store.state.isLogin = false;
      openNotification.value.openNotificationWithIcon(
        "success",
        "成功",
        "恭喜退出成功"
      );
    };

    return {
      userInfo,
      openNotification,
      logout,
    };
  },
  props: {
    showAvatar: {
      type: Function,
      required: true,
    },
  },
});
</script>