<template>
  <div class="login">
    <div class="login-frame">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
    <Form ref="formItem" :model="formItem" :rules="ruleItem" :label-width="15">
        <FormItem prop="username">
          <Input type="text" v-model="formItem.username" placeholder="输入你的用户名">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <FormItem prop="password">
          <Input type="password" v-model="formItem.password" placeholder="输入你的密码">
          <Icon type="ios-lock-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
      <Row>
        <Col :xs="16" :sm="16" :md="16" :lg="16">
          <FormItem prop="code" >
            <Input type="text" v-model="formItem.code" placeholder="请输入验证码"></Input>
          </FormItem>
        </Col>
        <Col :xs="8" :sm="8" :md="8" :lg="8">
          <h2 id="verifyCode" @click="createCode()">{{verifyCode}}</h2>
        </Col>
      </Row>
      <Row>
        <Col :xs="24" :sm="24" :md="24" :lg="24">
          <Button id="login_btn" shape="circle" type="primary" :loading="loading" @click.native="handleSubmit('formItem')">
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </Button>
        </Col>
      </Row>
      <Row class="tip">
        <Col :xs="10" :sm="10" :md="10" :lg="10" offset="12">
          <!--<span class="login_font">忘记密码？点击邮箱找回</span>-->
        </Col>
      </Row>
    </Form>
      </Card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      msg: '',
      verifyCode: 'hello',
      formItem: {
        username: '',
        password: '',
        code: ''
      },
      ruleItem: {
        username: [{
          required: true,
          message: '请填写账号！',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请填写密码',
          trigger: 'blur'
        }, {
          type: 'string',
          min: 3,
          message: '密码长度不能小于6位',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请填写验证码',
          trigger: 'blur'
        }]
      },
      loading: false
    }
  },
  mounted () {
    this.createCode()
  },
  methods: {
    handleSubmit (name) {
      var that = this
      this.loading = true
      this.$refs[name].validate((valid) => {
        if (valid) {
          if (that.verifyCode === that.formItem.code) {
            that.$http.post(that.GLOBAL.serverPath + '/login/doLogin',
              {
                username: that.formItem.username,
                password: that.formItem.password
              },
              {
                emulateJSON: true
              }
            ).then(function (res) {
              if (res.data.info.result === 'success') {
                this.$Message.success('登录成功!')
                // window.localStorage.setItem('userId', res.data.loginUser.id)
                // window.localStorage.setItem('username', res.data.loginUser.username)
                window.localStorage.setItem('name', res.data.info.name)
                window.localStorage.setItem('imageUrl', res.data.info.imageUrl)
                // window.localStorage.setItem('role', res.data.loginUser.role)
                // if (res.data.role === 2) {
                //   this.$router.replace({path: '/index'})
                // } else if (res.data.role === 1) {
                //   this.$router.replace({path: '/manager'})
                // } else {
                //   this.$router.replace({path: '/reader'})
                // }
                this.$router.replace({path: '/index'})
              } else {
                this.$Message.error(res.data.info.msg)
                this.loading = false
              }
            })
          } else {
            that.$Message.error('请填写正确的验证码!')
            this.loading = false
          }
        } else {
          this.loading = false
        }
      })
    },
    createCode () {
      var code = ''
      var codeLength = 4
      var random = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
      for (var i = 0; i < codeLength; i++) {
        var index = Math.floor(Math.random() * 36)
        code += random[index]
      }
      this.verifyCode = code
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .login{
    width: 100%;
    height: 100%;
    margin: auto;
    background-image: url("../assets/loginBg.gif");
    position: relative;
  }
  .login-frame{
    position: absolute;
    right: 160px;
    top: 50%;
    transform: translateY(-60%);
    width: 300px;
  }

  #login_btn{
    width:70%;
  }
</style>
