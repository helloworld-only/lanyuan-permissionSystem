import VueRouter from "vue-router";
import LoginPage from '@/components/LoginPage'
import Home from '@/components/Home'
import UserDisplay from '../components/user/UserDisplay'
import Vue from 'vue'
Vue.use(VueRouter)

const router = new VueRouter({
    mode:'history',
    routes : [
        {
            path:'/',
            redirect:'/login'
        },
        {
            path:'/login',
            component: LoginPage,
        },
        {
            path:'/home',
            component:Home,
            children:[
                {
                    path: 'user/display',
                    component: UserDisplay
                }
            ]
        }
    ]
})

export default router;