<template>
  <div class="menu">
    <a-menu
      :selectedKeys="[$route.path]"
      theme="light"
      mode="horizontal"
      :click="jumpTo"
      :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="1">
        <span>首页</span>
      </a-menu-item>
      <a-menu-item key="2">
        <span href="/">友链</span>
      </a-menu-item>
      <a-menu-item key="3" to="/editor">
        <span>撰写</span>
      </a-menu-item>
      <a-sub-menu key="sub1">
        <template #title>
          <span>
            <user-outlined />
            设置
            <DownOutlined />
          </span>
        </template>
        <a-menu-item key="4" to="/setting/setflink">
          <span>管理用户</span>
        </a-menu-item>
        <a-menu-item key="5" to="/setting/setflink">
          <span>设置友链</span>
        </a-menu-item>
        <a-menu-item key="6" to="/setting/article">
          <span>管理文章</span>
        </a-menu-item>
        <a-menu-item key="7" to="/setting/add">
          <span>添加分类</span>
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="8" to="/about">
        <span>关于</span>
      </a-menu-item>
      <a-menu-item key="9" to="/editor">
        <span>撰写</span>
      </a-menu-item>
    </a-menu>

    <q-item clickable v-ripple>
      <q-item-section side>
        <q-avatar round size="48px">
          <img src="https://cdn.quasar.dev/img/avatar.png" />
          <q-badge color="red" floating>4</q-badge>
        </q-avatar>
        <q-menu>
          <q-list style="min-width: 100px">
            <q-item clickable v-close-popup>
              <q-item-section>New tab</q-item-section>
            </q-item>
            <q-item clickable v-close-popup>
              <q-item-section>New incognito tab</q-item-section>
            </q-item>
            <q-separator />
            <q-item clickable v-close-popup>
              <q-item-section>Recent tabs</q-item-section>
            </q-item>
            <q-item clickable v-close-popup>
              <q-item-section>History</q-item-section>
            </q-item>
            <q-item clickable v-close-popup>
              <q-item-section>Downloads</q-item-section>
            </q-item>
            <q-separator />
            <q-item clickable v-close-popup>
              <q-item-section>Settings</q-item-section>
            </q-item>
            <q-separator />
            <q-item clickable v-close-popup>
              <q-item-section>Help &amp; Feedback</q-item-section>
            </q-item>
          </q-list>
        </q-menu>
      </q-item-section>
    </q-item>

    <LoginView v-if="!isLogin()" />
  </div>
</template>

<script>
import { DownOutlined } from "@ant-design/icons-vue";
import { defineComponent, onMounted, reactive,watch} from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { colors } from "quasar";
import LoginView from "@/views/LoginView.vue";
export default defineComponent({
  name: "BarDemo",
  components: {
    DownOutlined,
    LoginView,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();

    const blueModel = false;
    //自己定义的样式
    const colorStyle = [
      {
        name: "primary",
        color: "teal",
      },
      {
        name: "secondary",
        color: "#000",
      },
      {
        name: "positive",
        color: "#F03",
      },
    ];

    const colorStyles = [
      {
        name: "primary",
        color: "#027BE3",
      },
      {
        name: "secondary",
        color: "#000",
      },
      {
        name: "positive",
        color: "#F03",
      },
    ];

    const colStyle = () => {
      colorStyle.forEach((element) => {
        colors.setBrand(element.name, element.color);
      });
    };

    //封装 blueModel=true函数
    const colsStyle = () => {
      colorStyles.forEach((element) => {
        colors.setBrand(element.name, element.color);
      });
    };

    const showAvatar = () => props.showAvatar;

    const gotoEditor = () => {
      router.push({
        name: "editor",
        params: {},
      });
    };

    const jumpTo = (path) => {
      alert("jumpTo" + path);
      // router.push({
      //   name: path,
      //   params: {},
      // });
    };

    const isLogin = () => {
      return store.state.isLogin;
    };

    const state = reactive({
      collapsed: false,
      openKeys: [],
      rootSubmenuKeys: ["/"],
      list: [
        {
          key: "1",
          title: "首页",
          path: "/",
        },
        {
          key: "2",
          title: "友链",
          path: "/flink",
        },
        {
          key: "3",
          title: "撰写",
          path: "/editor",
        },
        {
          key: "4",
          title: "设置",
          path: "",
          children: [
            {
              key: "4.1",
              title: "管理用户",
              path: "/setting/manager",
            },
            {
              key: "4.2",
              title: "设置友链",
              path: "/setting/setflink",
            },
            {
              key: "4.3",
              title: "管理文章",
              path: "/setting/article",
            },
            {
              key: "4.4",
              title: "添加分类",
              path: "/setting/add",
            },
          ],
        },
        {
          key: "5",
          title: "关于",
          path: "/about",
        },
      ],
    });

    const toggleCollapsed = () => {
      this.collapsed = !this.collapsed;
    };

    const onOpenChange = (openKeys) => {
      // 将当前打开的父级菜单存入缓存中
      window.sessionStorage.setItem("openKeys", JSON.stringify(openKeys));
      //  控制只打开一个
      const latestOpenKey = openKeys.find(
        (key) => this.openKeys.indexOf(key) === -1
      );
      if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
        this.openKeys = openKeys;
      } else {
        this.openKeys = latestOpenKey ? [latestOpenKey] : [];
      }
    };

    const menuClick = ({ key }) => {
      // 获取到当前的key,并且跳转
      this.$router.push({
        path: key,
      });
    };

    onMounted(() => {
      // colStyle();
    });

    watch(blueModel, (val) => {
      if (val) {
        colsStyle();
      } else {
        colStyle();
      }
    });
    return {
      state,
      blueModel,
      colorStyle,
      colorStyles,
      jumpTo,
      colStyle,
      colsStyle,
      isLogin,
      menuClick,
      gotoEditor,
      showAvatar,
      onOpenChange,
      toggleCollapsed,
    };
  },
});
</script>

<style scoped>
.menu {
  height: 50px;
  width: 50%;
  float: right;
  background-color: #d9d9d9;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.link {
  margin-right: 20px;
  display: inline-block;
  font-size: 20px;
  color: #08c;
  align-items: center;
}

.link :hover {
  background-color: rgb(246, 246, 246);
}
</style>