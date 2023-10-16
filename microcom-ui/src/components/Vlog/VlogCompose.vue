<template>
  <q-card class="vlog-card">
    <q-card-section>
        <div class="text-h6" style="text-align:center">发布VLOG</div>
      </q-card-section>
    <q-card-section>
      <span class="item-label">标题</span>
      <a-input v-model:value="vlog.title" placeholder="输入主题" size="middle" maxlength=40 showCount allow-clear />
    </q-card-section>
    <q-card-section style="padding-bottom: 0px;padding-top: 0px;">
      <span class="item-label">内容</span>
      <a-textarea v-model:value="vlog.text" placeholder="记录当下所想..." :auto-size="{ minRows: 2, maxRows: 5 }"></a-textarea>
      <V3Emoji @click-emoji="clickEmoji" size="mid" fixPos="downleft" v-model="vlog.text" />
    </q-card-section>
    <q-card-section>
      <div>
        <template v-for="(tag, index) in state.tags" :key="tag">
          <a-tooltip v-if="tag.length > 5" :title="tag">
            <a-tag :closable="index !== 0" @close="handleClose(tag)">
              {{ `${tag.slice(0, 20)}...` }}
            </a-tag>
          </a-tooltip>
          <a-tag v-else :closable="index !== 0" @close="handleClose(tag)">
            {{ tag }}
          </a-tag>
        </template>
        <template v-if="state.tags.length < 5">
          <a-input v-if="state.inputVisible" ref="inputRef" v-model:value="state.inputValue" showCount maxlength="10"
            type="text" size="small" :style="{ width: '78px' }" @blur="handleInputConfirm"
            @keyup.enter="handleInputConfirm" />
          <a-tag v-else style="background: #fff; border-style: dashed" @click="showInput">
            <plus-outlined />
            添加标签
          </a-tag>
        </template>
      </div>
    </q-card-section>
    <q-card-section>
      <div class="clearfix">
        <a-upload v-model:file-list="fileList" :customRequest="uploadImage" list-type="picture-card"
          @preview="handlePreview">
          <div v-if="fileList.length < 8">
            <plus-outlined />
            <div style="margin-top: 8px">添加图片</div>
          </div>
        </a-upload>
        <a-modal :visible="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
          <img alt="example" style="width: 100%" :src="previewImage" />
        </a-modal>
      </div>
    </q-card-section>
    <q-card-section>
      <a-button style="margin-left: 85%;" type="primary" @click="handleSubmit">发布</a-button>
    </q-card-section>
  </q-card>
</template>
<script setup>
import { PlusOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import { ref, reactive, nextTick } from 'vue';
import uploadApi from '@/js/api/upload';
import vlogApi from '@/js/api/vlog';
import V3Emoji from 'vue3-emoji'
import 'vue3-emoji/dist/style.css'
import { useStore } from "vuex";

const store = useStore()
const imgs = ref([])  
const vlog = reactive({
  createId: '',
  title: '',
  label: '',
  text: '',
  img: '',
  time: '',
})

const clickEmoji = (val) => {
  vlog.text += val;
};

//upload start
function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');
const fileList = ref([]);

const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
};

//处理图片预览
const handlePreview = async file => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};

//处理图片上传
const uploadImage = async (e) => {
  let res = await uploadApi.uploadImage(e.file)
  if (res.code == 200) {
    imgs.value.push(res.data)
    e.onSuccess(res.data, e);
  } else {
    e.onError(res.msg);
  }
}

//处理vlog上传
const handleSubmit = async () => {
  vlog.createId = store.state.user.id
  vlog.label = state.tags.join(',')
  vlog.img = imgs.value.join(',')
  vlog.time = new Date().toLocaleString()
  // let object = JSON.stringify(vlog)
  let res = await vlogApi.add(vlog)
  if (res.code == 200) {
    message.success(res.msg)
    vlog.title = ''
    vlog.label = ''
    vlog.text = ''
    vlog.img = ''
    fileList.value = []
  } else {
    message.error(res.msg)
  }
}
//upload end

//tag start
const inputRef = ref();
const state = reactive({
  tags: [],
  inputVisible: false,
  inputValue: '',
});

const handleClose = removedTag => {
  const tags = state.tags.filter(tag => tag !== removedTag);
  console.log(tags);
  state.tags = tags;
};

const showInput = () => {
  state.inputVisible = true;
  nextTick(() => {
    inputRef.value.focus();
  });
};

const handleInputConfirm = () => {
  const inputValue = state.inputValue;
  if (state.tags.length > 4) {
    message.error('最多添加5个标签');
    return;
  }
  let tags = state.tags;
  if (inputValue && tags.indexOf(inputValue) === -1) {
    tags = [...tags, inputValue];
  }
  console.log(tags);
  Object.assign(state, {
    tags,
    inputVisible: false,
    inputValue: '',
  });
};
//tag end
</script>

<style scoped>
.item-label {
  font-size: 16px;
  line-height: 2;
}

.vlog-card {
  width: 550px;
  height: 550px;
}

.clearfix {
  display: flex;
}

.emoji-item {
  display: flex;
  justify-content: flex-end;
}

/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

::v-deep .ant-upload-list {
  width: 448px !important;
  margin: 0 auto !important;
}
</style>