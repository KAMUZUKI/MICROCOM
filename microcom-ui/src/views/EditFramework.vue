<template>
    <div>
        <a-layout>
            <a-layout-content>
                <a-space :size="small" direction="horizontal" align="start">
                    <a-col>
                        <div style="border: 1px solid #ccc;text-align:left;height:800px;min-width:1000px;max-width:1100px">
                            <v-md-editor v-model="formState.user.content" :disabled-menus="[]"
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
                                    <a-input v-model:value="formState.user.titleImgs" placeholder="空则使用默认图片" />
                                </a-form-item>
                                <a-form-item :name="['user', 'categorys']" label="栏目选择">
                                    <a-select showSearch v-model:value="categoryOptions" mode="single" style="width: 100%"
                                        placeholder="请选择栏目" :options="categorys">
                                    </a-select>
                                </a-form-item>
                                <a-form-item :name="['user', 'keywords']" label="关键词">
                                    <a-select showSearch v-model:value="keywordOptions" mode="multiple" style="width: 100%"
                                        placeholder="请选择关键词" :options="keywords">
                                    </a-select>
                                </a-form-item>
                                <a-form-item>
                                    <a-button shape="round" type="primary" html-type="submit">提交</a-button>
                                </a-form-item>
                            </a-form>
                            <q-uploader
                                url="http://localhost:8080/microcom/upload/test"
                                label="Individual upload"
                                field-name="image"
                                style="max-width: 300px"
                              />
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

// const props = defineProps({
//     openNotificationWithIcon: {
//         type: Function,
//         required: true,
//     },
// })

const router = useRouter()
const route = useRoute()
const store = useStore()
const openNotification = ref()
const categorys = ref([])
const keywords = ref([])
const tmpKeywords = ref([])
const tmpCategorys = ref([])
const keywordOptions = ref([])
const categoryOptions = ref()
const mode = ref(true)
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
});

const judgeMode = () => {
    const editInfo = route.query.articleId ?? '0'
    if (editInfo !== '0') {
        mode.value = false
        //TODO:通过articleId获取文章详情  修改文章
        var params = new URLSearchParams();
        params.append('articleId', editInfo);
        axios.post(store.state.path + '/article/getArticleById', params)
            .then(res => {
                if (res.data.code == 200) {
                    formState.user = res.data.data
                    keywordOptions.value = res.data.data.label.split(',')
                    categoryOptions.value = JSON.parse(sessionStorage.getItem("categorys"))[res.data.data.categoryId]
                    sessionStorage.setItem("articleDetail", JSON.stringify(formState));
                } else {
                    console.log(res.data.msg)
                }
            })
            .catch(function (error) {
                console.log(error);
            });
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

var ws
onMounted(() => {
    //撰写文章的分类以及关键词加载
    formState.user.content = formState.user.content ?? ''
    tmpKeywords.value = JSON.parse(sessionStorage.getItem("keywords")) ?? []
    Object.entries(tmpKeywords.value).forEach(([key, value]) => {
        keywords.value.push({ index: key, value: value })
    })
    tmpCategorys.value = JSON.parse(sessionStorage.getItem("categorys")) ?? []
    Object.entries(tmpCategorys.value).forEach(([key, value]) => {
        categorys.value.push({ index: key, value: value })
    })
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
        params.append('author', JSON.parse(sessionStorage.getItem("user")).username);
        params.append('title', formState.user.title)
        params.append('content', formState.user.content)
        params.append('description', formState.user.description)
        params.append('categoryId', (JSON.parse(sessionStorage.getItem('categorys')).indexOf(categoryOptions.value)) + 1 ?? 1)
        params.append('label', keywordOptions.value ?? '')
        params.append('titleImgs', formState.user.titleImgs ?? JSON.parse(sessionStorage.getItem("user")).head)
        params.append('createTime', formState.user.createTime)
        axios.post(store.state.path + '/article/addArticle', params)
            .then(res => {
                if (res.data.code == 200) {
                    formState.user.author = JSON.parse(sessionStorage.getItem("user")).username
                    formState.user.category = categoryOptions.value
                    formState.user.keywords = keywordOptions.value
                    let submitCode = JSON.stringify({
                        id: res.data.msg,
                        author: formState.user.author,
                        title: formState.user.title,
                        content: formState.user.content,
                        description: formState.user.description,
                        category: JSON.parse(sessionStorage.getItem('categorys')).indexOf(formState.user.category),
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
        params.append('author', JSON.parse(sessionStorage.getItem("user")).username);
        params.append('title', formState.user.title)
        params.append('content', formState.user.content)
        params.append('description', formState.user.description)
        params.append('categoryId', (JSON.parse(sessionStorage.getItem('categorys')).indexOf(categoryOptions.value)) + 1 ?? 1)
        params.append('label', keywordOptions.value ?? '')
        params.append('titleImgs', formState.user.titleImgs ?? JSON.parse(sessionStorage.getItem("user")).head)
        params.append('createTime', formState.user.createTime)
        axios.post(store.state.path + '/article/alterArticle', params)
            .then(res => {
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
    types: {
        email: '${label} is not a valid email!',
        number: '${label} is not a valid number!',
    },
    number: {
        range: '${label} must be between ${min} and ${max}',
    },
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
})
</script>