<template>
    
    <div>
        <router-link class="list-group-item" active-class="" to="/home/user">用户管理</router-link>
        <br/>
        <router-link class="list-group-item" active-class="" to="/home/user/add">用户添加</router-link>
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