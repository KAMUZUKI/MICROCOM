import { createStore } from 'vuex'

const store = createStore({
  state () {
    return {
      count: 0,
      msg:'Hello Vuex',
      isCertified:false,
      isLogin:false,
      path:'http://localhost:9999/framework',
      wspath:'ws://localhost:9999/framework', 
      // path:'http://175.178.212.207:9999/framework',
      // wspath:'ws://175.178.212.207:9999/framework',  
      user: undefined, 
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
    user: state => state.user,
    isCertified: state => state.isCertified,
    isLogin: state => state.isLogin,
    path: state => state.path,
    wspath: state => state.wspath,
    keywords: state => state.keywords,
  }
})

export default store