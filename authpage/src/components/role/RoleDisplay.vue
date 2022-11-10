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
        <el-table-column key="roleId" prop="roleId" align="center" label="角色编号"></el-table-column>
        <el-table-column key="roleName" prop="roleName" align="center" label="角色名称"></el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope" v-if="scope.row.roleId != 1">
                <!-- 
                    v-hasPermi="['system:user:edit']"
                    v-hasPermi="['system:user:remove']" -->
                <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                    
                >删除</el-button>

                <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-setting"
                    @click="toAuthDistribution(scope.row)"
                >权限分配</el-button>
            </template>
        </el-table-column>
    </el-table> 

    <!-- 弹出层 -->
    <el-dialog :title="layerTitle" :visible.sync="layerOpen" width="600px" append-to-body>
        <el-form ref="form" :model="formData" :rules="rules" label-width="80px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="角色编号" prop="roleId">
                    <el-input v-model="formData.roleId" disabled placeholder="请输入角色编号" maxlength="11" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="formData.roleName"  placeholder="请输入角色名称" maxlength="8" />
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
    name: "RoleDisplay",
    data(){
    return { 
        tableData:[
            {"roleId":1,"roleName":"20221102"},
            {"roleId":2,"roleName":"20221104"}
        ],

        layerOpen:false,
        rules:{},
        formData:{},
        layerTitle:'',
        roleNameInputDisabled:true,
    }
    },
    created(){
        this.getAllRoles();
    },
    methods:{

        // 获取数据库中所有角色
        getAllRoles(){
            const url = 'http://localhost' + this.$route.path;
            axios.get(url)
            .then(res => {
                this.tableData = res.data.data;
            })
            .catch(error =>{
                console.log(error)
            })
        },

        // 弹出添加数据的弹出层
        handleAdd(){
            this.layerOpen=true;
            this.layerTitle='添加角色';
            this.roleNameInputDisabled=false;
        },

        submitForm(){
            this.submitAddForm();
        },

        // 提交添加数据的表单
        submitAddForm(){
            axios({
            url:'http://localhost/home/role/add',
            method:'post',
            data:qs.stringify(this.formData),
            }).then(res => {
                if(res.data.code === 200){
                    const newUser = res.data.data;
                    this.tableData.push(newUser);
                    // this.getAllRoles();
                    this.$message.success('添加成功');
                }else{
                    let failMessage = '添加失败，' + res.data.msg;
                    this.$message.error(failMessage);
                }
            }).catch(err=>{
                this.$message.error('添加失败');
            }).finally(()=>{
                this.layerOpen = false;
                this.formData={};// 添加后一定要清空formData，不然添加新用户时，新用户的userId就是当前所添加的用户的userId
            })
        },

        // 提交更新数据的表单
        submitUpdateForm(){
            axios({
            url:'http://localhost/home/user/update',
            method:'post',
            data:qs.stringify(this.formData),
            }).then(res => {
                if(res.data.code === 200){
                    this.tableData = this.tableData.filter(item => {
                    if (this.formData.roleId == item.roleId){
                        item.roleName = this.formData.roleName;
                    }
                    return true;
                    });
                    this.$message.success('更新成功');
                }else{
                    let failMessage = '更新失败，' + res.data.msg;
                    this.$message.error(failMessage);
                }
            }).catch( err =>{
                this.$message.error('更新失败');
            }).finally(()=>{
                this.layerOpen = false;
                this.formData = {}; // 更新后一定要清空formData，不然添加新用户时，新用户的userId就是当前所更新的用户的userId
            })
        },
        // 取消更新数据
        cancel(){
            this.layerOpen = false;
        },

        // 向后端发起删除请求
        handleDelete(row){
            const roleId = row.roleId;
            this.$confirm('是否确认删除角色编号为 "' + roleId + '" 的数据项？',{
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'warning',
            }).then(()=>{
                const url = "http://localhost/home/role/delete/" + roleId;
                axios.get(url)
                .then(res =>{
                    if(res.data.code === 200){
                        // 这里不采用访问后端接口来更新数据，而是通过前端删除该条数据（建立在删除请求成功后执行）
                        this.tableData = this.tableData.filter((item)=>{
                            return item.roleId != roleId;
                        });
                        this.$message.success('删除成功');
                    }else{
                        let failMessage = '删除失败，' + res.data.msg;
                        this.$message.error(failMessage);
                    }
                    
                }).catch(err=>{
                    this.$message.error('删除失败' + err)
                })
            }).catch(()=>{
                this.$message.info('取消删除成功')
            })  
        },

        toAuthDistribution(row){
            const path = this.$route.path;
            this.$router.push(path + '/' + row.roleId + '/authDistribution'); 
        }

    }
};
</script>
