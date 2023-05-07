<template>
    <a-list v-if="comments.length" :data-source="comments"
        :header="`${comments.length} ${comments.length > 1 ? 'replies' : 'reply'}`" item-layout="horizontal">
        <template #renderItem="{ item }">
            <a-list-item>
                <a-comment style="margin-left:30px" :author="item.createBy" :avatar="item.head"
                    :content="item.content" :datetime="item.createTime" />
            </a-list-item>
        </template>
    </a-list>
    <a-comment style="margin-left:30px">
        <template #avatar>
            <a-avatar :src="userDetial.head" :alt="userDetial.username" />
        </template>
        <template #content>
            <div style="">
                <V3Emoji :textArea="true" size="mid" v-model="value" />
            </div>
            <a-form-item>
                <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit">
                    发表
                </a-button>
            </a-form-item>
        </template>
    </a-comment>
</template>
<script>
import { defineComponent, ref, onMounted, createVNode } from 'vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import { Modal } from 'ant-design-vue';
import V3Emoji from 'vue3-emoji'
import 'vue3-emoji/dist/style.css'
import axios from 'axios';
import {message} from 'ant-design-vue'
import { useStore } from 'vuex' // 引入useStore 方法
dayjs.extend(relativeTime);
export default defineComponent({
    name: "CommentComponent",
    setup() {
        const store = useStore()
        const detail = ref()
        const user = ref()
        const userDetial = ref()
        const CommentData = ref([])
        const comments = ref([])
        const submitting = ref(false)
        const value = ref('')
        const articleId = JSON.parse(localStorage.getItem("articleDetail")).id

        //判断是否登录，未登录从本地获取用户信息，若无则使用默认信息
        if (typeof (store.state.user) == "undefined") {
            user.value = JSON.parse(localStorage.getItem("user"))
            if (user.value == null) {
                user.value = {
                    username: 'zhangsan',
                    email: '1437487442',
                    head: 'http://q1.qlogo.cn/g?b=qq&nk=1437487442&s=100',
                }
                userDetial.value = {
                    username: 'zhangsan',
                    email: '1437487442',
                    head: 'http://q1.qlogo.cn/g?b=qq&nk=1437487442&s=100',
                }
            } else {
                userDetial.value = user.value
                store.state.isCertified = true
            }
        } else {
            //使用登录信息
            user.value = store.state.user
            userDetial.value = user.value
            store.state.isCertified = true
        }

        //获取评论
        const initComment = () => {
            var params = new URLSearchParams()
            params.append('articleId', articleId)
            axios.post(store.state.path+"/comment/getComments", params)
                .then(res => {
                    if (res.data.code == 200) {
                        CommentData.value = res.data.data
                        CommentData.value.forEach(item => {
                            item.head = 'http://q1.qlogo.cn/g?b=qq&nk='+item.head+'&s=100'
                            comments.value.push(item)
                        })
                        message.success('评论加载成功')
                    } else {
                        message.error('评论加载失败')
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            
        }

        const showPromiseConfirm = (title,message)=> {
            Modal.confirm({
                title: title,
                icon: createVNode(ExclamationCircleOutlined),
                content: createVNode('div', {
                    style: 'color:red;',
                }, message),
                onOk() {
                    console.log('OK');
                },
                onCancel() {
                    console.log('Cancel');
                },
                class: 'test',
            });
        }

        const handleSubmit = () => {
            if (!localStorage.getItem("user")) {
                showPromiseConfirm("无权限", "您还未登录，是否前往登录？")
                return
            }

            if (!value.value) {
                showPromiseConfirm("输入错误", "请输入评论内容！")
                return;
            }
            
            submitting.value = true;

            //TODO:提交评论
            var params = new URLSearchParams()
            params.append('articleId',articleId)
            params.append('createBy',userDetial.value.id)
            params.append('content',value.value)
            params.append('createTime',dayjs().format("YYYY-MM-DD HH:mm:ss"))
            axios.post(store.state.path +"/comment/addComment", params)
                .then(res => {
                    if (res.data.code == 200) {
                        comments.value.push({
                            articleid: articleId,
                            createBy: userDetial.value.username,
                            head: userDetial.value.head,
                            content: value.value,
                            createTime: dayjs().format("YYYY-MM-DD HH:mm:ss"),
                        })
                        value.value = '';
                        submitting.value = false;
                        message.success('添加评论成功')
                    } else {
                        submitting.value = false;
                        message.error('添加评论失败' + res.data.msg)
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }

        onMounted(() => {
            // setTimeout(() => {
            initComment()
            // }, 100)
        })
        return {
            comments,
            submitting,
            value,
            detail,
            userDetial,
            handleSubmit,
            showPromiseConfirm
        }
    },
    components: {
        V3Emoji
    }
});
</script>