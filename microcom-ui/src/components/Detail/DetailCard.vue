<template>
    <q-card class="profile-card" >
        <div class="scroll-img">
            <q-carousel class="carousel" swipeable animated arrows v-model="slide" v-model:fullscreen="fullscreen"
                infinite>
                <template v-for="(img,index) in imgs" :key="index">
                    <q-carousel-slide :name="index+1" :img-src=img />
                </template>
                <template v-slot:control>
                    <q-carousel-control position="bottom-right" :offset="[18, 18]">
                        <q-btn push round dense color="white" text-color="primary"
                            :icon="fullscreen ? 'fullscreen_exit' : 'fullscreen'" @click="fullscreen = !fullscreen" />
                    </q-carousel-control>
                </template>
            </q-carousel>
        </div>
        <div class="profile-bio">
            <div class="note-scroller">
                <profile-comment :detail="detail"></profile-comment>
            </div>
        </div>
    </q-card>
</template>

<script setup>
import { onMounted, ref, defineProps } from "vue";
import ProfileComment from "./ProfileComment.vue";

const slide = ref(1)
const fullscreen = ref(false)
const detail = ref(null)
const imgs = ref([
    "https://cdn.quasar.dev/img/mountains.jpg",
    "https://cdn.quasar.dev/img/parallax1.jpg",
    "https://cdn.quasar.dev/img/parallax2.jpg",
    "https://cdn.quasar.dev/img/quasar.jpg"])

const props = defineProps({
    item: {
        type: Object,
        default: null,
    },
})

onMounted(() => {
    detail.value = props.item
    imgs.value = [] // 清空图片数组
    imgs.value.push(...detail.value.img.split(',')) // 将图片串换为图片数组
})
</script>

<style scoped>
.carousel {
    height: 100%;
}

.note-scroller {
    height: 100%;
    overflow: hidden;
    clear: both;
}

.profile-card {
    max-width: 80vw;
    width: 900px;
    height: 580px;
    position: absolute;
    overflow: hidden;
    display: flex;
    text-align: left;
    border: 1px solid #e0e0e0;
    border-radius: 20px;
    box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.16), 0px 3px 6px rgba(0, 0, 0, 0.23);
}

.scroll-img {
    border-right: 2px dashed #eeeeee;
    flex: 1;
    background: #ffffff;
}

.scroll-img>img {
    max-width: 100%;
}

.profile-bio {
    background: #ffffff;
    flex: 1;
}

</style>