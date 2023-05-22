<template>
  <header>
    <div class="container">
      <div class="profile">
        <div class="profile-image">
          <img :src="userInfo.avatarUrl" style="width: 150px;height: 150px;" alt="头像" />
        </div>

        <div class="profile-user-settings">
          <h1 class="profile-user-name">{{ userInfo.username }}</h1>

          <button class="btn profile-edit-btn" @click="jumpTo()">编辑信息</button>

          <button class="btn profile-settings-btn" aria-label="profile settings">
            <i class="fas fa-cog" aria-hidden="true"></i>
          </button>
        </div>

        <div class="profile-stats">
          <ul>
            <li>
              <span class="profile-stat-count">{{ userInfo.posts }}</span> 作品
            </li>
            <li @click="showInfo(userInfo.id,1)">
              <span class="profile-stat-count">{{ userInfo.followers }}</span>
              粉丝
            </li>
            <li @click="showInfo(userInfo.id,0)">
              <span class="profile-stat-count">{{ userInfo.following }}</span>
              关注
            </li>
          </ul>
        </div>

        <div class="profile-bio">
          <p>
            <span class="profile-real-name">{{ userInfo.username }}</span>
            {{ userInfo.signs }}
          </p>
        </div>
      </div>
      <!-- End of profile section -->
    </div>
    <!-- End of container -->
  </header>

  <personal-follow ref="showFollow"></personal-follow>

  <main>
    <div class="container">
      <q-infinite-scroll :debounce="2000" @load="onLoad" :offset="100">
        <div class="gallery">
          <q-intersection v-for="item,index in list" :key="index" once transition="scale" class="example-item">
            <div class="gallery-item" tabindex="0" @click="showDetail(item)">
              <img :src=utils.getImg(item.img)[0] class="gallery-image" alt="" />
              <div class="gallery-item-info">
                <div class="views">
                  <span><svg width="30" height="30" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path
                        d="M24 36C35.0457 36 44 24 44 24C44 24 35.0457 12 24 12C12.9543 12 4 24 4 24C4 24 12.9543 36 24 36Z"
                        fill="none" stroke="#ffffff" stroke-width="4" stroke-linejoin="round" />
                      <path
                        d="M24 29C26.7614 29 29 26.7614 29 24C29 21.2386 26.7614 19 24 19C21.2386 19 19 21.2386 19 24C19 26.7614 21.2386 29 24 29Z"
                        fill="none" stroke="#ffffff" stroke-width="4" stroke-linejoin="round" />
                    </svg></span>
                  <span>{{ }}</span>
                </div>
                <div class="love">
                  <svg width="30" height="30" viewBox="0 0 48 48" fill="#ffffff" xmlns="http://www.w3.org/2000/svg">
                    <path
                      d="M4.18898 22.1733C4.08737 21.0047 5.00852 20 6.18146 20H10C11.1046 20 12 20.8954 12 22V41C12 42.1046 11.1046 43 10 43H7.83363C6.79622 43 5.93102 42.2068 5.84115 41.1733L4.18898 22.1733Z"
                      stroke-width="4" stroke-linecap="round" stroke-linejoin="round" />
                    <path
                      d="M18 21.3745C18 20.5388 18.5194 19.7908 19.2753 19.4345C20.9238 18.6574 23.7329 17.0938 25 14.9805C26.6331 12.2569 26.9411 7.33595 26.9912 6.20878C26.9982 6.05099 26.9937 5.89301 27.0154 5.73656C27.2861 3.78446 31.0543 6.06492 32.5 8.47612C33.2846 9.78471 33.3852 11.504 33.3027 12.8463C33.2144 14.2825 32.7933 15.6699 32.3802 17.0483L31.5 19.9845H42.3569C43.6832 19.9845 44.6421 21.2518 44.2816 22.5281L38.9113 41.5436C38.668 42.4051 37.8818 43 36.9866 43H20C18.8954 43 18 42.1046 18 41V21.3745Z"
                      stroke-width="4" stroke-linecap="round" stroke-linejoin="round" />
                  </svg>
                  <span>{{ }}</span>
                </div>
                <div class="comments">
                  <svg width="30" height="30" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M33 38H22V30H36V22H44V38H39L36 41L33 38Z" stroke="#ffffff" stroke-width="4"
                      stroke-linecap="round" stroke-linejoin="round" />
                    <path d="M4 6H36V30H17L13 34L9 30H4V6Z" fill="none" stroke="#ffffff" stroke-width="4"
                      stroke-linecap="round" stroke-linejoin="round" />
                    <path d="M12 22H18" stroke="#ffffff" stroke-width="4" stroke-linecap="round" />
                    <path d="M12 14H24" stroke="#ffffff" stroke-width="4" stroke-linecap="round" />
                  </svg>
                  <span>{{ }}</span>
                </div>
              </div>
            </div>
          </q-intersection>
          <!-- End of gallery -->
        </div>
        <template v-slot:loading>
        <loading-comp />
      </template>
      </q-infinite-scroll>
    </div>
    
    <detail-card ref="dialogRef" />
    <div v-if="showDataFlag" style="text-align: center;">
      <p>没有更多数据了...</p>
    </div>
    <!-- End of container -->
  </main>
</template>
  \
<script setup>
import { onMounted, ref } from "vue";
import LoadingComp from "@/components/tools/LoadingComp.vue"
import DetailCard from '@/components/Detail/DetailCard.vue'
import PersonalFollow from "@/components/Profile/PersonalFollow.vue"
import vlogApi from "@/js/api/vlog"
import utils from "@/js/utils/utils"
import { useRouter } from 'vue-router'

//显示数据是否显示完全的标记
const showDataFlag = ref(false)
const dialogRef = ref(null)
const showFollow = ref(null)
const router = useRouter()
const currentIndex = ref(1)

const showDetail = (item) => {
  dialogRef.value.show(item)
}

const showInfo = (id,mode)=>{
  showFollow.value.showFollow(id,mode)
}

const user = JSON.parse(localStorage.getItem("user"))

const userInfo = {
  id: 1,
  username: user.name,
  avatarUrl: user.head,
  posts: 32,
  followers: 188,
  following: 206,
  signs: "I'm a photographer and a web developer.",
};

const jumpTo = () => {
  router.push({
    path: '/personal'
  })
}

// const agreePost = (item) => {
//   item.isLiked = !item.isLiked;
//   item.likes += ((item.isLiked) ? 1 : -1);
// };

const list = ref([]);

const onLoad = async (index, done) => {
  if (showDataFlag.value) {
    done()
    return
  }
  setTimeout(async () => {
    let res = await vlogApi.findWithPageById(1,9,currentIndex.value)
    if (res.code == 200) {
      list.value.push(...res.data)
      await done()
      currentIndex.value++
      return
    }
    showDataFlag.value = true
    done();
  }, 500);
};

onMounted( async ()=>{
  let res = await vlogApi.findWithPageById(1,9,currentIndex.value)
  if (res.code == 200) {
    list.value.push(...res.data)
    currentIndex.value++
    return
  }
})
</script>
  
<style scoped>
.liked {
  fill: #f00;
}

:root {
  font-size: 10px;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font-family: "Open Sans", Arial, sans-serif;
  min-height: 100vh;
  background-color: #fafafa;
  color: #262626;
  padding-bottom: 3rem;
}

img {
  display: block;
}

.container {
  max-width: 93.5rem;
  margin: 0 auto;
  padding: 0 2rem;
}

.btn {
  display: inline-block;
  font: inherit;
  background: none;
  border: none;
  color: inherit;
  padding: 0;
  cursor: pointer;
}

.btn:focus {
  outline: 0.5rem auto #4d90fe;
}

.visually-hidden {
  position: absolute !important;
  height: 1px;
  width: 1px;
  overflow: hidden;
  clip: rect(1px, 1px, 1px, 1px);
}

/* Profile Section */

.profile {
  padding: 5rem 0;
}

.profile::after {
  content: "";
  display: block;
  clear: both;
}

.profile-image {
  float: left;
  width: calc(33.333% - 1rem);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 3rem;
}

.profile-image img {
  border-radius: 50%;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
  float: left;
  width: calc(66.666% - 2rem);
}

.profile-user-settings {
  margin-top: 1.1rem;
}

.profile-user-name {
  display: inline-block;
  font-size: 3.2rem;
  font-weight: 300;
}

.profile-edit-btn {
  font-size: 1.4rem;
  line-height: 1.8;
  border: 0.1rem solid #dbdbdb;
  border-radius: 0.3rem;
  padding: 0 2.4rem;
  margin-left: 2rem;
}

.profile-settings-btn {
  font-size: 2rem;
  margin-left: 1rem;
}

.profile-stats {
  margin-top: 2.3rem;
}

.profile-stats li {
  display: inline-block;
  font-size: 1.6rem;
  line-height: 1.5;
  margin-right: 4rem;
  cursor: pointer;
}

.profile-stats li:last-of-type {
  margin-right: 0;
}

.profile-bio {
  font-size: 1.6rem;
  font-weight: 400;
  line-height: 1.5;
  margin-top: 2.3rem;
}

.profile-real-name,
.profile-stat-count,
.profile-edit-btn {
  font-weight: 600;
}

/* Gallery Section */

.gallery {
  display: flex;
  flex-wrap: wrap;
  margin: -1rem -1rem;
  padding-bottom: 3rem;
}

.gallery-item {
  position: relative;
  flex: 1 0 22rem;
  margin: 1rem;
  color: #fff;
  cursor: pointer;
  overflow: hidden;
  height: 22rem;
}

.gallery-item:hover .gallery-item-info,
.gallery-item:focus .gallery-item-info {
  display: flex;
  justify-content: space-around;
  vertical-align: middle;
  align-items: center;
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
}

.gallery-item-info {
  display: none;
}

.gallery-item-info li {
  display: inline-block;
  font-size: 1.7rem;
  font-weight: 600;
}

.gallery-item-likes {
  margin-right: 2.2rem;
}

.gallery-item-type {
  position: absolute;
  top: 1rem;
  right: 1rem;
  font-size: 2.5rem;
  text-shadow: 0.2rem 0.2rem 0.2rem rgba(0, 0, 0, 0.1);
}

.fa-clone,
.fa-comment {
  transform: rotateY(180deg);
}

.gallery-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Media Query */
@media screen and (max-width: 40rem) {
  .profile {
    display: flex;
    flex-wrap: wrap;
    padding: 4rem 0;
  }

  .profile::after {
    display: none;
  }

  .profile-image,
  .profile-user-settings,
  .profile-bio,
  .profile-stats {
    float: none;
    width: auto;
  }

  .profile-image img {
    width: 7.7rem;
  }

  .profile-user-settings {
    flex-basis: calc(100% - 10.7rem);
    display: flex;
    flex-wrap: wrap;
    margin-top: 1rem;
  }

  .profile-user-name {
    font-size: 2.2rem;
  }

  .profile-edit-btn {
    order: 1;
    padding: 0;
    text-align: center;
    margin-top: 1rem;
  }

  .profile-edit-btn {
    margin-left: 0;
  }

  .profile-bio {
    font-size: 1.4rem;
    margin-top: 1.5rem;
  }

  .profile-edit-btn,
  .profile-bio,
  .profile-stats {
    flex-basis: 100%;
  }

  .profile-stats {
    order: 1;
    margin-top: 1.5rem;
  }

  .profile-stats ul {
    display: flex;
    text-align: center;
    padding: 1.2rem 0;
    border-top: 0.1rem solid #dadada;
    border-bottom: 0.1rem solid #dadada;
  }

  .profile-stats li {
    font-size: 1.4rem;
    flex: 1;
    margin: 0;
  }

  .profile-stat-count {
    display: block;
  }
}

/* Spinner Animation */

@keyframes loader {
  to {
    transform: rotate(360deg);
  }
}

/*
  
  The following code will only run if your browser supports CSS grid.
  
  Remove or comment-out the code block below to see how the browser will fall-back to flexbox & floated styling. 
  
  */

@supports (display: grid) {
  .profile {
    display: grid;
    grid-template-columns: 1fr 2fr;
    grid-template-rows: repeat(3, auto);
    grid-column-gap: 3rem;
    align-items: center;
  }

  .profile-image {
    grid-row: 1 / -1;
  }

  .gallery {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(22rem, 1fr));
    grid-gap: 2rem;
  }

  .profile-image,
  .profile-user-settings,
  .profile-stats,
  .profile-bio,
  .gallery-item,
  .gallery {
    width: auto;
    margin: 0;
  }

  @media (max-width: 40rem) {
    .profile {
      grid-template-columns: auto 1fr;
      grid-row-gap: 1.5rem;
    }

    .profile-image {
      grid-row: 1 / 2;
    }

    .profile-user-settings {
      display: grid;
      grid-template-columns: auto 1fr;
      grid-gap: 1rem;
    }

    .profile-edit-btn,
    .profile-stats,
    .profile-bio {
      grid-column: 1 / -1;
    }

    .profile-user-settings,
    .profile-edit-btn,
    .profile-settings-btn,
    .profile-bio,
    .profile-stats {
      margin: 0;
    }
  }
}
</style>