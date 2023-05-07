<template>
  <q-dialog v-model="fixed">
    <q-card>
      <q-card-section>
        <q-btn-group push>
          <q-btn @click="showDialog(userId,1)" push label="粉丝" icon="timeline" />
          <q-btn @click="showDialog(userId,0)" push label="关注" icon="visibility" />
        </q-btn-group>
      </q-card-section>

      <q-separator />

      <q-card-section style="max-height: 50vh;min-width: 500px" class="scroll">
        <q-list bordered class="rounded-borders">
          <template v-for="(item,index) in items" :key="index">
            <q-item clickable v-ripple>
              <q-item-section avatar>
                <q-avatar>
                  <img :src=item.head>
                </q-avatar>
              </q-item-section>
              <q-item-section>
                <q-item-label lines="1">{{item.name}}</q-item-label>
              </q-item-section>
              <q-item-section side>
                <div v-if="title=='粉丝'">
                    <q-btn @click="follow(item.id)" push color="white" text-color="dark" label="关注" />
                </div>
                <div v-else>
                    <q-btn @click="unfollow(item.id)" push color="white" text-color="dark" label="取消关注" />
                </div>
              </q-item-section>
            </q-item>
          </template>
        </q-list>
      </q-card-section>
      <q-separator />
    </q-card>
  </q-dialog>
</template>

<script setup>
import followApi from "@/js/api/user"
import { ref, onMounted, defineExpose} from "vue"
import { message } from "ant-design-vue"
import utils from "@/js/utils/utils"
const fixed = ref(false)
const items = ref([])
const title = ref("")
const userId = JSON.parse(localStorage.getItem("user")).id

const initData = async (id,mode)=>{
    let res
    if(mode == 1){
        res = await followApi.getFollowers(id)
        title.value = "粉丝"
    }else{
        res = await followApi.getFollowing(id)
        title.value = "关注"
    }
    items.value = res.data
}

const showDialog = (id,mode)=>{
    initData(id,mode)
    fixed.value = true
}

const follow = utils.debounce(async (targetId)=>{
    let res = await followApi.follow(userId,targetId)
    if(res.data){
        message.success("关注成功")
    }
},1000)

const unfollow = utils.debounce(async (targetId) => {
  let res = await followApi.unfollow(userId, targetId);
  if (res.data) {
    message.success('取消关注成功');
  }
}, 1000);

defineExpose({
    showDialog
})

onMounted(() => {
    initData(userId,1)
})
</script>

<style>

</style>