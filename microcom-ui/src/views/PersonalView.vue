<template>
    <div class="q-pa-md" style="max-width:1000px;margin: 0 auto;">
        <div class="q-gutter-md row items-start justify-start">
            <q-list style="min-width: 100px">
                <q-item @click="jumpTo('/personal')" clickable>
                    <q-item-section>个人信息</q-item-section>
                </q-item>
                <q-item @click="jumpTo('/personal/timeline')" clickable>
                    <q-item-section>历程</q-item-section>
                </q-item>
                <q-separator />
                <q-item clickable>
                    <q-item-section>设置</q-item-section>
                </q-item>
                <q-separator />
                <q-item clickable>
                    <q-item-section>帮助 &amp; 反馈</q-item-section>
                </q-item>
            </q-list>
            <div class="container" style="min-width:400px">
                <router-view v-slot="{ Component }">
                  <transition :enter-active-class="`animate__animated animate__fadeInLeft`"
        :leave-active-class="`animate__animated animate__fadeOutLeft`" mode="out-in">
                    <component :is="Component" />
                  </transition>
                </router-view>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

const jumpTo = (path) => {
    router.push(path)
}
</script>
  
<style></style>