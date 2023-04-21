
<template>
    <div class="scrollDist" ref="scrollDist">
    </div>
    <div class="main">
        <svg viewBox="0 0 1200 800" xmlns="http://www.w3.org/2000/svg">
            <mask id="m">
                <g class="cloud1">
                    <rect fill="#fff" width="100%" height="801" y="799" />
                    <image xlink:href="https://assets.codepen.io/721952/cloud1Mask.jpg" width="1200" height="800" />
                </g>
            </mask>

            <image class="sky" xlink:href="https://assets.codepen.io/721952/sky.jpg" width="1200" height="590" />
            <image class="mountBg" xlink:href="https://assets.codepen.io/721952/mountBg.png" width="1200" height="800" />
            <image class="mountMg" xlink:href="https://assets.codepen.io/721952/mountMg.png" width="1200" height="800" />
            <image class="cloud2" xlink:href="https://assets.codepen.io/721952/cloud2.png" width="1200" height="800" />
            <image class="mountFg" xlink:href="https://assets.codepen.io/721952/mountFg.png" width="1200" height="800" />
            <image class="cloud1" xlink:href="https://assets.codepen.io/721952/cloud1.png" width="1200" height="800" />
            <image class="cloud3" xlink:href="https://assets.codepen.io/721952/cloud3.png" width="1200" height="800" />
            <text font-size="99px" font-family="'Montserrat', sans-serif" fill="#fff" x="350" y="200">EXPLORE</text>
            <polyline class="arrow" fill="#fff"
                points="599,250 599,289 590,279 590,282 600,292 610,282 610,279 601,289 601,250" />

            <g mask="url(#m)">
                <rect fill="#fff" width="100%" height="100%" />
                <text font-size="99px" font-family="'Montserrat', sans-serif" x="350" y="200" fill="#162a43">FURTHER</text>
            </g>

            <rect id="arrowBtn" @click="moveToDown" @mouseenter="mouseenter" @mouseleave="mouseleave" width="100"
                height="100" opacity="0" x="550" y="220" style="cursor:pointer" />
        </svg>
    </div>
</template>

<script setup>
import { gsap, ScrollTrigger, ScrollToPlugin } from "gsap/all"
import { onMounted } from "vue"


const moveToDown = () => {
    gsap.to(window, { scrollTo: { y: '#layout' }, duration: 1.5, ease: 'power1.inOut' });
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
    gsap.set('.main', { background: '#fff', width: '100%', maxWidth: '1400px', height: '100%', top: 0 })
    gsap.set('.scrollDist', { width: '100%', height: '100%' })
    gsap.timeline({ scrollTrigger: { trigger: '.scrollDist', start: 'top top', end: 'bottom 20%', scrub: 1 } })
        .fromTo('.sky', { y: 0 }, { y: -200 }, 0)
        .fromTo('.cloud1', { y: 100 }, { y: -800 }, 0)
        .fromTo('.cloud2', { y: -150 }, { y: -500 }, 0)
        .fromTo('.cloud3', { y: -50 }, { y: -650 }, 0)
        .fromTo('.mountBg', { y: -10 }, { y: -100 }, 0)
        .fromTo('.mountMg', { y: -30 }, { y: -250 }, 0)
        .fromTo('.mountFg', { y: -50 }, { y: -600 }, 0)
})

</script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@900&display=swap');
</style>