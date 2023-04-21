<template>
    <div class="menu">
        <contacts-list/>
        <q-btn @click="jumpTo">JumpTo</q-btn>
    </div>
</template>
  
<script setup>
import { useRouter } from "vue-router";
import ContactsList from "@/components/Personal/ContactsList.vue";
const router = useRouter();

const jumpTo = (path) => {
    router.push({
        name: path,
        params: {},
    });
};
</script>
  
<style scoped></style>