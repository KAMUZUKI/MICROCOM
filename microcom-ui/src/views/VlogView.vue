<template>
    <div class="q-pa-md q-gutter-md container">
        <q-infinite-scroll :debounce="2000" @load="onLoad" :offset="800">
            <div class="row justify-between">
                <div class="scrollDist">
                </div>
                <div class="header-scroll">
                    <svg viewBox="0 0 1200 800" xmlns="http://www.w3.org/2000/svg">
                        <mask id="m">
                            <g class="cloud1">
                                <rect fill="#fff" width="100%" height="801" y="799" />
                                <image xlink:href="https://assets.codepen.io/721952/cloud1Mask.jpg" width="1200"
                                    height="800" />
                            </g>
                        </mask>
                        <image class="sky" xlink:href="https://assets.codepen.io/721952/sky.jpg" width="1200"
                            height="590" />
                        <image class="mountBg" xlink:href="https://assets.codepen.io/721952/mountBg.png" width="1200"
                            height="800" />
                        <image class="mountMg" xlink:href="https://assets.codepen.io/721952/mountMg.png" width="1200"
                            height="800" />
                        <image class="cloud2" xlink:href="https://assets.codepen.io/721952/cloud2.png" width="1200"
                            height="800" />
                        <image class="mountFg" xlink:href="https://assets.codepen.io/721952/mountFg.png" width="1200"
                            height="800" />
                        <image class="cloud1" xlink:href="https://assets.codepen.io/721952/cloud1.png" width="1200"
                            height="800" />
                        <image class="cloud3" xlink:href="https://assets.codepen.io/721952/cloud3.png" width="1200"
                            height="800" />
                        <text class="text" font-size="99px" font-family="'Montserrat', sans-serif" fill="#fff" x="350"
                            y="200">EXPLORE</text>
                        <polyline class="arrow" fill="#fff"
                            points="599,250 599,289 590,279 590,282 600,292 610,282 610,279 601,289 601,250" />

                        <g mask="url(#m)">
                            <rect fill="#fff" width="100%" height="100%" />
                            <text class="text" font-size="99px" font-family="'Montserrat', sans-serif" x="350" y="200"
                                fill="#162a43">FURTHER</text>
                        </g>

                        <rect id="arrowBtn" @click="moveToDown" @mouseenter="mouseenter" @mouseleave="mouseleave"
                            width="100" height="100" opacity="0" x="550" y="220" style="cursor:pointer" />
                    </svg>
                    <div class="vlog-box justify-between">
                        <div id="layout" v-masonry="containerId" item-selector=".item" transition-duration="0.3s"
                            gutter="50" fit-width="true" stagger="0.03s">
                            <div v-masonry-tile class="item" v-for="(item, index) in cards" :key="index">
                                <div class="card" @click="showDialog(item)">
                                    <div class="card-header">
                                        <img :src=utils.getImg(item.img)[0] :alt=utils.getImg(item.img)[0] />
                                    </div>
                                    <div class="card-body">
                                        <span class="tag tag-teal">Technology</span>
                                        <h4>{{ item.id + item.title }}</h4>
                                        <p>
                                            {{ item.text.substring(0, 200) }}.....
                                        </p>
                                        <div class="user">
                                            <img :src=item.head alt="" />
                                            <div class="user-info">
                                                <h5>{{ item.name }}</h5>{{ utils.parseDateToPast(item.time) }}
                                                <small>{{ utils.parseDate(item.time) }}</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <template #loading>
                <loading-comp />
            </template>
        </q-infinite-scroll>
        <q-dialog class="dialog" v-model="basic">
            <q-card class="profile-card">
                <div class="scroll-img">
                    <q-carousel class="carousel" swipeable animated arrows v-model="slide" v-model:fullscreen="fullscreen"
                        infinite>
                        <template v-for="(img, index) in imgs" :key="index">
                            <q-carousel-slide :name="index + 1" :img-src=img />
                        </template>
                        <template v-slot:control>
                            <q-carousel-control position="bottom-right" :offset="[18, 18]">
                                <q-btn push round dense color="white" text-color="primary"
                                    :icon="fullscreen ? 'fullscreen_exit' : 'fullscreen'"
                                    @click="fullscreen = !fullscreen" />
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
        </q-dialog>
        <div v-if="showDataFlag" style="text-align: center;">
            <p>没有更多数据了...</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { gsap, ScrollTrigger, ScrollToPlugin } from "gsap/all"
import LoadingComp from '@/components/tools/LoadingComp.vue';
import ProfileComment from "@/components/Detail/ProfileComment.vue";
import api from '@/js/api/vlog'
import utils from '@/js/utils/utils'

const basic = ref(false)
const showDataFlag = ref(false)
const cards = ref([]);
const slide = ref(1)
const fullscreen = ref(false)
const detail = ref(null)

const imgs = ref([
    "https://cdn.quasar.dev/img/mountains.jpg",
    "https://cdn.quasar.dev/img/parallax1.jpg",
    "https://cdn.quasar.dev/img/parallax2.jpg",
    "https://cdn.quasar.dev/img/quasar.jpg"])

const findWithPage = async (page) => {
    return await api.findWithPage(page)
}

const moveToDown = () => {
    const targetElement = document.getElementById('layout');
    const targetPosition = targetElement.offsetTop;
    gsap.to(window, { scrollTo: { y: targetPosition }, duration: 1.5, ease: 'power1.inOut' });
    // scrollTo requires the ScrollTo plugin (not to be confused w/ ScrollTrigger)
}

const mouseenter = () => {
    gsap.to('.arrow', { y: 10, duration: 0.8, ease: 'back.inOut(3)', overwrite: 'auto' });
}

const mouseleave = () => {
    gsap.to('.arrow', { y: 0, duration: 0.5, ease: 'power3.out', overwrite: 'auto' });
}

onMounted(() => {
    gsap.registerPlugin(ScrollTrigger, ScrollToPlugin)
    gsap.set('.header-scroll', { position: 'absolote', background: '#fff', width: '100%', maxWidth: '100%', height: '100%', top: 0 })
    gsap.set('.scrollDist', { width: '100%', height: '100%' })
    gsap.timeline({ scrollTrigger: { trigger: '.header-scroll', start: 'top top', end: 'bottom 100%', scrub: 1 } })
        .fromTo('.sky', { y: 0 }, { y: -200 }, 0)
        .fromTo('.cloud1', { y: 100 }, { y: -800 }, 0)
        .fromTo('.cloud2', { y: -150 }, { y: -500 }, 0)
        .fromTo('.cloud3', { y: -50 }, { y: -650 }, 0)
        .fromTo('.mountBg', { y: -10 }, { y: -100 }, 0)
        .fromTo('.mountMg', { y: -30 }, { y: -250 }, 0)
        .fromTo('.mountFg', { y: -50 }, { y: -600 }, 0)
        .fromTo('.text', { y: 0 }, { y: 600 }, 0)
})

const onLoad = (index, done) => {
    Promise.resolve().then(async () => {
        try {
            var res = await findWithPage(index); // Your asynchronous data retrieval method
            if (res == null || res == undefined) {
                done();
                showDataFlag.value = true;
                return;
            }
            cards.value.push(...res);
            done();
        } catch (error) {
            console.error(error);
        }
    });
};

const showDialog = (vlog) => {
    basic.value = true
    detail.value = vlog
    imgs.value = [] // 清空图片数组
    imgs.value.push(...detail.value.img.split(',')) // 将图片串换为图片数组
}
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
<style lang="sass" scoped>
.q-pa-md
    padding: 0px !important

#layout
    width: 100%
    margin: 0 auto

.vlog-box
    // max-width: 2000px
    margin: 0 auto

.justify-between
    justify-content: space-around
    
.my-card
    width: 100%
    max-width: 250px
    
.item
    margin-top: 20px

p 
  text-align: start

body
  background-color: #f3fbfb
  font-family: "Open Sans", sans-serif
  display: flex
  align-items: center
  justify-content: center
  height: 100vh
  margin: 0
</style>

<style scoped>
/* Chrome Safari */
/* ::-webkit-scrollbar {
    display: none;

} */

.card {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    width: 300px;
}

.card-header img {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.card-body {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 20px;
    min-height: 250px;
}

.tag {
    background-color: #ccc;
    color: #fff;
    border-radius: 50px;
    font-size: 12px;
    margin: 0;
    padding: 2px 10px;
    text-transform: uppercase;
}

.tag-teal {
    background-color: #92d4e4;
}

.tag-purple {
    background-color: #3d1d94;
}

.tag-pink {
    background-color: #c62bcd;
}

.card-body h4 {
    margin: 10px 0;
}

.card-body p {
    font-size: 14px;
    margin: 0 0 40px 0;
    font-weight: 500;
    color: rgb(70, 68, 68);
}

.user {
    display: flex;
    margin-top: auto;
}

.user img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
    object-fit: cover;
}

.user-info h5 {
    margin: 0;
}

.user-info small {
    color: #888785;
}

@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@900&display=swap');

.text {
    animation: showup 1.5s forwards;
}

@keyframes showup {
    from {
        letter-spacing: -50px;
        filter: blur(10px);
    }

    to {
        letter-spacing: 10px;
        filter: blur(0px);
    }
}
</style>