import Vue from 'vue'
import App from './App.vue'
import Element, { MessageBox } from 'element-ui'

import router from './router'
import store from './store/index.js'

import axios from 'axios'




Vue.config.productionTip = false

Vue.use(Element)

axios.defaults.withCredentials=true // 开启axios请求带上cookie

// axios请求拦截器
axios.interceptors.request.use(config => {
  const token = window.localStorage.getItem('token');
  config.params = {'token': token}
  return config
})



// 响应拦截器
// axios.interceptors.response.use(res => {
//   // 未设置状态码则默认成功状态
//   const code = res.data.code 

//   const statusCode = res.statusCode
//   // confirm.log("statusCode = " + statusCode + ", code = " + code)

//   let isRelogin = false;
//   // 获取信息
//   const msg =  res.data.msg

//   if(code === 200){
//     return res;
//   }

//   if (code === 304 || statusCode === 302){
//     // console.log("重定向")
//     Element.MessageBox.confirm('登录状态已过期，请重新登录', '系统提示', {
//       confirmButtonText: '确定',
//       type: 'warning'
//     }).then(() => {
//       location.href = '/login';
//     })
//     return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
//   }
//   // else if (code === 401) {
//   //   if (!isRelogin) {
//   //     isRelogin = true;
//   //     Element.MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
//   //       confirmButtonText: '重新登录',
//   //       cancelButtonText: '取消',
//   //       type: 'warning'
//   //     }).then(() => {
//   //       isRelogin = false;
//   //       store.dispatch('LogOut').then(() => {
//   //         location.href = '/login';
//   //       })
//   //     }).catch(() => {
//   //       isRelogin.show = false;
//   //     });
//   //   }
//   //   return Promise.reject('无效的会话，或者会话已过期，请重新登录。')

//   // } else if (code === 500) {
//   //   Element.Message({
//   //     message: msg,
//   //     type: 'error'
//   //   })
//   //   return Promise.reject(new Error(msg))
//   // } else if (code === 601) {
//   //   Element.Message({
//   //     message: msg,
//   //     type: 'warning'
//   //   })
//   //   return Promise.reject('error')
//   // } else if (code !== 200) {
//   //   Notification.error({
//   //     title: msg
//   //   })
//   //   return Promise.reject('error')
//   // } else {
//   //   return res.data
//   // }
//   return res;
// },
// // error => {
// //   console.log('err' + error)
// //   let { message } = error;
// //   if (message == "Network Error") {
// //     message = "后端接口连接异常";
// //   }
// //   else if (message.includes("timeout")) {
// //     message = "系统接口请求超时";
// //   }
// //   else if (message.includes("Request failed with status code")) {
// //     message = "系统接口" + message.substr(message.length - 3) + "异常";
// //   }
// //   Element.Message({
// //     message: message,
// //     type: 'error',
// //     duration: 5 * 1000
// //   })
// //   return Promise.reject(error)
// // }
// )

new Vue({
  render: h => h(App),
  router,
  store,
}).$mount('#app')
