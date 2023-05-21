import { createRouter, createWebHistory } from 'vue-router'
import { message } from 'ant-design-vue';
// 1. 定义路由组件.
// 也可以从其他文件导入

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
    {
        path: '/',
        name: 'content',
        component: () => import('@/views/MainFramework.vue'),
        meta: {
            title: '首页',
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        },
        children: [
            {
                path: '/',
                name: 'articleList',
                component: () => import('@/components/Content/ContentComponent.vue'),
                meta: {
                    title: '文章列表',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            },
            {
                path: 'article/:id',
                name: 'article',
                component: () => import('@/components/Article/ArticleComponent.vue'),
                meta: {
                    title: '文章',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            },
            {
                path: 'about',
                name: 'about',
                component: () => import('@/components/About/AboutComponent.vue'),
                meta: {
                    title: '关于',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            }
        ]
    }, 
    {
        path: '/vlog',
        name: 'vlog',
        component: () => import('@/views/VlogView.vue'),
        meta: {
            title: '微动态',
            // requireAuth: true,
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        },
        props: true
    },
    {
        path: '/personal',
        name: 'personalview',
        component: () => import('@/views/PersonalView.vue'),
        meta: {
            title: '用户信息',
            // requireAuth: true,
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        },
        children: [
            {
                path: '',
                name: 'personalInfo',
                component: () => import('@/components/Profile/PersonalInfo.vue'),
                meta: {
                    title: '个人信息',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            },
            {
                path: 'timeline',
                name: 'timeline',
                component: () => import('@/components/Personal/TimeLine.vue'),
                meta: {
                    title: '时间线',
                    // requireAuth: true,
                    transition_enter: 'animate__fadeInLeft',
                    transition_leave: 'animate__fadeOutLeft',
                }
            },
        ]
    },
    {
        path: '/profileview',
        name: 'profileview',
        component: () => import('@/views/ProfileView.vue'),
        meta: {
            title: '用户主页',
            // requireAuth: true,
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        }
    },
    {
        path: '/searchview',
        name: 'searchview',
        component: () => import('@/views/SearchView.vue'),
        meta: {
            title: '搜索',
            // requireAuth: true,
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        }
    },
    {
        path: '/editor',
        name: 'editor',
        component: () => import('@/views/EditFramework.vue'),
        meta: {
            title: '编辑',
            // requireAuth: true,
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        }
    },
    {
        path: '/ChatView',
        name: 'chatview',
        component: () => import('@/views/ChatView.vue'),
        meta: {
            title: '聊天',
            // requireAuth: true,
            transition_enter: 'animate__fadeInLeft',
            transition_leave: 'animate__fadeOutLeft',
        }
    },
    {
        path: '/setting',
        name: 'setting',
        component: () => import('@/views/SettingFramework.vue'),
        meta: {
            title: '设置',
            // requireAuth: true,
            transition_enter: 'animate__fadeInRight',
            transition_leave: 'animate__fadeOutLeft',
        },
        children: [
            {
                path: 'add',
                name: 'add',
                component: () => import('@/components/Setting/AddProperties.vue'),
                meta: {
                    title: '添加',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            },
            {
                path: 'article',
                name: 'articlemanager',
                component: () => import('@/components/Setting/ArticleManager.vue'),
                meta: {
                    title: '文章管理',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            },
            {
                path: 'manager',
                name: 'manager',
                component: () => import('@/components/Setting/UserManager.vue'),
                meta: {
                    title: '管理用户',
                    transition_enter: 'animate__fadeInDown',
                    transition_leave: 'animate__fadeOutDown',
                }
            },
        ]
    },
    {
        path: '/status/404',
        name: 'status404',
        component: () => import('@/components/status/404Component.vue'),
        meta: {
            title: '404',
            transition_enter: 'animate__fadeInRight',
            transition_leave: 'animate__fadeOutLeft',
        }, 
    },
    {
        path: '/status/403',
        name: 'status403',
        component: () => import('@/components/status/403Component.vue'),
        meta: {
            title: '403',
            transition_enter: 'animate__fadeInRight',
            transition_leave: 'animate__fadeOutLeft',
        }, 
    },
    {
        path: '/:catchall(.*)',
        name: '404',
        component: () => import('@/components/status/404Component.vue'),
    }
]

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里a
// 暂时保持简单

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    mode: 'hash',
    history: createWebHistory(process.env.BASE_URL),
    base:'/dist/',
    routes // `routes: routes` 的缩写
})

const list = ['article', 'setflink', 'manager']
router.beforeEach((to,from,next)=>{
    //to 目标路由
    //from 来源
    //next 放行
    
    //如果需要验证，首页守卫
    if(to.meta.requireAuth){
      //vuex仓库中的信息是否存在
      if(sessionStorage.getItem('user')){
        //   setting/article
        if (list.includes(to.path.split('/')[2])) {
            if(JSON.parse(sessionStorage.getItem('user')).type == 1){
                next()
            }else{
                message.warning('您没有权限，请向管理员申请');
            }
        }else{
            next()
        }
      }else{
        //拦截路由
        message.warning('您没有权限，请先登录');
        next('/')
      }
    }else{//不需要验证，直接放行
      next()
    }
  })

export default router