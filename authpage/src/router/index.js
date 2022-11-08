import VueRouter from "vue-router";
import LoginPage from '@/components/LoginPage'
import Home from '@/components/Home'
import UserDisplay from '../components/user/UserDisplay'
import Vue from 'vue'
import RoleDisplay from '../components/role/RoleDisplay'
import AuthDisplay from '../components/auth/AuthDisplay'
import RoleDistribution from '../components/user/RoleDistribution'
import AuthDistribution from '../components/role/AuthDistribution'
Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location){
    return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    mode:'history',
    routes : [
        {
            path:'/',
            redirect:'/login'
        },

        {
            path:'/login',
            components: {
                appRouterView: LoginPage
            }
        },

        {
            path:'/home',
            components:{
                appRouterView: Home,
            },   

            children:[
                {
                    path: 'user',
                    components: {
                        homeRouterView: UserDisplay,
                    },

                    // component:{
                    //     render(c){
                    //         return c('homeRouterView')
                    //     }
                    // },

                    // redirect:'/home/user/roleDistribution',

                    // children:[
                    //     {
                    //         path: 'roleDistribution',
                    //         components: {
                    //             homeRouterView: RoleDistribution
                    //         }
                    //     }
                    // ]
                },

                {
                    path: 'user/:userId/roleDistribution',
                    components: {
                        homeRouterView: RoleDistribution
                    },
                    props: true,
                },

                {
                    path: 'role',
                    components: {
                        homeRouterView: RoleDisplay
                    }
                },
                {
                    path: 'role/:roleId/authDistribution',
                    components:{
                        homeRouterView:AuthDistribution
                    },
                    props: true,
                },
                
                {
                    path: 'auth',
                    components: {
                        homeRouterView: AuthDisplay
                    }
                },

            ]
        }
    ],
    
})

export default router;