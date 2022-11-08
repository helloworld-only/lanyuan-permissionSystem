import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios'

// 使用Vuex插件，这个要在创建 store 对象之前使用，不然会报错
Vue.use(Vuex);


const userDisplayVueShare = {
  state:{
    userInfo:{}
  }
}

const store = new Vuex.Store({
  // 全局共享数据
  state:{
    allUsers:[],
    allRoles:[],
    allAuths:[],
    // userInfo:{}
  },

  actions:{
    getAllRoles(context){
      const url = 'http://localhost/home/role';
      axios.get(url)
      .then(res => {
          context.commit('setAllRoles',res.data)
      })
      .catch(error =>{
          console.log(error)
      })
    }
  },

  mutations:{
    setAllRoles(state,data){
      state.allRoles = data;
    }
  },

  modules:{
    userDisplayVueShare,
  }

});
  
export default store;