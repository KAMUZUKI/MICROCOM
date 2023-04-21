<template>
    <div>
        <h1>TestDev</h1>
        <p>TestDev</p>
    </div>
</template>

<script>
import { defineComponent,ref} from "vue";
export default defineComponent({
    setup() {
        const list = ref([])
        const data = ref([])
        list.value = data.value.concat([...new Array(3)].map(() => ({
            loading: true,
            name: {},
            picture: {},
          })));
        return {};
    },
})
</script>

<style>

</style>