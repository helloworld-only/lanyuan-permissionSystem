<template>
<div class="app-container">
    <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
            <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            >新增</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            @click="handleDelete"
            >删除</el-button>
        </el-col>
    </el-row>

    <el-table :data="tableData" :stripe="true" style="width:100">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column key="id" prop="id" align="center" label="编号"></el-table-column>
        <el-table-column key="acct" prop="acct" align="center" label="用户账号"></el-table-column>
        <el-table-column key="userName" prop="userName" align="center" label="用户昵称"></el-table-column>
        <el-table-column key="roleName" prop="roleName" align="center" label="角色名称"></el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">

            <template slot-scope="scope"> <!-- v-if="scope.row.userId != 1" -->
                <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"       
                >删除</el-button>
            </template>
        </el-table-column>
    </el-table> 

    <!-- 弹出层 -->
    <el-dialog :title="layerTitle" :visible.sync="layerOpen" width="600px" append-to-body>
        <el-form ref="form" :model="formData" :rules="rules" label-width="80px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="用户编号" prop="userId">
                    <el-input v-model="formData.userId" disabled placeholder="请输入用户编号" maxlength="11" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="用户账号" prop="acct">
                    <el-input v-model="formData.acct" :disabled="acctInputDisabled" placeholder="请输入用户账号" maxlength="8" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="用户密码" prop="passwd">
                    <el-input v-model="formData.passwd" placeholder="请输入用户密码" maxlength="15" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="用户昵称" prop="userName">
                    <el-input v-model="formData.userName" placeholder="请输入用户昵称" maxlength="10" />
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
        </div>
    </el-dialog>
</div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
export default {
    name: "UserDisplay",
    data(){
    return { 
        tableData:[
            {"id":1,"userId":1,"acct":"20221102","passwd":"123","userName":"admin","roleId":1,"roleName":"admin"},
            {"id":2,"userId":2,"acct":"20221102","passwd":"123","userName":"admin","roleId":1,"roleName":"admin"}
        ],

        layerOpen:false,
        rules:{},
        formData:{},
        layerTitle:'',
        acctInputDisabled:true,
    }
    },
    created(){
        //this.getAllRolesByUserId();
    },
    methods:{

        // 获取数据库中所有用户
        getAllRolesByUserId(){
            const url = 'http://localhost' + this.$route.path;
            axios.get(url)
            .then(res => {
                this.tableData = res.data;
            })
            .catch(error =>{
                console.log(error)
            })
        },

        // 弹出添加数据的弹出层
        handleAdd(){
            this.layerOpen=true;
            this.layerTitle='角色分配';
            this.acctInputDisabled=false;
        },

        submitForm(){
            this.submitAddForm();
        },

        // 提交添加数据的表单
        submitAddForm(){
            axios({
            url:'http://localhost/home/user/add',
            method:'post',
            data:qs.stringify(this.formData),
            }).then(res => {
            if(res.data.code === 200){
                // const newUser = res.data.data;
                // this.tableData.push(newUser);
                this.getAllUsers()
                this.$message.success('添加成功');
            }
            }).catch(err=>{
            this.$message.error('添加失败');
            }).finally(()=>{
            this.layerOpen = false;
            this.formData={};// 添加后一定要清空formData，不然添加新用户时，新用户的userId就是当前所添加的用户的userId
            })
        },

        // 取消添加/更新数据
        cancel(){
            this.layerOpen = false;
        },

        // 向后端发起删除请求
        handleDelete(row){
            const id = row.id;
            this.$confirm('是否确认删除用户编号为"' + row.userId + '"的' + row.roleName + '角色',{
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'warning',
            }).then(()=>{
                const url = "http://localhost/home/user/roleDistribution/delete" + id;
                axios.get(url)
                .then(res =>{
                    // 这里不采用访问后端接口来更新数据，而是通过前端删除该条数据（建立在删除请求成功后执行）
                    this.tableData = this.tableData.filter((item)=>{
                        return item.id != id;
                    });
                    this.$message.success('删除成功');
                }).catch(err=>{
                    this.$message.error('删除失败' + err)
                })
            }).catch(()=>{
                this.$message.info('取消删除成功')
            })  
        },

    }
};
</script>