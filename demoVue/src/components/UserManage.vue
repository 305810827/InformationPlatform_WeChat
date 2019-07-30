<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.account" placeholder="账号">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="modal1 = true">新添用户</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns7" :data="userList"></Table>
    <Page :total="total" :page-size="10" @on-change="changePage" style="padding-top: 10px;"></Page>

    <Modal
      v-model="modal1"
      title="新添用户"
      @on-ok="ok('addArticleInfo')"
      >
      <Form ref="addArticleInfo" :model="addArticleInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="账号" prop="id ">
          <Input v-model="addArticleInfo.id" placeholder=""></Input>
        </FormItem>
        <FormItem label="用户名" prop="username">
          <Input v-model="addArticleInfo.username" placeholder=""></Input>
        </FormItem>
        <FormItem label="密码" prop="password">
          <Input v-model="addArticleInfo.password" placeholder=""></Input>
        </FormItem>
        <FormItem label="姓名" prop="name">
          <Input v-model="addArticleInfo.name" placeholder=""></Input>
        </FormItem>
        <FormItem label="身份" prop="role">
          <Select v-model="addArticleInfo.role">
            <Option value="0">管理员</Option>
            <Option value="1">超级管理员</Option>
          </Select>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script type="es6">
export default {
  name: 'UserManage',
  data () {
    return {
      userList: [],
      total: 1,
      role: '',
      modal1: false,
      formInline: {
        account: ''
      },
      ruleInline: {
      },
      addArticleInfo: {
        id: '',
        username: '',
        password: '',
        name: '',
        role: '0'
      },
      addRuleItem: {
        username: [{
          required: true,
          message: '请填写账号！',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请填写密码！',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请填写学生姓名',
          trigger: 'blur'
        }]
      },
      columns7: [
        {
          title: '编号',
          key: 'id',
          width: 100,
          render: (h, params) => {
            return h('div', [
              h('Icon', {
                props: {
                  type: 'person'
                }
              }),
              h('strong', params.row.id)
            ])
          }
        },
        {
          title: '账号',
          key: 'username'
        },
        {
          title: '密码',
          key: 'password'
        },
        {
          title: '姓名',
          key: 'name'
        },
        {
          title: '身份',
          key: 'role'
        },
        {
          title: '操作',
          key: 'action',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.show(params.index)
                  }
                }
              }, '查看'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.remove(params.index)
                  }
                }
              }, '删除')
            ])
          }
        }
      ]
    }
  },
  mounted () {
    this.request(1)
  },
  methods: {
    handleSubmit (username) {
      this.request(1)
    },
    show (index) {
      if (this.userList[index].role === 'admin') {
        this.role = '管理员'
      } else {
        this.role = '超级管理员'
      }
      this.$Modal.info({
        title: '用户信息',
        content: `姓名：${this.userList[index].name}<br>用户账号：${this.userList[index].username}<br>身份：${this.role}`
      })
    },
    remove (index) {
      this.userList.splice(index, 1)
    },
    request (currentPage) {
      var that = this
      this.$http.post(that.GLOBAL.serverPath + '/user/getAllUsers',
        {
          userName: that.formInline.account,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log(res.data.users)
        that.total = res.data.pageInfo.total
        that.userList = res.data.users
      }).catch((e) => {
        this.$Message.fail('网络有误！')
      })
    },
    changePage: function (page) {
      this.request(page)
    },
    ok (name) {
      var that = this
      this.$refs[name].validate((valid) => {
        if (valid) {
          that.$http.post(that.GLOBAL.serverPath + '/excise/addReader',
            {
              account: that.addArticleInfo.account,
              name: that.addArticleInfo.name,
              sex: that.addArticleInfo.sex,
              condi: that.addArticleInfo.condi
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            console.log(res.data.status)
            if (res.data.status === 'ok') {
              that.$Message.success('新增成功')
              that.formInline.account = ''
              that.request(1)
            } else {
              that.$Message.error('已存在该学号的用户')
            }
          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    }
  }
}
</script>
