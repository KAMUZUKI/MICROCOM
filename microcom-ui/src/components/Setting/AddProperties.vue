<template>
    <div class="container">
        <h1>添加分类</h1>
        <div class="catepory_box">
            <template v-for="item in categoryList" :key="item.id">
                <a-popconfirm title="操作" ok-text="删除" cancel-text="取消" @confirm="confirm(item.id)" @cancel="cancel">
                    <a-card hoverable style="width: 300px;margin-top:20px">
                        <a-card-meta :title=item.name :description=item.description>
                        </a-card-meta>
                    </a-card>
                </a-popconfirm>
            </template>
            <a-card hoverable style="width: 300px;height:97px;margin-top:20px" @click="visible = true">
                <a-card-meta title="添加分类" />
            </a-card>
            <a-modal v-model:visible="visible" title="添加分类" ok-text="Create" cancel-text="Cancel" @ok="onOk">
                <a-form ref="formRef" :model="formState" layout="vertical" name="form_in_modal">
                    <a-form-item name="title" label="分类名" :rules="[{ required: true, message: '请输入分类名' }]">
                        <a-input v-model:value="formState.title" />
                    </a-form-item>
                    <a-form-item name="description" label="描述">
                        <a-textarea v-model:value="formState.description" />
                    </a-form-item>
                </a-form>
            </a-modal>
        </div>
    </div>
</template>
<script>
import { defineComponent, reactive, ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { useStore } from 'vuex'
import axios from 'axios'

export default defineComponent({
    setup() {
        const formRef = ref()
        const visible = ref(false)
        const store = useStore()
        const categoryList = ref([])
        const formState = reactive({
            title: '',
            description: '',
        })

        const initData = () => {
            //TODO: 从后台获取分类
            axios.post(store.state.path + '/category/getCategory')
                .then(res => {
                    if (res.data.code == 1) {
                        categoryList.value = res.data.data
                    } else {
                        console.log(res.data.msg)
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }

        const confirm = (id) => {
            var params = new URLSearchParams();
            params.append('id', id);
            //TODO: 删除分类
            axios.post(store.state.path + '/category/deleteCategory', params)
                .then(res => {
                    if (res.data.code == 1) {
                        categoryList.value.pop(id)
                        message.success('删除成功')
                    } else {
                        message.error('删除失败')
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }

        const onOk = () => {
            formRef.value.validateFields().then(values => {
                visible.value = false;
                //TODO:添加分类
                var params = new URLSearchParams();
                params.append('name', values.title);
                params.append('sort', categoryList.value.length+1);
                params.append('description', values.description);
                axios.post(store.state.path + '/category/addCategory', params)
                    .then(res => {
                        if (res.data.code == 1) {
                            categoryList.value.push({
                                id: categoryList.value.length + 1,
                                name: values.title,
                                sort:categoryList.value.length + 1,
                                description: values.description,
                            });
                            formRef.value.resetFields();
                            message.success('添加成功');
                        } else {
                            message.error('添加失败');
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }).catch(info => {
                message.error('添加失败');
                console.log('Validate Failed:', info);
            });
        };

        onMounted(() => {
            initData()
        })

        return {
            formRef,
            visible,
            formState,
            categoryList,
            onOk,
            confirm,
            
        };
    },
});
</script>

<style>
.collection-create-form_last-form-item {
    margin-bottom: 0;
}

.catepory_box {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    align-items: center;
}

.container {
    min-height: 80vh;
    max-width: 1000px;
}
</style>