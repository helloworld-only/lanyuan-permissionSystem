import Vue from 'vue'
import App from './App.vue'
import Element from 'element-ui'

import router from './router'
import store from './store/index.js'


Vue.config.productionTip = false

Vue.use(Element)

new Vue({
  render: h => h(App),
  router,
  store,
}).$mount('#app')
