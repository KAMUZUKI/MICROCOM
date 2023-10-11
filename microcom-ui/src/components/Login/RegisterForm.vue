<template>
  <div class="box">
    <a-form
    class="register-form"
    ref="formRef"
    name="custom-validation"
    :model="formRegister"
    :rules="rules"
    v-bind="layout"
    @finish="handleFinish"
    @validate="handleValidate"
    @finishFailed="handleFinishFailed"
    :validate-messages="validateMessages"
  >
    <h4 style="text-align: center">注册</h4>
    <a-form-item label="用户名" name="username" required>
      <a-input
        v-model:value="formRegister.username"
        :rules="[{ required: true }]"
      >
        <template #prefix>
          <UserOutlined class="site-form-item-icon" />
        </template>
      </a-input>
    </a-form-item>
    <a-form-item has-feedback label="密码" name="pass">
      <a-input
        v-model:value="formRegister.pass"
        type="password"
        autocomplete="off"
      />
    </a-form-item>
    <a-form-item has-feedback label="确认密码" name="checkPass">
      <a-input
        v-model:value="formRegister.checkPass"
        type="password"
        autocomplete="off"
      />
    </a-form-item>
    <a-form-item
      :name="['email']"
      label="邮箱"
      :rules="[{ required: true }, { type: 'email' }]"
    >
      <a-input v-model:value="formRegister.email" />
    </a-form-item>
    <a-form-item :name="['phone']" label="手机号" :rules="[{ required: true }]">
      <a-input v-model:value="formRegister.phone" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 16, offset: 6 }">
      <a-button type="primary" html-type="submit">注册</a-button>
      <a-button style="margin-left: 10px" @click="toLogin"
        >登录</a-button
      >
    </a-form-item>
  </a-form>
  </div>
  <NotificationComponent ref="openNotification"></NotificationComponent>
</template>

<script setup>
import { ref, defineProps } from "vue";
import NotificationComponent from "@/components/tools/NotificationComponent.vue";
import { userRegister } from "@/js/api/user";

// 声明props
const props = defineProps({
  changeLogin:{
    type:Function
  }
});

const toLogin = ()=>{
  props.changeLogin();
};

//register start
const openNotification = ref();
const formRef = ref();
const formRegister = ref({
  username: "",
  pass: "",
  checkPass: "",
  email: "",
  phone: "",
  status: 0,
  usernamePass: 0,
  password1Pass: 0,
  password2Pass: 0,
  emailPass: 0,
});

const validateMessages = {
  required: "${label} 是必要的!",
  types: {
    email: "${label} 是无效的邮箱!",
    number: "${label} 是无效的数字!",
  },
};

let validatePass = async (_rule, value) => {
  if (value === "") {
    return Promise.reject("请输入密码!");
  } else {
    if (formRegister.value.checkPass !== "") {
      formRef.value.validateFields("checkPass");
    }
    return Promise.resolve();
  }
};

let validatePass2 = async (_rule, value) => {
  if (value === "") {
    return Promise.reject("请再次输入密码!");
  } else if (value !== formRegister.value.pass) {
    return Promise.reject("两次输入不一致!");
  } else {
    return Promise.resolve();
  }
};

const rules = {
  pass: [
    {
      required: true,
      validator: validatePass,
      trigger: "change",
    },
  ],
  checkPass: [
    {
      validator: validatePass2,
      trigger: "change",
    },
  ],
};

const layout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 16,
  },
};

const handleFinish = (values) => {
  console.log(values, formRegister);
  //TODO:注册账号
  var params = {
    "name": formRegister.value.username,
    "pwd": formRegister.value.pass,
    "phone": formRegister.value.phone,
    "email": formRegister.value.email,
    "head": formRegister.value.email.match(/(\w+)@/)[1]
  }
  userRegister(params)
    .then((res) => {
      if (res.data.code == 200) {
        resetForm();
        openNotification.value.openNotificationWithIcon(
          "success",
          "注册成功",
          res.data.msg
        );
      } else {
        openNotification.value.openNotificationWithIcon(
          "error",
          "注册失败",
          res.data.msg
        );
      }
    })
    .catch(function (error) {
      openNotification.value.openNotificationWithIcon(
        "error",
        "注册失败",
        error
      );
    });
};

const handleFinishFailed = (errors) => {
  console.log(errors);
};

const resetForm = () => {
  formRef.value.resetFields();
};

const handleValidate = (...args) => {
  console.log(args);
};
//register end
</script>

<style>
.box{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #ffffff;
  box-shadow: 0px 106px 42px rgba(0, 0, 0, 0.01),
    0px 59px 36px rgba(0, 0, 0, 0.05), 0px 26px 26px rgba(0, 0, 0, 0.09),
    0px 7px 15px rgba(0, 0, 0, 0.1), 0px 0px 0px rgba(0, 0, 0, 0.1);
  border-radius: 11px;
  font-family: "Inter", sans-serif;
}
</style>