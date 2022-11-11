<template>
    
    <div>
        <router-link class="list-group-item"  active-class="" to="/home/user">用户管理</router-link>&nbsp;
        <router-link class="list-group-item" active-class="" to="/home/role">角色管理</router-link>&nbsp;
        <router-link class="list-group-item" active-class="" to="/home/auth">权限管理</router-link>

            <el-button @click="logout">退出登录</el-button>

        <router-view name="homeRouterView"/>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name : 'Home',

    data(){
        return {
            s : 'Hello world',
        }
    },

    methods:{
        logout(){
            axios.get('http://localhost/logout')
            .then(res => {
                if(res.data.code === 200){
                    this.$message.success('退出登录成功')
                    this.$router.push('/');    
                }else{
                    this.$message.error('退出失败')
                }
            })
        }
    }
    
}
</script>