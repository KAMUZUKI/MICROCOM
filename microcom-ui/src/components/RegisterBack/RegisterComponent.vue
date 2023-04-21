<template>
    <a-form ref="formRef" name="custom-validation" :model="formState" :rules="rules" v-bind="layout"
        @finish="handleFinish" 
        @validate="handleValidate" 
        @finishFailed="handleFinishFailed"
        :validate-messages="validateMessages">
        <h2 style="text-align:center;">注册</h2>
        <a-form-item label="用户名" name="username" required>
            <a-input v-model:value="formState.username" :rules="[{ required: true }]">
                <template #prefix>
                    <UserOutlined class="site-form-item-icon" />
                </template>
            </a-input>
        </a-form-item>
        <a-form-item has-feedback label="密码" name="pass">
            <a-input v-model:value="formState.pass" type="password" autocomplete="off" />
        </a-form-item>
        <a-form-item has-feedback label="确认密码" name="checkPass">
            <a-input v-model:value="formState.checkPass" type="password" autocomplete="off" />
        </a-form-item>
        <a-form-item :name="['email']" label="邮箱" :rules="[{ required: true }, { type: 'email' }]">
            <a-input v-model:value="formState.email" />
        </a-form-item>
        <a-form-item :name="['phone']" label="手机号" :rules="[{ required: true }]">
            <a-input v-model:value="formState.phone" />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 16, offset: 6 }">
            <a-button type="primary" html-type="submit">注册</a-button>
            <a-button style="margin-left: 10px" @click="onClose">退出</a-button>
        </a-form-item>
    </a-form>
    <notification-component ref="openNotification"></notification-component>
</template>
<style>

</style>
<script>
import { defineComponent, ref } from 'vue';
import NotificationComponent from '../tools/NotificationComponent.vue';
import axios from 'axios'
import { useStore } from 'vuex';
export default defineComponent({
    components: { NotificationComponent },
    name: 'RegisterComponent',
    setup() {
        const formRef = ref();
        const store = useStore();
        const openNotification = ref()

        const formState = ref({
            username: '',
            pass: '',
            checkPass: '',
            email: '',
            phone: '',
            status: 0,
            usernamePass: 0,
            password1Pass: 0,
            password2Pass: 0,
            emailPass: 0,
        });

        const validateMessages = {
            required: '${label} 是必要的!',
            types: {
                email: '${label} 是无效的邮箱!',
                number: '${label} 是无效的数字!',
            }
        };

        let validatePass = async (_rule, value) => {
            if (value === '') {
                return Promise.reject('请输入密码!');
            } else {
                if (formState.value.checkPass !== '') {
                    formRef.value.validateFields('checkPass');
                }
                return Promise.resolve();
            }
        };

        let validatePass2 = async (_rule, value) => {
            if (value === '') {
                return Promise.reject('请再次输入密码!');
            } else if (value !== formState.value.pass) {
                return Promise.reject("两次输入不一致!");
            } else {
                return Promise.resolve();
            }
        };

        const rules = {
            pass: [{
                required: true,
                validator: validatePass,
                trigger: 'change',
            }],
            checkPass: [{
                validator: validatePass2,
                trigger: 'change',
            }],

        };

        const layout = {
            labelCol: {
                span: 6,
            },
            wrapperCol: {
                span: 16,
            },
        };

        const handleFinish = values => {
            console.log(values, formState);
            //TODO:注册账号
            var params = new URLSearchParams();
            params.append('op', 'register');
            params.append('username', formState.value.username);
            params.append('password', formState.value.pass);
            params.append('phone', formState.value.phone);
            params.append('email', formState.value.email);
            params.append('head', formState.value.email.match(/(\w+)@/)[1]);
            axios.post(store.state.path + '/user.action', params)
                .then(res => {
                    if (res.data.code == 1) {
                        alert(res.data.msg)
                        openNotification.value.openNotificationWithIcon('success', '注册成功',res.data.msg);
                    } else {
                        openNotification.value.openNotificationWithIcon('error', '注册失败', '注册失败');
                    }
                })
                .catch(function (error) {
                    console.log(error);
                    openNotification.value.openNotificationWithIcon('error', '注册失败', '注册失败');
                });

        };

        const handleFinishFailed = errors => {
            console.log(errors);
        };

        const resetForm = () => {
            formRef.value.resetFields();
        };

        const handleValidate = (...args) => {
            console.log(args);
        };

        return {
            rules,
            layout,
            formRef,
            formState,
            openNotification,
            validateMessages,
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            resetForm,
            handleFinish,
            handleValidate,
            handleFinishFailed,
        };
    },
    props: {
        onClose: {
            type: Function,
            required: false,
        },
    },
});
</script>