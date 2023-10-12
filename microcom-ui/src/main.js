import { createApp } from 'vue';
import Antd from 'ant-design-vue';
import App from './App';
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'
import 'ant-design-vue/dist/antd.css';
import 'animate.css/animate.min.css';
import 'element-plus/dist/index.css'
import eventBus from 'vue3-eventbus'
//引入Element-plus icons
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//MARKED BEGIN
// 预览组件以及样式
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';

import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
// githubTheme主题以及样式（这里也可以选择github主题）
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// 代码高亮
import hljs from 'highlight.js';

//Quasar
import { Quasar } from 'quasar'
import quasarUserOptions from './quasar-user-options'

// 选择使用主题
VMdPreview.use(githubTheme, {
  Hljs: hljs,
});

VMdEditor.use(githubTheme, {
  Hljs: hljs,
  config: {
    toc: {
      includeLevel: [3, 4],
    },
  },
});
//MARKED END

const app = createApp(App).use(Quasar, quasarUserOptions);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(Antd)
  .use(router)
  .use(VMdEditor)
  .use(VueCookies)
  .use(VMdPreview)
  .use(store)
  .use(eventBus)
  .mount('#app');
