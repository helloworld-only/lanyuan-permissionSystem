<template>
    
    <div>
        <router-link class="list-group-item"  active-class="" to="/home/user">用户管理</router-link>&nbsp;
        <router-link class="list-group-item" active-class="" to="/home/role">角色管理</router-link>&nbsp;
        <router-link class="list-group-item" active-class="" to="/home/auth">权限管理</router-link>
        <router-view name="homeRouterView"/>
    </div>
</template>

<script>
import UserDisplay from './user/UserDisplay';
export default {
    name : 'Home',
    components:{
        UserDisplay,
    },
    data(){
        return {
            s : 'Hello world',
        }
    },
    
}
</script>