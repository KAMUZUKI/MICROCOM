<template>
    <a-layout>
        <a-layout-content>
            <a-space :size="small" direction="horizontal" align="start">
                <a-col>
                    <a-card title="设置中心" style="width: 200px">
                        <a-button class="siderSelect" block>
                            <router-link class="nav-link" to="/setting/manager">
                                <comment-outlined :style="{ fontSize: '26px', color: '#08c' }" /><span>用户管理</span>
                            </router-link>
                        </a-button>
                        <a-button class="siderSelect" block>
                            <router-link class="nav-link" to="/setting/setflink">
                                <comment-outlined :style="{ fontSize: '26px', color: '#08c' }" /><span>设置友链</span>
                            </router-link>
                        </a-button>
                        <a-button class="siderSelect" block>
                            <router-link class="nav-link" to="/setting/article">
                                <comment-outlined :style="{ fontSize: '26px', color: '#08c' }" /><span>管理文章</span>
                            </router-link>
                        </a-button>
                        <a-button class="siderSelect" block>
                            <router-link class="nav-link" to="/setting/add">
                                <comment-outlined :style="{ fontSize: '26px', color: '#08c' }" /><span>添加分类</span>
                            </router-link>
                        </a-button>
                    </a-card>
                </a-col>
                <a-col style="background-color:#fff;" flex="fix">
                    <router-view #default="{ route, Component }">
                        <transition :enter-active-class="`animate__animated ${route.meta.transition_enter}`"
                            :leave-active-class="`animate__animated ${route.meta.transition_leave}`" mode="out-in">
                            <component :is="Component" ref="transContent"></component>
                        </transition>
                    </router-view>
                </a-col>
            </a-space>
        </a-layout-content>
    </a-layout>
</template>
<style scoped>
.siderSelect{
    background-color: #fff;
    border: 0;
    border-radius: 0;
    color: #000;
    font-size: 16px;
    font-weight: 400;
    height: 50px;
    line-height: 50px;
    margin: 0;
    padding: 0;
    text-align: left;
    width: 100%;
}
</style>
<script>
import { defineComponent } from 'vue';
export default defineComponent({
    name: 'SettingFramework',
})

</script>