import Vue from 'vue'
import App from './App.vue'
import Element from 'element-ui'

import router from './router'
import store from './store/index.js'

import axios from 'axios'

axios.defaults.withCredentials=true // 开启axios请求带上cookie

// axios请求拦截器
axios.interceptors.request.use(config => {
  const token = window.localStorage.getItem('token');
  config.params = {'token': token}
  return config
})


Vue.config.productionTip = false

Vue.use(Element)

new Vue({
  render: h => h(App),
  router,
  store,
}).$mount('#app')
