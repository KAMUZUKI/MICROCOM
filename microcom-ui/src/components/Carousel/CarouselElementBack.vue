<template>
    <!-- <el-card class="Carouselcard" style="width: 300px" shadow="hover"> -->
    <el-carousel autoplay=false class="carousel-card" indicator-position="outside" height="230px">
        <el-carousel-item v-for="item in initCarousel" :key="item.id" :label="item.info">
            <img width="300" height="200" :src="item.imgSrc" @click="toArticle(item.id)">
            <span>{{ item.description }}</span>
        </el-carousel-item>
    </el-carousel>
    <!-- </el-card> -->
</template>

<script>
import { onMounted, reactive, ref } from 'vue'
import { ElCarousel, ElCarouselItem } from 'element-plus'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useStore } from 'vuex'
export default {
    name: 'CarouselElement',
    components: {
        // ElCard,
        ElCarousel,
        ElCarouselItem
    },
    setup() {
        const router = useRouter()
        const store= useStore();
        const imgList = ref([
            {
                id: 1,
                imgSrc: require('../../static/image/lunbo1.jpg'),
            },
            {
                id: 2,
                imgSrc: require('../../static/image/lunbo2.jpg'),
            },
            {
                id: 3,
                imgSrc: require('../../static/image/lunbo3.jpg'),
            }
        ])

        const listData = ref([]);
        const listDataTemp = ref([]);
        const initCarousel = reactive([])

        const getImage = () => {
            //TODO:获取阅读数最高的三篇文章
            axios.post(store.state.path+"/article/getArticleTop")
                .then(res => {
                    if (res.data.code == 1) {
                        listDataTemp.value = res.data.data
                        for (const [key, value] of Object.entries(listDataTemp.value)) {
                          console.log(key)
                          listData.value.push(value);
                        }
                        
                        listData.value.forEach((item,i = 0) => {
                            initCarousel.push({
                                id: item.id,
                                imgSrc: imgList.value[i].imgSrc,
                                info: item.label,
                                description: item.title.substring(0, 40)+'...'
                            })
                            i++
                        })
                    } else {
                        console.log(res.data.msg)
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }

        //跳转到文章详情页
        const toArticle = (articleId) => {
            router.push({
                path: '/article/' + articleId,
                query: {
                    mode: 1
                }
            })
        }

        onMounted(() => {
            getImage()
        })

        return {
            initCarousel,
            listData,
            toArticle
        }
    }
}
</script>

<style scoped>
.el-carousel__item h3 {
    border-radius: 5px;
    display: flex;
    color: #475669;
    opacity: 0.75;
    margin: 0;
}

.el-card :deep() .el-card__body {
    padding: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}

.carousel-card{
    width: 300px;
    border-radius: 10px;
    background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
    border-radius: 10px;
    box-shadow:  19px 19px 37px #bebebe,
             -19px -19px 37px #ffffff;
}

</style>