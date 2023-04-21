<template>
    <div style="border: 1px solid #ccc;text-align:left;">
        <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig"
            :mode="mode" />
        <Editor style="height: 500px; overflow-y: hidden;" v-model="valueHtml" :defaultConfig="editorConfig"
            :mode="mode" @onCreated="handleCreated" />
    </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import axios from 'axios';
export default {
    name: 'MavonEditor',
    components: { Editor, Toolbar },
    setup(props) {
        // 编辑器实例，必须用 shallowRef
        const editorRef = shallowRef()

        // 内容 HTML
        const valueHtml = ref('')

        // 模拟 ajax 异步获取内容
        onMounted(() => {
            setTimeout(() => {
                valueHtml.value = props.content??''
            }, 500)
        })

        const toolbarConfig = {}
        const editorConfig = { placeholder: '请输入内容...' }

        const getContent = ()=>{
            alert("valueHtml.value" + valueHtml.value)
            return valueHtml.value
        }

        // 组件销毁时，也及时销毁编辑器
        onBeforeUnmount(() => {
            const editor = editorRef.value
            if (editor == null) return
            editor.destroy()
        })

        const handleCreated = (editor) => {
            editorRef.value = editor // 记录 editor 实例，重要！
        }

        const commit =  ()=>{//提交
            let params=new URLSearchParams();
            params.append("op","postTopic");
            params.append("title",this.title);
            console.log(this.valueHtml);
            params.append("content",this.valueHtml);
            params.append("boardid",this.boardid);
            axios.post("topic.action",params).then(result=>{
                let json=result.data;
                if(json.code==1){
                    alert("操作成功");
                    window.location.href="list.html";
                }else{
                    alert("操作失败。"+json.msg);
                }
            })
        }

        return {
            editorRef,
            valueHtml,
            mode: 'default', // 或 'simple'
            toolbarConfig,
            editorConfig,
            commit,
            getContent,
            handleCreated,
        };
    },
    props: {
        content: {
            type: String,
            default: ''
        }
    }
}
</script>    