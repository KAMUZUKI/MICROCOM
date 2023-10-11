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
                <div v-if="userId!==user.createId" @click="subscribe()">
                    <a class="subscribe-button">
                        <svg xmlns="http://www.w3.org/2000/svg">
                          <g>
                            <rect class="plus__line1" width="2" height="12" x="5" y="0"></rect>
                            <rect class="plus__line2" width="12" height="2" x="0" y="5"></rect>
                          </g>
                        </svg>
                        <span class="subscribe-text">关注</span>
                    </a>
                </div>
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
            <div style="display: flex" v-if="props.detail.label !== null">
                <template v-for="(tag, index) in props.detail.label.split(',')" :key="index">
                    <span class="tag tag-teal">{{ tag }}</span>
                </template>
            </div>
            <div style="margin: 10px 0 0 10px;">评论</div>
            <q-separator spaced inset />
            <q-infinite-scroll @load="onLoad" :offset="1" scroll-target=".comment-section">
                <q-list>
                    <template v-for="(comment, index) in comments" :key="index">
                        <q-item>
                            <q-item-section avatar top>
                                <q-avatar icon="folder" color="orange" text-color="white" />
                            </q-item-section>
                            <q-item-section>
                                <q-item-label>
                                    <span>{{ comment.author}}</span>
                                    <span class="comment-header">{{ comment.time }}</span>
                                    <span class="comment-header" @click="prepareAdd(index, comment.id)">回复</span>
                                </q-item-label>
                                <q-item-label>
                                    <p style="word-break: break-word;">{{ comment.content }}</p>
                                </q-item-label>
                                <q-item-label>
                                    <span v-if="comment.hasReply" @click="toggleReplies(index, comment.id)">{{
                                        comment.showReplies ? "收起" : "查看更多评论" }}</span>
                                </q-item-label>
                                <!-- 评论回复 -->
                                <q-list v-if="comment.showReplies">
                                    <template v-for="(reply, replyIndex) in comment.replies" :key="replyIndex">
                                        <q-item>
                                            <q-item-section avatar top>
                                                <q-avatar icon="folder" color="orange" text-color="white" />
                                            </q-item-section>
                                            <q-item-section>
                                                <q-item-label>
                                                    <span>
                                                        {{ reply.author.replace("-",">")}}
                                                    </span>
                                                    <span class="comment-header">{{ reply.time }}</span>
                                                    <span class="comment-header"
                                                        @click="prepareAdd(index, comment.id, reply.author)">回复</span>
                                                </q-item-label>
                                                <q-item-label>
                                                    <p style="word-break: break-word;">{{ reply.content }}</p>
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
                <div v-if="showDataFlag" style="text-align: center;">
                    <p>没有更多数据了...</p>
                </div>
            </q-infinite-scroll>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right" class="input-content">
            <textarea id="comment-area" ref="textareaRef" v-model="newCommentText" @input="handleInput"
                placeholder="善语结善缘，恶语伤人心" maxlength="200" rows="1"></textarea>
            <V3Emoji class="emoji" v-model="newCommentText" size="small" @click-emoji="appendText" :recent="true"
                fixPos="upright" />
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
import { reactive, ref, watch, defineProps, onMounted } from "vue";
import V3Emoji from 'vue3-emoji'
import 'vue3-emoji/dist/style.css'
import { message } from 'ant-design-vue';
import vlogComment from '@/js/api/vlogComment'
import followApi from '@/js/api/user'
import utils from "@/js/utils/utils";

const showDataFlag = ref(false)
const username = JSON.parse(localStorage.getItem("user")).name
const userId = JSON.parse(localStorage.getItem("user")).id
const props = defineProps({
    detail: {
        type: Object,
        default: null,
    },
})

const user = ref({
    id: props.detail.id,
    createId: props.detail.createId,
    author: props.detail.name,
    time: utils.parseDate(props.detail.time),
    content: props.detail.content,
    head: props.detail.head,
})

const newCommentText = ref("")

// 评论数据 start
const comments = reactive([]);

const onLoad = (index, done) => {
    if (showDataFlag.value) {
        done();
        return;
    }
    setTimeout(async () => {
        var res = await vlogComment.findByVlogId(props.detail.id, index + 1)
        if (res.data.length == 0) {
            done();
            showDataFlag.value = true
            return
        }
        res.data.forEach(vlog => {
            vlog.replies = []
            vlog.showReplies = false
            comments.push(vlog)
        });
        done();
    }, 1000);
}
// 评论数据 end

const appendText = (value) => {
    newCommentText.value += value;
}

const toggleReplies = async (index, id) => {
    var replies = await vlogComment.findChild(props.detail.id, id, 1)
    replies.data.forEach(reply => {
        reply.showReplies = false
        comments[index].replies.push(reply)
    });
    comments[index].showReplies = !comments[index].showReplies;
}

const addComment = async () => {
    var res = await vlogComment.save({
        vlogId: props.detail.id,
        author: username,
        time: utils.getTime(),
        content: newCommentText.value,
    })
    if (res.code == 200) {
        message.success("评论成功")
        comments.push({
            author: username,
            time: utils.getTime(),
            content: newCommentText.value,
            showReplies: false,
            replies: [],
        });
        newCommentText.value = ""
    } else {
        message.error("评论失败,请稍后重试！")
    }
}

const addReply = async (commentIndex, parentId) => {
    var res = await vlogComment.save({
        vlogId: props.detail.id,
        userId: userId,
        parentId: parentId,
        author: username,
        time: utils.getTime(),
        content: newCommentText.value,
    })
    if (res.code == 200) {
        message.success("回复成功")
        comments[commentIndex].replies.push({
            parentId: parentId,
            author: username,
            time: utils.getTime(),
            content: newCommentText.value,
        });
        comments[commentIndex].showReplies = true;
        newCommentText.value = "";
    } else {
        message.error("回复失败,请稍后重试！")
    }
}

const addReplyWith = async (commentIndex, parentId, replyName) => {
    var res = await vlogComment.save({
        vlogId: props.detail.id,
        userId: userId,
        parentId: parentId,
        author: username + "-" + replyName,
        time: utils.getTime(),
        content: newCommentText.value,
    })
    if (res.code == 200) {
        replyName = replyName.split(">")[0]
        comments[commentIndex].replies.push({
            parentId: parentId,
            author: username + "-" + replyName,
            time: utils.getTime(),
            content: newCommentText.value,
        });
        comments[commentIndex].showReplies = true;
        newCommentText.value = "";
        message.success("回复成功")
    } else {
        message.error("回复失败,请稍后重试！")
    }


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
        document.getElementById("comment-area").placeholder = "@" + comments[index].author;
    } else {
        mode.value = "ReplyWith";
        document.getElementById("comment-area").placeholder = "@" + replyName.split("-")[0];
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

const subscribe = utils.debounce(async ()=>{
    var subButton = document.querySelector('.subscribe-button');
    var subbedClass = 'subbed';
    var text;
    var res = await followApi.isFollow(userId,props.detail.createId)
    if (res.data) {
        text = '关注';
        await followApi.unfollow(userId,props.detail.createId)
        subButton.classList.remove(subbedClass);
        message.success("取消关注")
    } else {
        text = '已关注';
        await followApi.follow(userId,props.detail.createId)
        subButton.classList.add(subbedClass);
        message.success("关注成功")
    }
    subButton.querySelector('.subscribe-text').innerHTML = text;
},1000)

const initSubscribe = async ()=>{
    var subButton = document.querySelector('.subscribe-button')
    if(subButton==null) return
    var subbedClass = 'subbed'
    var text
    var res = await followApi.isFollow(userId,props.detail.id)
    if (res.data) {
        subButton.classList.add(subbedClass)
        text = '已关注'
    } else {
        subButton.classList.remove(subbedClass)
        text = '关注'
    }
    subButton.querySelector('.subscribe-text').innerHTML = text
}

// 监听用户输入的变化
watch(newCommentText, (newValue) => {
    updateTextareaRows(newValue);
});

// 更新 textarea 的 rows 属性
const updateTextareaRows = (input) => {
    const textarea = document.getElementById('comment-area'); // 获取 textarea 元素的引用
    if (input == "") {
        textarea.style.height = "24px";
        return;
    }
    if (input.length > 199) {
        message.warning('评论限定在200字以内')
    }
    // 计算 textarea 的行数
    textarea.style.height = 'auto'; // 先将 textarea 的高度设置为 auto，以便自动调整高度
    textarea.style.height = `${textarea.scrollHeight > 72 ? 72 : textarea.scrollHeight}px`; // 设置 textarea 的高度为内容的实际高度
};

const fetchData = async () => {
    const response = await vlogComment.findByVlogId(props.detail.id, 1)
    response.data.forEach(vlog => {
        vlog.replies = []
        vlog.showReplies = false
        comments.push(vlog)
    });
    console.log(comments)
}

onMounted(() => {
    fetchData()
    initSubscribe()
});

</script>
  
<style lang="scss" scoped>
//tags start
.tag {
    background-color: #ccc;
    color: #fff;
    border-radius: 50px;
    font-size: 12px;
    margin: 0 5px;
    padding: 2px 10px;
    text-transform: uppercase;
}

.tag-teal {
    background-color: #92d4e4;
}
//tag end

.container {
	margin: 140px auto;
	width: 100px;
}

.subscribe-button {
	$padding: 10px;
	$radius: 3px;
	$gap: 10px;
	$col-info: #e1e1e1;
	$col-bg: #2486b9;
	$col-bg-subbed: #777;
	$duration: 0.25s;
	
	position: relative;
	padding: $padding 18px;
	font-family: inherit;
	font-size: inherit;
	font-weight: 500;
	text-transform: uppercase;
	color: white;
	background: $col-bg;
	border: none;
	border-radius: $radius;
	cursor: pointer;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.16), 0 1px 2px rgba(0, 0, 0, 0.1);
	
	transition: background, box-shadow;
	transition-duration: 0.2s;
	
	&:active {
		background: darken($col-bg, 2%);
		box-shadow: 0 3px 8px rgba(0, 0, 0, 0.2), 0 1px 4px rgba(0, 0, 0, 0.14);
	}
	
	&.subbed {
		background: $col-bg-subbed;

		svg {
			width: 16px;
			.plus__line1 {
				transform: translate(14px, 0) rotate(45deg) translate(-5px,0) scaleY((14/12));
			}

			.plus__line2 {
				transform: translate(2px, 5px) rotate(45deg) scaleX(0.5) translate(0px, -5px);
			}
		}
	}
	
	svg {
		display: inline-block;
		width: 12px;
		height: 12px;
		fill: white;
		margin-right: $padding;
		
		transition: width $duration;
		
		.plus__line1,
		.plus__line2 {
			transition: transform $duration;	
		}
		
		.plus__line1 {
			transform-origin: 0 0; // should be this by default in in FF <= 42 (which doesn't support this on svg)
		}

		.plus__line2 {
			transform-origin: 0 0;
		}
	}
	
	* {
		vertical-align: middle;
	}
}
</style>

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
} */</style>