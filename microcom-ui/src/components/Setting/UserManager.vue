<template>
  <div class="container">
    <a-table :columns="columns" :data-source="dataSource" bordered>
      <template #bodyCell="{ column, text, record }">
        <template
          v-if="['status', 'type'].includes(column.dataIndex)">
          <div>
            <a-input v-if="editableData[record.id]" v-model:value="editableData[record.id][column.dataIndex]"
              style="margin: -5px 0" />
            <template v-else>
              {{ text }}
            </template>
          </div>
        </template>
        <template v-else-if="column.dataIndex === 'modify'">
          <div class="editable-row-operations">
            <span v-if="editableData[record.id]">
              <a-typography-link @click="save(record)">保存</a-typography-link>
              <a-popconfirm title="确认取消?" @confirm="cancel(record.id)">
                <a>取消</a>
              </a-popconfirm>
            </span>
            <span v-else>
              <a @click="edit(record.id)">编辑</a>
            </span>
          </div>
        </template>
        <template v-else-if="column.dataIndex === 'delete'">
          <div class="editable-row-operations">
            <span>
              <a-popconfirm title="确认删除?" ok-text="删除" cancel-text="取消" @confirm="deleteById(record.id)">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </div>
        </template>
      </template>
    </a-table>
  </div>
</template>
<script>
import { cloneDeep } from 'lodash-es';
import { defineComponent, reactive, ref, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import axios from 'axios'
import { useStore } from 'vuex';
export default defineComponent({
  name: 'UserManager',
  setup() {
    const store=useStore();
    const columns = [{
      title: 'Id',
      width: 15,
      dataIndex: 'id',
    }, {
      title: 'Name',
      width: 25,
      dataIndex: 'username',
    }, {
      title: 'Account',
      dataIndex: 'account',
      width: 35,
    }, {
      title: 'Phone',
      dataIndex: 'phone',
      width: 40,
    }, {
      title: 'Email',
      dataIndex: 'email',
      width: 60,
    }, {
      title: 'CreateTime',
      dataIndex: 'createTime',
      width: 50,
    }, {
      title: 'Status',
      dataIndex: 'status',
      width: 25,
    }, {
      title: '用户类型',
      dataIndex: 'type',
      width: 30,
    }, {
      title: 'Action',
      dataIndex: 'modify',
      width: 25,
    }, {
      title: 'Action',
      dataIndex: 'delete',
      width: 25,
    }];

    const dataSource = ref([]);
    const listDataTmp = ref([]);

    const initDataSource = () => {
      //TODO:获取数据
      axios.post(store.state.path+'/user/getAllUser')
        .then(res => {
          if (res.data.code == 1) {
            listDataTmp.value = res.data.data
            for (const [key, item] of Object.entries(listDataTmp.value)) {
              console.log(key)
              dataSource.value.push(item);
            }
          } else {
            console.log('获取用户信息列表失败');
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    const editableData = reactive({});

    const edit = key => {
      editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.id)[0]);
    }

    const save = user => {
      Object.assign(dataSource.value.filter(item => user.id === item.id)[0], editableData[user.id]);
      delete editableData[user.id];
      //TODO:通过userid修改后台用户数据
      setTimeout(() => {
        var params = new URLSearchParams();
        params.append('userId', user.id);
        params.append('status', user.status);
        params.append('type', user.type);
        axios.post(store.state.path+'/user/updateUserById', params)
          .then(res => {
            if (res.data.code == 1) {
              delete editableData[user.id];
              message.success('修改用户ID'+user.id+'成功');
            } else {
              message.error('修改用户ID'+user.id+'失败');
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }, 500);
    }

    const cancel = key => {
      delete editableData[key];
    }

    const deleteById = userId => {
      //TODO:通过userid删除后台用户数据
      var params = new URLSearchParams();
      params.append('op', 'deleteUser');
      params.append('userId', userId);
      axios.post(store.state.path+'/user.action', params)
        .then(res => {
          if (res.data.code == 1) {
            dataSource.value = dataSource.value.filter(item => userId !== item.id);
            message.success('删除用户ID'+userId+'成功');
          } else {
            message.error('删除用户ID'+userId+'失败');
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    onMounted(() => {
      initDataSource()
    })

    return {
      dataSource,
      columns,
      editingKey: '',
      editableData,
      edit,
      save,
      cancel,
      deleteById,
    };
  },
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}

.container {
  min-width: 1200px;
}
</style>