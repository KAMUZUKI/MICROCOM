<template>
    <div class="q-pa-md q-gutter-md container">
        <q-infinite-scroll :debounce="2000" @load="onLoad" :offset="100">
            <div class="row justify-between">
                <div class="scrollDist"></div>
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
                            y="200">
                            EXPLORE
                        </text>
                        <polyline class="arrow" fill="#fff"
                            points="599,250 599,289 590,279 590,282 600,292 610,282 610,279 601,289 601,250" />

                        <g mask="url(#m)">
                            <rect fill="#fff" width="100%" height="100%" />
                            <text class="text" font-size="99px" font-family="'Montserrat', sans-serif" x="350" y="200"
                                fill="#162a43">
                                FURTHER
                            </text>
                        </g>
                        <rect id="arrowBtn" @click="moveToDown" @mouseenter="mouseenter" @mouseleave="mouseleave"
                            width="100" height="100" opacity="0" x="550" y="220" style="cursor: pointer" />
                    </svg>
                    <div id="position-hook"></div>
                    <!--mian start-->
                    <div class="row justify-center q-gutter-sm vlog-container">
                        <Waterfall :list="cards" :width="300" :gutter="20" :animationEffect="fadeInUp"
                            :animation-delay="2000" :animation-duration="2000">
                            <template #item="{ item }">
                                <div class="card" @click="showDialog(item)">
                                    <div class="card-header">
                                        <LazyImg :url="utils.getImg(item.img)[0]" :alt="utils.getImg(item.img)[0]" />
                                    </div>
                                    <div class="card-body">
                                        <div style="display: flex" v-if="item.label !== null">
                                            <template v-for="(tag, index) in item.label.split(',')" :key="index">
                                                <span class="tag tag-teal">{{ tag }}</span>
                                            </template>
                                        </div>
                                        <h6>{{ item.id + item.title }}</h6>
                                        <p>{{ item.text.substring(0, 200) }}.....</p>
                                        <div class="user">
                                            <img :src="item.head" alt="" />
                                            <div class="user-info">
                                                <h5>{{ item.name }}</h5>
                                                {{ utils.parseDateToPast(new Date(item.time)) }}
                                                <small>{{ utils.parseDate(item.time) }}</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </template>
                            <!-- </q-intersection> -->
                        </Waterfall>
                    </div>
                </div>
            </div>
            <template #loading>
                <loading-comp />
            </template>
        </q-infinite-scroll>
        <personal-dialog ref="dialogRef" />
        <div v-if="showDataFlag" style="text-align: center">
            <p>没有更多数据了...</p>
        </div>
        <!--vlog添加-->
        <q-page-sticky v-if="buttonVisiable" class="add-button" position="bottom-right">
            <q-fab @click="seamless = !seamless" icon="add" color="accent"> </q-fab>
        </q-page-sticky>
        <q-dialog v-model="seamless" seamless position="bottom">
            <vlog-compose></vlog-compose>
        </q-dialog>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { gsap, ScrollTrigger, ScrollToPlugin } from "gsap/all";
import { message } from "ant-design-vue";
import { LazyImg, Waterfall } from "vue-waterfall-plugin-next";
import LoadingComp from "@/components/tools/LoadingComp.vue";
import PersonalDialog from "@/components/Detail/DetailCard.vue";
import utils from "@/js/utils/utils";
import VlogCompose from "@/components/Vlog/VlogCompose.vue";
import api from "@/js/api/vlog";
import "vue-waterfall-plugin-next/dist/style.css";
import "animate.css";

const showDataFlag = ref(false)
const cards = ref([])
const dialogRef = ref(null)
const currentIndex = ref(1)

const showDialog = (item) => {
    dialogRef.value.show(item);
};

//addvlog start
const buttonVisiable = ref(false)
const seamless = ref(false)

//处理添加vlog按钮的显示与隐藏
const handleScroll = () => {
    const scrollDistance = window.scrollY;
    const targetDistance = 1000;
      if (scrollDistance > targetDistance) {
        // 当滚动距离大于 1000 时调用方法
        buttonVisiable.value = true;
      } else {
        buttonVisiable.value = false;
        seamless.value = false;
      }
}
//addvlog end

const moveToDown = () => {
    const targetElement = document.getElementById("position-hook");
    const targetPosition = targetElement.offsetTop;
    gsap.to(window, {
        scrollTo: { y: targetPosition },
        duration: 1.5,
        ease: "power1.inOut",
    });
    // scrollTo requires the ScrollTo plugin (not to be confused w/ ScrollTrigger)
};

const mouseenter = () => {
    gsap.to(".arrow", {
        y: 10,
        duration: 0.8,
        ease: "back.inOut(3)",
        overwrite: "auto",
    });
};

const mouseleave = () => {
    gsap.to(".arrow", {
        y: 0,
        duration: 0.5,
        ease: "power3.out",
        overwrite: "auto",
    });
};

const scrollTrigger = () => {
    gsap.registerPlugin(ScrollTrigger, ScrollToPlugin);
    gsap.set(".header-scroll", {
        position: "absolote",
        background: "#fff",
        width: "100%",
        maxWidth: "100%",
        height: "100%",
        top: 0,
    });
    gsap.set(".scrollDist", { width: "100%", height: "100%" });
    gsap
        .timeline({
            scrollTrigger: {
                trigger: ".header-scroll",
                start: "top top",
                end: "bottom 100%",
                scrub: 1,
            },
        })
        .fromTo(".sky", { y: 0 }, { y: -200 }, 0)
        .fromTo(".cloud1", { y: 100 }, { y: -800 }, 0)
        .fromTo(".cloud2", { y: -150 }, { y: -500 }, 0)
        .fromTo(".cloud3", { y: -50 }, { y: -650 }, 0)
        .fromTo(".mountBg", { y: -10 }, { y: -100 }, 0)
        .fromTo(".mountMg", { y: -30 }, { y: -250 }, 0)
        .fromTo(".mountFg", { y: -50 }, { y: -600 }, 0)
        .fromTo(".text", { y: 0 }, { y: 600 }, 0);
}

const onLoad = (index, done) => {
    if (showDataFlag.value) {
        done();
        return;
    }
    setTimeout(async () => {
        var res = await api.findWithPage(8, currentIndex.value); // Your asynchronous data retrieval method
        if (res.code == "ERR_NETWORK") {
            message.error("网络错误,请稍后重试");
            done();
            return;
        }
        //没有更多数据了
        if (res == null || res == undefined) {
            done();
            showDataFlag.value = true;
            return;
        }
        cards.value.push(...res.data);
        currentIndex.value++
        await done();
    }, 1000);
};

onMounted(() => {
    scrollTrigger()
    window.addEventListener('scroll', handleScroll);
})

onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll);
})
</script>

<style lang="sass" scoped>
.add-button
    margin-bottom: 50px
    margin-right: 50px

.vlog-container
    max-width: 1400px
    margin: 0 auto

.example-item
    width: 300px

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
    transition: 1s;
}

.card:hover {
    background-color: rgba(0, 0, 0, 0.2);
    transition: all 1s;
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
    margin: 0 5px;
    padding: 2px 10px;
    text-transform: uppercase;
}

.tag-teal {
    background-color: #92d4e4;
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

@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@900&display=swap");

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