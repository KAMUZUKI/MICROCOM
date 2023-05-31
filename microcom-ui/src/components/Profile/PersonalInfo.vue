<template>
  <div class="q-pa-md">
    <div class="q-gutter-md items-start">
      <q-form
      @submit="handleForm"
      @reset="onReset"
      class="q-gutter-md"
    >
      <q-input v-model="userInfo.name" filled type="text" label="用户名" lazy-rules
        :rules="[val => val && val.length > 0 || '用户名不为空']" />
      <q-input v-model="userInfo.email" label="邮箱" filled type="email" lazy-rules
        :rules="[(val, rules) => rules.email(val) || '输入正确的邮箱']" />
      <q-input v-model="userInfo.phone" label="电话" filled type="tel" lazy-rules
        :rules="[val => val && val.length == 11 || '输入正确的电话']" hint="电话为11位"/>
      <q-input v-model="userInfo.head" readonly filled type="text" label="头像地址" />
      <a-upload v-model:file-list="fileList" name="file" list-type="picture-card" class="avatar-uploader"
        :headers="{ satoken: token }" :show-upload-list="false" :action=uploadUrl :before-upload="beforeUpload"
        @change="handleChange">
        <img v-if="imageUrl" :src="imageUrl" alt="avatar" style="overflow: hidden;" />
        <div v-else>
          <loading-outlined v-if="loading"></loading-outlined>
          <plus-outlined v-else></plus-outlined>
          <div class="ant-upload-text">Upload</div>
        </div>
      </a-upload>
      <div>
        <q-btn label="Submit" type="submit" color="primary"/>
        <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
        </div>
    </q-form>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import config from '@/js/api/config'
import userApi from '@/js/api/user'

const fileList = ref([]);
const loading = ref(false);
const imageUrl = ref('');
const uploadUrl = config.getUrl + "upload/image";
const token = localStorage.getItem('tokeninfo');
const user = JSON.parse(localStorage.getItem("user"));
const userInfo = reactive({
  id: user.id,
  name: user.name,
  phone: user.phone,
  email: user.email,
  head: user.head
});

const handleChange = info => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    // Get this url from response in real world.
    if (info.file.response.data == null) {
      message.error(info.file.response.msg);
      loading.value = false;
      return;
    }
    imageUrl.value = info.file.response.data;
    userInfo.head = info.file.response.data;
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('upload error');
  }
};

const beforeUpload = file => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('You can only upload JPG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJpgOrPng && isLt2M;
};

const handleForm = async () => {
  // userApi.updateUserById(userInfo)
  let res = await userApi.updateUserById(userInfo)
  if (res.code === 200) {
    message.success(res.msg);
  } else {
    message.error(res.msg);
  }
}

onMounted(() => {
  imageUrl.value = user.head
})
</script>

<style scoped>
.avatar-uploader>.ant-upload {
  width: 128px;
  height: 128px;
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>