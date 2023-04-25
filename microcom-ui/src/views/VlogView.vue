<template>
    <div class="q-pa-md q-gutter-md container">
        <q-infinite-scroll @load="onLoad" :offset="250">
            <div class="row justify-between">
                <!-- <q-parallax src="../static/image/vlog.jpg">
                    <h1 class="text-white">Vlog</h1>
                </q-parallax> -->
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
                                      <img :src=item.img alt="" />
                                    </div>
                                    <div class="card-body">
                                      <span class="tag tag-teal">Technology</span>
                                      <h4>{{ item.title }}</h4>
                                      <p>
                                        {{ item.text }}
                                      </p>
                                      <div class="user">
                                        <img src="https://upload.wikimedia.org/wikipedia/commons/4/48/Outdoors-man-portrait_%28cropped%29.jpg" alt="" />
                                        <div class="user-info">
                                          <h5>{{ item.author }}</h5>
                                          <small>2h ago</small>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                            </div>
                        </div>
                    </div>
                    </div>  
                </div>
                <template v-slot:loading>
                    <loading-comp/>
                </template>
        </q-infinite-scroll>
        <q-dialog class="dialog" v-model="basic">
            <detail-card></detail-card>
        </q-dialog>
    </div>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { gsap, ScrollTrigger, ScrollToPlugin } from "gsap/all"
import LoadingComp from '@/components/tools/LoadingComp.vue';
import DetailCard from '@/components/Detail/DetailCard.vue';

const basic = ref(false)

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

const cards = ref([
    {
        title: "1Strange Things",
        author: "by Jane Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://cdn.quasar.dev/img/parallax2.jpg",
    },
    {
        title: "2The Big Bang",
        author: "by John Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
    },
    {
        title: "3The Big Bang",
        author: "by John Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
    },
    {
        title: "4The Big Bang",
        author: "by John Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
    },
    {
        title: "5The Big Bang",
        author: "by John Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
    },
    {
        title: "6The Big Bang",
        author: "by John Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
    },
    {
        title: "7Strange Things",
        author: "by Jane Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://cdn.quasar.dev/img/parallax2.jpg",
    },
    {
        title: "8Strange Things",
        author: "by Jane Doe",
        text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        img: "https://cdn.quasar.dev/img/parallax2.jpg",
    }
]);
const onLoad = (index, done) => {
    setTimeout(() => {
        cards.value.push(
            {
                title: "1Strange Things",
                author: "by Jane Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://cdn.quasar.dev/img/parallax2.jpg",
            },
            {
                title: "2The Big Bang",
                author: "by John Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
            },
            {
                title: "3The Big Bang",
                author: "by John Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
            },
            {
                title: "4The Big Bang",
                author: "by John Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
            },
            {
                title: "5The Big Bang",
                author: "by John Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
            },
            {
                title: "6The Big Bang",
                author: "by John Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elitLorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_412,c_fill,g_auto,h_232,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200305114843-01-edge-hudson-yards-observation-deck.jpg",
            },
            {
                title: "7Strange Things",
                author: "by Jane Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://cdn.quasar.dev/img/parallax2.jpg",
            },
            {
                title: "8Strange Things",
                author: "by Jane Doe",
                text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                img: "https://cdn.quasar.dev/img/parallax2.jpg",
            }
        )
        done()
    }, 2000)
}

const showDialog = (detail)=>{
    basic.value = true
    console.log(detail)
}
</script>

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
  from{
    letter-spacing: -50px;
    filter: blur(10px);
  }
  to{
    letter-spacing: 10px;
    filter: blur(0px);
  }
}
</style>