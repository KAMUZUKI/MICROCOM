<template>
    <a-space style="margin-top: 10px" size="small" align="end" direction="horizontal">
        <div style="float:left;" v-for="(item, index) in data" :key="index">
            <div class="list-item" v-if="index == 0">
                <router-link @click.prevent="showContentBycategory(item)" to="/">
                    <a-list-item class="a-list-item">
                        <a-tag color="red">Top{{ index+1 }}</a-tag>{{ item }}
                    </a-list-item>
                </router-link><br />
            </div>
            <div class="list-item" v-else-if="index == 1">
                <router-link @click.prevent="showContentBycategory(item)" to="/">
                    <a-list-item class="a-list-item">
                        <a-tag color="cyan">Top{{ index+1 }}</a-tag>{{ item }}
                    </a-list-item><br/>
                </router-link>
            </div>
            <div class="list-item" v-else>
                <router-link @click.prevent="showContentBycategory(item)" to="/">
                    <a-list-item class="a-list-item">
                        <a-tag color="green">Top{{ index+1 }}</a-tag>{{ item }}
                    </a-list-item>
                </router-link><br />
            </div>
        </div>
    </a-space>
</template>

<style>
.list-item {
    width: 100%;
    height: 40px;
    text-align: left;
    border-radius: 10px;
    background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
    background: #a1c4fd;
}

.list-item:hover {
    background-image: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
}

</style>


<script>
import { defineComponent } from 'vue';
const data = ['JAVA技术', '.NET技术', '数据库技术', 'GO', 'PYTHON', 'C#'];
const data1 = [{ '1': 'JAVA技术' },
{ '2': '.NET技术' },
{ '3': '数据库技术' },
{ '4': 'GO' },
{ '5': 'PYTHON' },
{ '6': 'C#' }];

export default defineComponent({
    name: 'HeadLeftMenu',
    setup(props) {
        const showContentBycategory = (type)=>{
            console.log(type);
            props.showContentByCategory(type);
        }

        return {
            data,
            data1,
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