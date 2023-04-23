<template>
    <a-card bordered style="width: 200px;border-radius: 20px;margin: 60px 0 0 68px;" hoverable>
        <div style="width: 100%" v-for="(item, index) in data" :key="index">
            <div class="list-item" v-if="index == 0">
                <router-link @click.prevent="showContentBycategory(index + 1)" to="/">
                    <a-list-item class="a-list-item">
                        <a-tag color="red">Top{{ index + 1 }}</a-tag>{{ item }}
                    </a-list-item>
                </router-link><br />
            </div>
            <div class="list-item" v-else-if="index == 1">
                <router-link @click.prevent="showContentBycategory(index + 1)" to="/">
                    <a-list-item class="a-list-item">
                        <a-tag color="cyan">Top{{ index + 1 }}</a-tag>{{ item }}
                    </a-list-item><br />
                </router-link>
            </div>
            <div class="list-item" v-else>
                <router-link @click.prevent="showContentBycategory(index + 1)" to="/">
                    <a-list-item class="a-list-item">
                        <a-tag color="green">Top{{ index + 1 }}</a-tag>{{ item }}
                    </a-list-item>
                </router-link><br />
            </div>
        </div>
    </a-card>
</template>

<style>
.list-item {
    width: 100%;
    height: 40px;
    text-align: left;
    border-radius: 10px;
}
</style>

<script>
import { defineComponent, ref } from 'vue';
import axios from 'axios'
import { useStore } from 'vuex'

export default defineComponent({
    name: 'MenuComponent',
    setup(props) {
        const data = ref([])
        const tmpData = ref([])
        const store = useStore()
        //TODO: 从后台获取分类
        axios.post(store.state.path+'/category/getCategory')
        .then(res=>{
            console.log(res.data)
          if (res.data.code == 200) {
            tmpData.value = res.data.data
            for (var i = 0; i < tmpData.value.length; i++) {
                data.value.push(tmpData.value[i].name)
            }
            sessionStorage.setItem('categorys', JSON.stringify(data.value))
          } else {
            console.log(res.data.msg)
          }
        })
        .catch(function (error) {
          console.log(error); 
        });


        const showContentBycategory = (type) => {
            props.showContentByCategory(type);
        }

        return {
            data,
            showContentBycategory
        };
    },
    props: {
        showContentByCategory: {
            type: Function,
        }
    }
});
</script>