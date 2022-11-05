<template>
<el-table :data="tableData" :stripe="true" style="width:100">
    <el-table-column prop="userId" label="用户编号"></el-table-column>
    <el-table-column prop="acct" label="用户账号"></el-table-column>
    <el-table-column prop="username" label="用户昵称"></el-table-column>
</el-table>
</template>

<script>
import axios from 'axios'
export default {
  name: "UserDisplay",
  data(){
    return {
        tableData:[]
    }
  },
  created(){
    this.getAllUsers();
  },
  methods:{
    getAllUsers(){
        var url = 'http://localhost' + this.$route.path;
        axios.get(url)
        .then(res => {
            this.tableData = res.data.data;
        })
        .catch(error =>{
            console.log(error)
        })
    }
  }
};
</script>