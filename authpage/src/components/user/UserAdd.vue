<template>
  <div class="app-container">
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="Dialog Title">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="单行文本" prop="acct">
          <el-input v-model="formData.acct" placeholder="请输入单行文本" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="passwd">
          <el-input v-model="formData.passwd" placeholder="请输入密码" clearable show-password
            :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="单行文本" prop="userName">
          <el-input v-model="formData.userName" placeholder="请输入单行文本" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  inheritAttrs: false,
  components: {},
  props: [],
  data() {
    return {
      formData: {
        acct: undefined,
        passwd: undefined,
        userName: undefined,
      },
      rules: {
        acct: [{
          required: true,
          message: '请输入单行文本',
          trigger: 'blur'
        }],
        passwd: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }],
        userName: [{
          required: true,
          message: '请输入单行文本',
          trigger: 'blur'
        }],
      },
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onOpen() {},
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.close()
      })
    },
  }
}

</script>
<style>
</style>
