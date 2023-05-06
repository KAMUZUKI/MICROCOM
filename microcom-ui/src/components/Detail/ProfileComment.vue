<template>
    <q-card style="height: 580px;width: 500px;">
        <q-item v-ripple>
            <q-item-section side>
                <q-avatar size="48px">
                    <img :src=user.head />
                </q-avatar>
            </q-item-section>
            <q-item-section>
                <q-item-label>{{ user.author }}</q-item-label>
                <q-item-label caption>{{ user.time }}</q-item-label>
            </q-item-section>
            <q-item-section side>
                <q-btn outline rounded color="primary" label="关注" />
            </q-item-section>
        </q-item>
        <q-separator />
        <q-card-section class="comment-section q-pa-md" style="height: 475px; overflow: auto;">
            <div class="note-content">
                <div class="title"></div>
                <div class="desc">
                    <text>{{ props.detail.text }}</text>
                </div>
            </div>
            <div style="margin: 10px 0 0 10px;">评论</div>
            <q-separator spaced inset />
            <q-infinite-scroll @load="onLoad" :offset="10" scroll-target=".comment-section">
                <q-list>
                    <template v-for="(comment, index) in comments" :key="index">
                        <q-item>
                            <q-item-section avatar top>
                                <q-avatar icon="folder" color="orange" text-color="white" />
                            </q-item-section>
                            <q-item-section>
                                <q-item-label>
                                    <span>{{ comment.author }}</span>
                                    <span class="comment-header">{{ comment.time }}</span>
                                    <span class="comment-header" @click="prepareAdd(index, comment.id)">回复</span>
                                </q-item-label>
                                <q-item-label>
                                    <p style="word-break: break-word;">{{ comment.content }}</p>
                                </q-item-label>
                                <q-item-label>
                                    <span v-if="comment.replies" @click="toggleReplies(index)">{{
                                        comment.showReplies ? "收起" : "查看更多评论" }}</span>
                                </q-item-label>
                                <q-list v-if="comment.showReplies">
                                    <template v-for="(reply, replyIndex) in comment.replies" :key="replyIndex">
                                        <q-item>
                                            <q-item-section avatar top>
                                                <q-avatar icon="folder" color="orange" text-color="white" />
                                            </q-item-section>
                                            <q-item-section>
                                                <q-item-label>
                                                    <span>{{ reply.name }}</span>
                                                    <span class="comment-header">{{ reply.date }}</span>
                                                    <span class="comment-header"
                                                        @click="prepareAdd(index, comment.id, reply.name)">回复</span>
                                                </q-item-label>
                                                <q-item-label>
                                                    <p style="word-break: break-word;">{{ reply.text }}</p>
                                                </q-item-label>
                                            </q-item-section>
                                        </q-item>
                                    </template>
                                </q-list>
                            </q-item-section>
                        </q-item>
                        <q-separator spaced inset />
                    </template>
                </q-list>
                <template v-slot:loading>
                    <div class="row justify-center q-my-md">
                        <q-spinner-dots color="primary" size="40px" />
                    </div>
                </template>
            </q-infinite-scroll>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right" class="input-content">
            <textarea id="comment-area" ref="textareaRef" v-model="newCommentText" @input="handleInput"
                placeholder="善语结善缘，恶语伤人心" maxlength="200" rows="1"></textarea>
            <V3Emoji class="emoji" v-model="newCommentText" size="small"
                @click-emoji="appendText" :recent="true" fixPos="upright" />
            <div class="submit-comment" @click="addAction()">
                <svg width="24" height="24" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M42 6L4 20.1383L24 24.0083L29.0052 44L42 6Z" stroke="#333" stroke-width="4"
                        stroke-linejoin="round" />
                    <path d="M24.0083 24.0084L29.6651 18.3516" stroke="#333" stroke-width="4" stroke-linecap="round"
                        stroke-linejoin="round" />
                </svg>
            </div>
        </q-card-actions>
    </q-card>
</template>

<script setup>
import { reactive, ref, watch, defineProps, onMounted, onBeforeMount } from "vue";
import V3Emoji from 'vue3-emoji'
import 'vue3-emoji/dist/style.css'
import { message } from 'ant-design-vue';
import vlogComment from '@/js/api/vlogComment'

const props = defineProps({
    detail: {
        type: Object,
        default: null,
    },
})

const user = ref({
    author: "test",
    time: "test",
    content: "test",
    head: "https://cdn.quasar.dev/img/avatar3.jpg",
})

const newCommentText = ref("")

// 评论数据 start
const comments = reactive([
    {
        id: 1,
        author: "John Doe",
        time: "2023-04-15",
        content: "This is a great article!",
        showReplies: false,
        replies: [
            {
                id: 11,
                author: "Daryl Doe",
                time: "2023-04-15",
                content: "This is a good reply"
            },
            {
                id: 12,
                author: "Dike Pen",
                time: "2023-04-15",
                content: "This is a good reply"
            },
            {
                id: 13,
                author: "Youge Gem",
                time: "2023-04-15",
                content: "This is a good reply"
            }
        ],
    },
    {
        id: 2,
        author: "Jane Smith",
        time: "2023-04-14",
        content: "I really enjoyed reading this. Thanks for sharing!",
        showReplies: false,
        replies: [],
    }
]);

const onLoad = (index, done) => {
    setTimeout(async () => {
        var res = await vlogComment.findByVlogId(props.detail.id,index+1)
        if(res.data.length == 0) return
        res.data.forEach(vlog => {
            vlog.replies = false
            comments.push(vlog)
        });
        done();
    }, 1000);
}
// 评论数据 end

const appendText = (value) => {
    newCommentText.value += value;
}

const toggleReplies = (index) => {
    comments[index].push(...vlogComment.findChild(props.detail.id,index,1))
    comments[index].showReplies = !comments[index].showReplies;
}

const addComment = () => {
    comments.push({
        name: "Anonymous",
        date: new Date().toLocaleDateString(),
        text: newCommentText.value,
        showReplies: false,
        replies: [],
    });
    newCommentText.value = "";
}

const addReply = (commentIndex, parentId) => {
    comments[commentIndex].replies.push({
        parentId: parentId,
        name: "Anonymous",
        date: new Date().toLocaleDateString(),
        text: newCommentText.value,
    });
    comments[commentIndex].showReplies = true;
    newCommentText.value = "";
}

const addReplyWith = (commentIndex, parentId, replyName) => {
    replyName = replyName.split(">")[0]
    comments[commentIndex].replies.push({
        parentId: parentId,
        name: "Anonymous" + ">" + replyName,
        date: new Date().toLocaleDateString(),
        text: newCommentText.value,
    });
    comments[commentIndex].showReplies = true;
    newCommentText.value = "";
}

// 评论模式
const mode = ref("null")
// 评论参数
const args = ref(["null", "null", "null"])
// 点击回复时先保存参数并将焦点转至textarea
const prepareAdd = (index, parentId, replyName) => {
    args.value = [index, parentId, replyName]
    document.getElementById("comment-area").focus();
    if (typeof (replyName) == "undefined") {
        mode.value = "Reply";
        document.getElementById("comment-area").placeholder = "@" + comments[index].name;
    } else {
        mode.value = "ReplyWith";
        document.getElementById("comment-area").placeholder = "@" + replyName.split(">")[0];
    }
}

const addAction = () => {
    if (newCommentText.value == "" || newCommentText.value == null) {
        return;
    }
    console.log(newCommentText.value)
    if (mode.value == "null") {
        addComment()
    } else if (mode.value == "Reply") {
        addReply(args.value[0], args.value[1])
    } else {
        addReplyWith(args.value[0], args.value[1], args.value[2])
    }
    document.getElementById("comment-area").blur();
    document.getElementById("comment-area").placeholder = "善语结善缘，恶语伤人心";
    mode.value = "null";
    args.value = ["null", "null", "null"]
}

// 监听用户输入的变化
watch(newCommentText, (newValue) => {
    updateTextareaRows(newValue);
});

// 更新 textarea 的 rows 属性
const updateTextareaRows = (input) => {
    const textarea = document.getElementById('comment-area'); // 获取 textarea 元素的引用
    if(input==""){
        textarea.style.height = "24px";
        return;
    }
    if(input.length>199){
        message.warning('评论限定在200字以内')
    }
    // 计算 textarea 的行数
    textarea.style.height = 'auto'; // 先将 textarea 的高度设置为 auto，以便自动调整高度
    textarea.style.height = `${textarea.scrollHeight>72?72:textarea.scrollHeight}px`; // 设置 textarea 的高度为内容的实际高度
};

const fetchData = async ()=>{
    const response = await vlogComment.findByVlogId(props.detail.id,1)
    comments.push(...response.data)
    console.log(comments)
}

onBeforeMount(() => {
    console.log(comments)
});

onMounted(() => {
    fetchData()
});

</script>
  
<style scoped>
.comment-header {
    margin-left: 20px;
}

.input-content {
    width: 100%;
    resize: none;
    border: none;
    outline: none;
    font-size: 16px;
    position: relative;
    background-color: #f5f5f5;
    display: flex;
    align-items: flex-end;
}

.emoji {
    position: absolute;
    left: 10px;
    height: 30px;
    width: 0;
    bottom: 5px;
}

.submit-comment {
    position: absolute;
    border: none;
    outline: none;
    resize: none;
    margin-right: 5px;
    bottom: 5px;
}

#comment-area {
    width: 100%;
    resize: none;
    border: none;
    outline: none;
    padding: 0 30px 0 30px;
    font-size: 16px;
    border-radius: 5px;
    background-color: #f5f5f5;
}

/* ::-webkit-scrollbar {
    display: none;
} */
</style>