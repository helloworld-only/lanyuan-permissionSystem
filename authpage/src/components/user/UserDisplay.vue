<template>
<div class="app-container">
  <el-table :data="tableData" :stripe="true" style="width:100">
    <el-table-column type="selection" width="50" align="center" />
    <el-table-column key="userId" prop="userId" align="center" label="用户编号"></el-table-column>
    <el-table-column key="acct" prop="acct" align="center" label="用户账号"></el-table-column>
    <el-table-column key="userName" prop="userName" align="center" label="用户昵称"></el-table-column>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template slot-scope="scope" v-if="scope.row.userId != 1">
        <el-button
          size="mini"
          type="text"
          icon="el-icon-edit"
          @click="handleUpdate(scope.row)"
          
        >修改</el-button>
        <!-- 
          v-hasPermi="['system:user:edit']"
          v-hasPermi="['system:user:remove']" -->
        <el-button
          size="mini"
          type="text"
          icon="el-icon-delete"
          @click="handleDelete(scope.row)"
          
        >删除</el-button>
      </template>
    </el-table-column>
  </el-table> 

  <el-dialog :title="layerTitle" :visible.sync="layerOpen" width="600px" append-to-body>
    <el-form ref="form" :model="formData" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户编号" prop="userId">
            <el-input v-model="formData.userId" disabled placeholder="请输入用户编号" maxlength="30" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户账号" prop="passwd">
            <el-input v-model="formData.acct" disabled placeholder="请输入用户账号" maxlength="11" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户密码" prop="acct">
            <el-input v-model="formData.passwd" placeholder="请输入用户密码" maxlength="30" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户昵称" prop="userName">
            <el-input v-model="formData.userName" placeholder="请输入用户昵称" maxlength="11" />
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
          {"userId":1,"acct":"20221102","passwd":"123","userName":"admin"},
          {"userId":2,"acct":"20221102","passwd":"123","userName":"admin"}
        ],

        layerOpen:false,
        rules:{},
        formData:{},

        layerTitle:'',
    }
  },
  created(){
    this.getAllUsers();
  },
  methods:{

    // 获取数据库中所有用户
    getAllUsers(){
        const url = 'http://localhost' + this.$route.path;
        axios.get(url)
        .then(res => {
            this.tableData = res.data;
        })
        .catch(error =>{
            console.log(error)
        })
    },

    // 弹出更新数据的弹出层
    handleUpdate(row){
      this.formData = row;
      this.layerOpen = true;
      this.layerTitle="修改用户信息"
    },

    // 提交更新数据的表单
    submitForm(){
      axios({
        url:'http://localhost/home/user/update',
        method:'post',
        data:qs.stringify(this.formData),
      }).then(res => {
        if(res.data.code === 200){
          this.tableData = this.tableData.filter(item => {
            if (this.formData.userId == this.item.userId){
              this.item.passwd = this.formData.passwd;
              this.item.userName = this.formData.userName;
            }
            return true;
          });
          this.$message.success('更新成功');
        }
      }).catch(err=>{
        this.$message.error('更新失败');
      }).finally(()=>{
        this.layerOpen = false;
      })
    },
    // 取消更新数据
    cancel(){
      this.layerOpen = false;
    },

    // 向后端发起删除请求
    handleDelete(row){
      const userId = row.userId;
      this.$confirm('是否确认删除用户编号为"' + userId + '"的数据项？',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning',
      }).then(()=>{
        const url = "http://localhost/home/user/delete/" + userId;
        axios.get(url)
        .then(res =>{
          // 这里不采用访问后端接口来更新数据，而是通过前端删除该条数据（建立在删除请求成功后执行）
          this.tableData = this.tableData.filter((item)=>{
            return item.userId != userId;
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