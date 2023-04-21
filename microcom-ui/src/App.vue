<template>
  <LayoutFramework></LayoutFramework>
</template>

<script>
import LayoutFramework from "./views/LayoutFramework.vue";
export default {
  name: "App",
  components: {
    LayoutFramework,
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
