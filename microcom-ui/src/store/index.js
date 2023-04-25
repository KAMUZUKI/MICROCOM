import { createStore } from 'vuex'

const store = createStore({
  state () {
    return {
      count: 0,
      msg:'Hello Vuex',
      isCertified:false,
      path:'http://172.16.124.68:8080/microcom',
      wspath:'ws://172.16.124.68:8080/microcom',  
      user: undefined, 
      // path:'http://175.178.212.207:8080/demo',
      // wspath:'ws://175.178.212.207:8080/demo',
      // path:'http://localhost:8081/demo',
      // wspath:'ws://localhost:8081/demo',
      keywords:[],
    }
  },
  mutations: {
    increment (state) {
      state.count++
    }
  },
  actions: {
    increment (context) {
      context.commit('increment')
    }
  },
  getters: {
    count: state => state.count,
    msg: state => state.msg,
    isCertified: state => state.isCertified,
    path: state => state.path,
    wspath: state => state.wspath,
    keywords: state => state.keywords,
  }
})

export default store