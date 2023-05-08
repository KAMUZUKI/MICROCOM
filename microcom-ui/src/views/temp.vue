<template>
    <div v-for="(item, index) in cards" :key="index">
        <div class="card" @click="showDialog(item)">
        </div>
    </div>
    <q-dialog class="dialog" v-model="basic">
        <detail-card v-model="detail"></detail-card>
    </q-dialog>
</template>

<script>
export default {

}
</script>

<style>

</style>