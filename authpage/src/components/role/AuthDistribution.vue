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

        <!-- <span>角色分配</span> -->
    </el-row>

    <el-table :data="tableData" :stripe="true" style="width:100">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column key="id" prop="id" align="center" label="编号"></el-table-column>
        <el-table-column key="authName" prop="authName" align="center" label="已拥有权限"></el-table-column>
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
        <el-checkbox-group v-model="checkedAuthId">
            <!-- <el-checkbox-button v-for="item in unDistributedRoles" :key="item.roleId" :label="item.roleId">{{item.roleName}} </el-checkbox-button> -->
            <el-checkbox v-for="item in unDistributedAuths" :key="item.authId" :label="item.authId">{{item.authName}}</el-checkbox>
        </el-checkbox-group>
        
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
    name: "AuthDistribution",

    data(){
        return { 
            tableData:[
                {"id":1,"roleId":1,"authId":1,"authName":"admin"},
                {"id":2,"roleId":1,"authId":1,"authName":"guest"}
            ],


            allAuths:[], // 所有的权限

            unDistributedAuths:[], // 未被分配给该用户的角色

            checkedAuthId:[],

            layerOpen:false,
            rules:{},
            formData:{},
            layerTitle:'',
        }
    },
    
    created(){
        this.getAllAuthByRoleId();
        if(this.allAuths.length < 1){
            const url = 'http://localhost/home/auth';
            axios.get(url)
            .then(res => {
                this.allAuths = res.data.data
            })
            .catch(error =>{
                console.log(error)
            })
        }
    },

    methods:{

        // 获取数据库中用户所具有的角色
        getAllAuthByRoleId(){
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
            this.layerTitle='权限分配';

            const distributedAuthId = []; // 已分配给该角色的权限的权限id
            this.tableData.forEach(function(item){
                distributedAuthId.push(item['authId'])
            });

            // 判断该角色是否有所有的权限
            if(distributedAuthId.length === this.allAuths.length){
                this.unDistributedAuths = [];
            }else{
                this.unDistributedAuths = this.allAuths.filter(item=>{
                    return !distributedAuthId.includes(item.authId); 
                })
            }
        },

        submitForm(){
            this.submitAddForm();
        },

        // 提交添加数据的表单
        submitAddForm(){
            axios({
                url:'http://localhost/home/role/' + this.$route.params.roleId + '/authDistribution/add',
                // url:'http://localhost' + this.$router.path + '/add',
                method:'post',
                data:this.checkedAuthId,
            }).then(res => {
                if(res.data.code === 200){    
                    let resData = res.data.data;
                    resData.forEach(item=>{
                        let authId = item['authId'];
                        for(let obj of this.allAuths){
                            if(obj['authId'] === authId){
                                console.log(obj['authId'])
                                item['authName'] = obj['authName'];
                                console.log(item['authName'])
                                break;
                            }
                        }
                        this.tableData.push(item);
                    });
                    
                    // 后端会把插入成功后的数据返回前端，所以可以通过前端实现更新表格，就不需要再访问后端来更新表格了
                    // this.getAllAuthByRoleId()
                    this.$message.success('添加成功');
                }
            }).catch(err=>{
                this.$message.error('添加失败');
            }).finally(()=>{
                this.layerOpen = false;
                this.checkedAuthId=[];// 添加后一定要清空checked，不然添加新用户时，新用户的userId就是当前所添加的用户的userId
            })
        },

        // 取消添加/更新数据
        cancel(){
            this.layerOpen = false;
        },

        // 向后端发起删除请求
        handleDelete(row){
            const id = row.id;
            this.$confirm('是否确认删除角色编号为"' + row.roleId + '"的' + row.authName + '权限',{
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'warning',
            }).then(()=>{
                const url = "http://localhost/home/role/" + row.roleId + "/authDistribution/delete/" + id;
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