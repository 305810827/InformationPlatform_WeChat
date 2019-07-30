   <template>
  <div class="container">
    <div style="position: absolute;margin: 20px; font-weight: bold; font-family: Microsoft YaHei">
      批量删除 <i-switch v-model="showCheckbox" style="margin-right: 5px"></i-switch>
    </div>
    <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.word" placeholder="输入关键字">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit()">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="setSensitiveWordModel = true">设置敏感词汇</Button>
      </FormItem>
    </Form>

    <Table border :columns="headerName" :data="sensitiveList" ref="selection" @on-selection-change="handleSelectRow"></Table>

    <Button v-if="showCheckbox" @click="deletes()" type="error" style="position:absolute; margin-left: -540px; margin-top: 10px">批量删除</Button>

    <Page :total="total" :page-size="10" @on-change="changePage" style="padding-top: 10px;"></Page>

    <Modal
      title="新添敏感字汇"
      v-model="setSensitiveWordModel"
      ok-text="添加"
      :mask-closable="false"
      @on-ok="ok('addSensitiveInfo')"
      class-name="vertical-center-modal">
      <Form ref="addSensitiveInfo" :model="addSensitiveInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="敏感字汇" prop="word">
          <Input v-model="addSensitiveInfo.word" placeholder=""  style="width: 300px"></Input>
        </FormItem>
      </Form>
    </Modal>

    <Modal
      title="编辑"
      v-model="editSensitiveWordModel"
      ok-text="修改"
      :mask-closable="false"
      @on-ok="okEdit('editSensitiveInfo')"
      class-name="vertical-center-modal">
      <Form ref="editSensitiveInfo" :model="editSensitiveInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="敏感字汇" prop="word">
          <Input v-model="editSensitiveInfo.word" placeholder=""  style="width: 300px"></Input>
        </FormItem>
      </Form>
    </Modal>

    <Modal v-model="deleteModel" width="260">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="information-circled"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>删除后数据不可恢复,确定要这么做吗?</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long :loading="delete_loading" @click="okDelete">确定删除</Button>
      </div>
    </Modal>

    <Modal v-model="deletesModel" width="260">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="information-circled"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>删除后数据不可恢复,确定要这么做吗?</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long :loading="delete_loading" @click="okDeletes">确定删除</Button>
      </div>
    </Modal>

  </div>
</template>
<script type="es6">
export default {
  name: 'SensitiveManage',
  data () {
    return {
      total: 0,
      setSensitiveWordModel: false,
      editSensitiveWordModel: false,
      deleteModel: false,
      deletesModel: false,
      delete_loading: false,
      showCheckbox: false,
      deletesId: [],
      deleteId: 0,
      sensitiveList: [],
      formInline: {
        word: ''
      },
      ruleInline: {
      },
      addSensitiveInfo: {
        word: ''
      },
      editSensitiveInfo: {
        word: ''
      },
      addRuleItem: {
        word: [{
          required: true,
          message: '请填写敏感词汇！',
          trigger: 'blur'
        }]
      }
    }
  },
  computed: {
    headerName () {
      let columns = []
      if (this.showCheckbox) {
        columns.push({
          type: 'selection',
          width: 60,
          align: 'center'
        })
      }
      columns.push({
        title: '编号',
        width: 110,
        key: 'id',
        render: (h, params) => {
          return h('div', [
            h('Icon', {
              props: {
                type: 'document-text'
              }
            }),
            h('strong', params.row.id)
          ])
        }
      })
      columns.push({
        title: '敏感字汇',
        key: 'word'
      })
      columns.push({
        title: '发布时间',
        key: 'time',
        sortable: true
      })
      if (!this.showCheckbox) {
        columns.push({
          title: '操作',
          key: 'action',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'success',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.edit(params.index)
                  }
                }
              }, '编辑'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.remove(params.row.id)
                  }
                }
              }, '删除')
            ])
          }
        })
      }
      return columns
    }
  },
  // mounted是vue实例挂载的事件钩子函数
  mounted () {
    this.request(1)
  },
  methods: {
    handleSubmit () {
      this.request(1)
    },
    ok (addSensitiveInfo) {
      var that = this
      this.$refs[addSensitiveInfo].validate((valid) => {
        if (valid) {
          that.$http.post(that.GLOBAL.serverPath + '/sensitive/addSensitive',
            {
              word: that.addSensitiveInfo.word
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if (res.data.status === 'success') {
              that.$Message.success('新增成功')
              that.$Notice.config({
                top: 50,
                duration: 3,
                title: '通知',
                desc: '发布资讯成功!'
              })
              that.addSensitiveInfo.word = ''
              that.request(1)
            }
          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    },
    edit (index) {
      this.editSensitiveWordModel = true
      this.editSensitiveInfo.id = this.sensitiveList[index].id
      this.editSensitiveInfo.word = this.sensitiveList[index].word
    },
    okEdit (editSensitiveInfo) {
      var that = this
      this.$refs[editSensitiveInfo].validate((valid) => {
        if (valid) {
          that.$http.post(that.GLOBAL.serverPath + '/sensitive/updateSensitive',
            {
              id: that.editSensitiveInfo.id,
              word: that.editSensitiveInfo.word
            },
            {
              emulateJSON: true
            }
          ).then(function (res) {
            if (res.data.status === 'success') {
              that.$Message.success('更新成功')
              that.$Notice.config({
                top: 50,
                duration: 3,
                title: '通知',
                desc: '更新资讯成功!'
              })
              that.request(1)
            }
          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    },
    okDelete () {
      var that = this
      this.$http.post(
        that.GLOBAL.serverPath + '/sensitive/deleteSensitive',
        {
          id: this.deleteId
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        this.delete_loading = false
        this.deleteModel = false
        this.$Message.info('删除成功')
        this.request(1)
      })
    },
    remove (id) {
      this.deleteModel = true
      this.deleteId = id
    },
    request (currentPage) {
      var that = this
      this.$http.post(that.GLOBAL.serverPath + '/sensitive/selectAllSensitive',
        {
          word: that.formInline.word,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        that.sensitiveList = []
        that.total = res.data.pageInfo.total
        res.data.sensitiveList.forEach((e) => {
          let obj = {}
          obj.id = e.id
          obj.word = e.word
          obj.time = that.formatDate(e.time)
          that.sensitiveList.push(obj)
        })
      })
    },
    formatDate: function (value) {
      let date = new Date(value)
      let y = date.getFullYear()
      let MM = date.getMonth() + 1
      MM = MM < 10 ? ('0' + MM) : MM
      let d = date.getDate()
      d = d < 10 ? ('0' + d) : d
      let h = date.getHours()
      h = h < 10 ? ('0' + h) : h
      let m = date.getMinutes()
      m = m < 10 ? ('0' + m) : m
      let s = date.getSeconds()
      s = s < 10 ? ('0' + s) : s
      return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s
    },
    changePage: function (page) {
      this.request(page)
    },
    handleSelectRow (selection) {
      this.deletesId = []
      selection.forEach((e) => {
        this.deletesId.push(e.id)
      })
    },
    deletes () {
      if (this.deletesId.length === 0) {
        this.$Message.success('请选择删除项')
      } else {
        this.deletesModel = true
      }
    },
    okDeletes () {
      var that = this
      this.delete_loading = true
      this.$http.post(
        that.GLOBAL.serverPath + '/sensitive/deletesSensitive',
        {
          id: that.deletesId
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        this.delete_loading = false
        this.deletesModel = false
        this.deletesId = []
        this.$Message.info('删除成功')
        this.request(1)
      })
    }
  }
}
</script>

<style>
  .ql-container {
    height: 200px;
  }
  .vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .ivu-modal {
    top: 0;
  }
</style>
