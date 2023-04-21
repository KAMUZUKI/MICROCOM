<template>
  <q-tabs class="text-dark shadow-2 navbar" align="center">
    <search-bar></search-bar>
    <q-route-tab to="/" label="首页" exact />
    <q-route-tab :to="{ name: 'vlog' }" label="微动态" exact />
    <q-route-tab :to="{ name: 'setting' }" label="设置" exact />
    <q-route-tab :to="{ name: 'editor' }" label="编辑" exact />
    <q-route-tab :to="{ name: 'profileview'}" label="个人中心" exact />
    <q-route-tab :to="{ name: 'searchview'}" label="搜索" exact />
  </q-tabs>
</template>
<script setup>
import SearchBar from "@/components/Header/SearchBar.vue";
</script>

<style>
.navbar{
  float: left;
}
</style>