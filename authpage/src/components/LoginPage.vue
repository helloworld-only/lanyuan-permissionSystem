<template>
    <div class="login">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <h3 class="title">若依后台管理系统</h3>

          <!-- 账号 -->
          <el-form-item prop="acct">
            <el-input
              v-model="loginForm.acct"
              type="text"
              auto-complete="off"
              placeholder="账号"
            >
              <!-- <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" /> -->
            </el-input>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item prop="passwd">
            <el-input
              v-model="loginForm.passwd"
              type="password"
              auto-complete="off"
              placeholder="密码"
              @keyup.enter.native="handleForm"
            >
              <!-- <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" /> -->
            </el-input>
          </el-form-item>

          <!-- 验证码 -->
          <el-form-item prop="code" v-if="captchaEnabled">
            <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              style="width: 63%"
              @keyup.enter.native="handleForm"
            >
              <!-- <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" /> -->
            </el-input>
            <div class="login-code">
              <img :src="imgCode" @click="getVerifyCode" class="login-code-img"/>
            </div>
          </el-form-item>

          <!-- 记住密码 -->
          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>

          <el-form-item style="width:100%;">
            <!-- 登录按钮 -->
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:100%;"
              @click.native.prevent="handleForm"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>

            <!-- 注册（超链接） -->
            <div style="float: right;" v-if="register">
              <router-link class="link-type" :to="'/register'">立即注册</router-link>
            </div>
          </el-form-item>
          
        </el-form>


        <!--  底部  -->
        <div class="el-login-footer">
            <span>Copyright © 2018-2022 ruoyi.vip All Rights Reserved.</span>
        </div>
    </div>
</template>
<script>

import Cookies from "js-cookie";
import axios from 'axios'
import qs from 'qs'

export default {
  name: "LoginPage",
  data() {
    return {
      imgCode: "",
      loginForm: {
        acct: "20221102",
        passwd: "123",
        rememberMe: false,
        code: "12",
        uuid: ""
      },
      loginRules: {
        acct: [
          // { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        passwd: [
          // { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [
          // { required: true, trigger: "change", message: "请输入验证码" }
        ]
      },
      loading: false,
      
      captchaEnabled: true,// 验证码开关
      
      register: true,// 注册开关
      redirect: undefined
    };
  },

  created(){
    this.getVerifyCode();
  },

  methods:{
    // 获取验证码
    getVerifyCode(){
      const that = this;
      const xhr = new XMLHttpRequest();
      const url = 'http://localhost/captchaImg';
      xhr.open('GET', url , true);
      xhr.responseType = 'json';
      xhr.onload = function(){
        if(this.response.code === 200){
          that.imgCode=this.response.data.code;
        }
      }
      xhr.send();

    },

    // 处理登录（验证登录信息是否均已填写、提交表单至后端接口）
    handleForm(){
      axios({
        url: 'http://localhost/home',
        method: 'post',
        data:qs.stringify(this.loginForm),
        header:{
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        console.log(res);
        if(res.data.code === 200){
          console.log(res.data.msg);
          this.$router.push({
            path:'/home',
          })
        }else{
          this.getVerifyCode();
          console.log(res.data.msg);
          // 弹出层，提示错误类型
        }
      })
      .catch(error => {
        console.log(error)
        this.getVerifyCode()
      }) 
      .finally(()=>{
        console.log('请求结束')
      })
    },
    
  }
  
};
</script>

<style rel="stylesheet" lang="css">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-image: url("../assets/images/login-background.jpg");
    background-size: cover;
  }
  .title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
  }

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;
    /* .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 2px;
    } */
  }
  .login-form > .el-input{
      height:38px;
    }
  .login-form > .el-input > input{
    height:38px;
  }
  .login-form > .input-icon{
    height: 39px;
      width: 14px;
      margin-left: 2px;
  }

  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    height: 38px;
    float: right;
    /* img {
      cursor: pointer;
      vertical-align: middle;
    } */
  }
  .login-code > img {
    cursor: pointer;
    vertical-align: middle;
  }

  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
  .login-code-img {
    height: 38px;
  }
</style>
