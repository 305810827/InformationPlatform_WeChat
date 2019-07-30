   <template>
  <div class="container">
    <div style="position: absolute;margin: 20px; font-weight: bold; font-family: Microsoft YaHei">
      批量删除 <i-switch v-model="showCheckbox" style="margin-right: 5px"></i-switch>
    </div>
    <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
      <FormItem>
        <Select v-model="formInline.classify" placeholder="文章类别">
          <Option v-for="item in classifyList" :value="item.classifyName" :key="item.id">{{item.classifyName}}</Option>
        </Select>
      </FormItem>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.keyword" placeholder="文章名">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit()">查找</Button>
      </FormItem>
    </Form>

    <Table border :columns="headerName" :data="commentList" ref="selection" @on-selection-change="handleSelectRow"></Table>

    <Button v-if="showCheckbox" @click="deletes()" type="error" style="position:absolute; margin-left: -540px; margin-top: 10px">批量删除</Button>

    <Page :total="total" :page-size="10" @on-change="changePage" style="padding-top: 10px;"></Page>

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
  name: 'ArticleManage',
  data () {
    return {
      total: 0,
      deleteModel: false,
      deletesModel: false,
      delete_loading: false,
      showCheckbox: false,
      deletesId: [],
      deleteId: 0,
      classifyList: [],
      formInline: {
        classify: '',
        keyword: ''
      },
      ruleInline: {
      },
      commentList: []
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
        title: '资讯ID',
        width: 110,
        key: 'articleId'
      })
      columns.push({
        title: '发表人',
        key: 'name'
      })
      columns.push({
        title: '发布时间',
        key: 'createTime',
        sortable: true
      })
      columns.push({
        title: '评论内容',
        key: 'content'
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
    console.log('初步加载')
    this.request(1)
    this.getClassify()
  },
  methods: {
    handleSubmit () {
      this.request(1)
    },
    okDelete () {
      var that = this
      this.$http.post(
        that.GLOBAL.serverPath + '/comment/deleteComment',
        {
          id: this.deleteId
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log(res.data)
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
      console.log('that.formInline.title' + this.formInline.title)
      this.$http.post(that.GLOBAL.serverPath + '/comment/queryComment',
        {
          keyword: that.formInline.keyword,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log(res.data.commentList)
        console.log(res.data)
        that.commentList = []
        that.total = res.data.pageInfo.total
        res.data.commentList.forEach((e) => {
          console.log(e)
          let obj = {}
          obj.id = e.id
          obj.name = e.wxName
          obj.createTime = that.formatDate(e.date)
          obj.articleId = e.articleId
          obj.content = e.content
          that.commentList.push(obj)
        })
      })
    },
    getClassify () {
      var that = this
      this.$http.get(that.GLOBAL.serverPath + '/classify/getClassifyList',
        {
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log('res.data.classifyList' + res.data.classifyList)
        that.classifyList = res.data.classifyList
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
        console.log(e.id)
      })
      console.log(this.deletesId)
    },
    deletes () {
      if (this.deletesId.length === 0) {
        this.$Message.success('请选择删除项')
      } else {
        this.deletesModel = true
        console.log(this.deletesId)
      }
    },
    okDeletes () {
      var that = this
      this.delete_loading = true
      this.$http.post(
        that.GLOBAL.serverPath + '/comment/deletesComment',
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
</style>
