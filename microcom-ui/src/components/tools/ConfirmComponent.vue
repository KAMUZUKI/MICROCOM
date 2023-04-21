<template>
    <RegisterComponent></RegisterComponent>
    <a-button @click="showConfirm">Confirm</a-button>
</template>

<script>
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { createVNode, defineComponent } from 'vue';
import { Modal } from 'ant-design-vue';
import RegisterComponent from '.../RegisterComponent.vue';
export default defineComponent({
    name: 'ConfirmComponent',
    setup() {
        const showConfirm = () => {
            Modal.confirm({
                title: 'Do you want to delete these items?',
                icon: createVNode(ExclamationCircleOutlined),
                content: 'When clicked the OK button, this dialog will be closed after 1 second',

                onOk() {
                    return new Promise((resolve, reject) => {
                        setTimeout(Math.random() > 0.5 ? resolve : reject, 1000);
                    }).catch(() => console.log('Oops errors!'));
                },
                onCancel() { },
            });
        };
        return {
            showConfirm,
        };
    },
    components: {
        RegisterComponent,
    },
});
</script>