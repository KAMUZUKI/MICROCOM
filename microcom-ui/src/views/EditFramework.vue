<template>
    <div>
        <a-layout>
            <a-layout-content>
                <a-space :size="small" direction="horizontal" align="start">
                    <a-col>
                        <div style="border: 1px solid #ccc;text-align:left;height:800px;min-width:1000px;max-width:1100px">
                            <v-md-editor v-model="formState.user.content" :include-level="[3, 4]" :disabled-menus="[]"
                                @upload-image="handleUploadImage" height="800px">
                            </v-md-editor>
                        </div>
                    </a-col>
                    <a-col style="background-color:#fff;" flex="fix">
                        <a-card style="width: 300px;border-radius: 20px;" hoverable>
                            <a-form :model="formState" name="nest-messages" @finish="onFinish"
                                @onFinishFailed="onFinishFailed" :validate-messages="validateMessages">
                                <a-form-item :name="['user', 'createTime']" label="日期"
                                    :rules="[{ required: true, message: '选择日期' }]">
                                    <a-date-picker v-model:value="formState.user.createTime" show-time
                                        format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                                </a-form-item>
                                <a-form-item :name="['user', 'title']" label="文章标题"
                                    :rules="[{ required: true, message: '填写文章标题' }]">
                                    <a-input v-model:value="formState.user.title" />
                                </a-form-item>
                                <a-form-item :name="['user', 'description']" label="文章描述"
                                    :rules="[{ required: true, message: '填写文章描述' }]">
                                    <a-input v-model:value="formState.user.description" />
                                </a-form-item>
                                <a-form-item :name="['user', 'articleImg']" label="文章图片">
                                    <a-input v-model:value="formState.user.titleImgs" disabled placeholder="未上传则使用默认图片" />
                                </a-form-item>
                                <a-form-item :name="['user', 'categorys']" label="栏目选择">
                                    <a-select showSearch v-model:value="categoryOptions" mode="single" style="width: 100%"
                                        placeholder="请选择栏目" :options="categorys">
                                        {{ categorys }} 
                                    </a-select>
                                    {{ categoryOptions }}
                                </a-form-item>
                                <a-form-item :name="['user', 'keywords']" label="关键词">
                                    <a-select showSearch v-model:value="keywordOptions" mode="multiple" style="width: 100%"
                                        placeholder="请选择关键词" :options="keywords">
                                    </a-select>
                                </a-form-item>
                                <q-uploader :factory="factoryFn" @factory-failed="failed" max-files="1"
                                    color="blue" label="文章图片" field-name="file" :uploadProgressLabel="uplaodProcess"
                                    style="max-width: 250px;margin-bottom:20px;" />
                                <a-form-item>
                                    <a-button shape="round" type="primary" html-type="submit">提交</a-button>
                                </a-form-item>
                            </a-form>

                        </a-card>
                    </a-col>
                </a-space>
            </a-layout-content>
        </a-layout>
        <NotificationComponent ref="openNotification" />
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import NotificationComponent from '../components/tools/NotificationComponent.vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { useRoute } from 'vue-router'
import { onBeforeUnmount } from 'vue'
import { useStore } from 'vuex';
import { message } from 'ant-design-vue';
import upload from '@/js/api/upload'
import categoryApi from '@/js/api/category'
import articleApi from '@/js/api/article'

const router = useRouter()
const route = useRoute()
const store = useStore()
const openNotification = ref()
const categorys = ref([])
const keywords = ref([])
const tmpKeywords = ref([])
const keywordOptions = ref([])
const categoryOptions = ref()
const mode = ref(true)
const uplaodProcess = ref('10')
const formState = reactive({
    user: {
        author: '',
        title: '',
        content: '',
        keywords: [],
        description: '',
        category: '',
        titleImgs: '',
        createTime: '',
    }
})

const factoryFn = async (files) => {
  try {
    const res = await upload.uploadImage(files[0])
    if (res.code === 200) {
      uplaodProcess.value = '100'
      formState.user.titleImgs = res.data
      message.success("图片上传成功")
      return res.data
    } else {
      message.error(res.msg)
    }
  } catch (error) {
    console.error('文件上传失败：', error)
    message.error('文件上传失败，请重试')
  }
}

const failed = ()=>{
    message.success("图片上传失败")
}

const judgeMode = () => {
    const editInfo = route.query.articleId ?? '0'
    if (editInfo !== '0') {
        mode.value = false
        //TODO:通过articleId获取文章详情  修改文章
        // var params = new URLSearchParams();
        // params.append('articleId', editInfo);
        // axios.post(store.state.path + '/article/getArticleById', params).then(res => {
        //         if (res.data.code == 200) {
        //             formState.user = res.data.data
        //             keywordOptions.value = res.data.data.label.split(',')
        //             categoryOptions.value = JSON.parse(localStorage.getItem("categorys"))[res.data.data.categoryId]
        //             localStorage.setItem("articleDetail", JSON.stringify(formState));
        //         } else {
        //             console.log(res.data.msg)
        //         }
        //     })
        //     .catch(function (error) {
        //         console.log(error);
        //     });
        articleApi.getArticleById(editInfo).then(res=>{
            if(res.code == 200){
                formState.user = res.data
                keywordOptions.value = res.data.label.split(',')
                categoryOptions.value = JSON.parse(localStorage.getItem("categorys"))[res.data.categoryId]
                localStorage.setItem("articleDetail", JSON.stringify(formState));
            }else{
                console.log(res.msg)
            }
        })
    }
}

const handleUploadImage = async (event, insertImage, file) => {
    // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
    let res = await upload.uploadImage(file[0])
    if (res.code == 200) {
        insertImage({
            url: res.data,
            desc: '图片描述',
        });
        message.success(res.msg)
    } else {
        message.error(res.msg)
    }
}

const initData = ()=>{
    categoryApi.getCategory().then(res=>{
        if(res.code == 200){
            localStorage.setItem("categorys", JSON.stringify(res.data));
            Object.entries(res.data).forEach(([key, value]) => {
                categorys.value.push({ index: key, value: value.name })
            })
        }else{
            console.log(res.msg)
        }
    })
}

var ws
onMounted(() => {
    //撰写文章的分类以及关键词加载
    formState.user.content = formState.user.content ?? ''
    tmpKeywords.value = JSON.parse(localStorage.getItem("keywords")) ?? []
    Object.entries(tmpKeywords.value).forEach(([key, value]) => {
        keywords.value.push({ index: key, value: value })
    })
    
    initData()
    judgeMode()
    //连接websocket
    ws = new WebSocket(
        store.state.wspath + `/websocket`
    );
})

const onFinish = (values) => {
    console.log(formState.user)
    if (formState.user.content == null || formState.user.content == undefined || formState.user.content == '') {
        message.error('文章内容不能为空');
        return
    }
    console.log(values)
    var params = new URLSearchParams();
    if (mode.value) {
        params.append('author', JSON.parse(localStorage.getItem("user")).name);
        params.append('title', formState.user.title)
        params.append('content', formState.user.content)
        params.append('description', formState.user.description)
        params.append('categoryId', (JSON.parse(localStorage.getItem('categorys')).indexOf(categoryOptions.value)) + 1 ?? 1)
        params.append('label', keywordOptions.value ?? '')
        params.append('titleImgs', formState.user.titleImgs ?? JSON.parse(localStorage.getItem("user")).head)
        params.append('createTime', formState.user.createTime)
        axios.post(store.state.path + '/article/addArticle', params
        ,{
            headers:{
            'satoken': localStorage.getItem('tokeninfo')
            }
        }).then(res => {
                if (res.data.code == 200) {
                    formState.user.author = JSON.parse(localStorage.getItem("user")).name
                    formState.user.category = categoryOptions.value
                    formState.user.keywords = keywordOptions.value
                    let submitCode = JSON.stringify({
                        id: res.data.msg,
                        author: formState.user.author,
                        title: formState.user.title,
                        content: formState.user.content,
                        description: formState.user.description,
                        category: JSON.parse(localStorage.getItem('categorys')).indexOf(formState.user.category),
                        keywords: formState.user.keywords,
                        titleImgs: formState.user.titleImgs,
                        createTime: formState.user.createTime,
                        colCnt: [0, 0, 0],
                    })
                    ws.send(submitCode)
                    message.success('文章发布成功')
                    router.push('/')
                } else {
                    message.error('文章发布失败')
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    } else {
        params.append('id', route.query.articleId);
        params.append('author', JSON.parse(localStorage.getItem("user")).name);
        params.append('title', formState.user.title)
        params.append('content', formState.user.content)
        params.append('description', formState.user.description)
        params.append('categoryId', (JSON.parse(localStorage.getItem('categorys')).indexOf(categoryOptions.value)) + 1 ?? 1)
        params.append('label', keywordOptions.value ?? '')
        params.append('titleImgs', formState.user.titleImgs ?? JSON.parse(localStorage.getItem("user")).head)
        params.append('createTime', formState.user.createTime)
        axios.post(store.state.path + '/article/alterArticle', params
        ,{
            headers:{
            'satoken': localStorage.getItem('tokeninfo')
            }
        }).then(res => {
                if (res.data.code == 200) {
                    message.success('文章修改成功')
                    router.push('/')
                } else {
                    message.error('文章修改失败')
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
};

const onFinishFailed = errorInfo => {
    console.log('Failed:', errorInfo);
};

const validateMessages = {
    required: '${label} is required!',
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {

})
</script>

<style scoped>
.q-uploader__header {
    background-color: #0395cf !important
}
</style>