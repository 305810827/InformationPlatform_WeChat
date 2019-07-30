<template>
  <div class="container">
    <div style="position: absolute;margin: 20px; font-weight: bold; font-family: Microsoft YaHei">
      批量删除 <i-switch v-model="showCheckbox" style="margin-right: 5px"></i-switch>
    </div>
    <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.title" placeholder="文章名">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit()">查找</Button>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="addArticle = true">新添资讯</Button>
      </FormItem>
    </Form>
    <Table border ref="selection"
           :columns="headerName"
           :data="articleList"
           @on-selection-change="handleSelectRow"></Table>

    <Button v-if="showCheckbox" @click="deletes()" type="error" style="position:absolute; margin-left: -540px; margin-top: 10px">批量删除</Button>

    <Page :total="total" :page-size="10" @on-change="changePage" style="padding-top: 10px;"></Page>

    <Modal
      v-model="addArticle"
      title="新添资讯"
      scrollable
      ok-text="添加"
      fullscreen
      @on-ok="ok('addArticleInfo')"
    >
      <Form ref="addArticleInfo" :model="addArticleInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="文章标题" prop="title">
          <Input v-model="addArticleInfo.title" placeholder=""></Input>
        </FormItem>
        <FormItem label="封面图片" prop="image">
          <div class="addUpload" @click="beforeUpload">
            <Icon v-if=!visible type="ios-camera" size="25" ></Icon>
            <div v-else >
              <img :src="this.avatar" class="uploadImage">
            </div>
          </div>
          <input type="file" name="avatar" accept="image/gif,image/jpeg,image/jpg,image/png" style="display:none" @change="changeImage($event)" ref="avatarInput">
        </FormItem>
        <FormItem label="描述" prop="desc">
          <Input v-model="addArticleInfo.desc" placeholder=""></Input>
        </FormItem>
        <FormItem label="类别" prop="classify">
          <Select v-model="addArticleInfo.classify" placeholder="">
            <Option v-for="item in classifyList" :value="item.classifyName" :key="item.id">{{item.classifyName}}</Option>
          </Select>
        </FormItem>
        <FormItem label="内容" prop="content">
          <template v-if="addArticle">
            <!--<Input v-model="addArticleInfo.descri" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="书籍描述..."></Input>-->
            <quill-editor v-model="addArticleInfo.content" ref="VueQuillEditor"
                          :content="content"
            >
            </quill-editor>
          </template>
          <!-- 文件上传input 将它隐藏-->
          <!--<el-upload class="upload-demo" :action="qnLocation" :before-upload='beforeUpload' :data="uploadData" :on-success='upScuccess'-->
          <!--ref="upload" style="display:none">-->
          <!--<el-button size="small" type="primary" id="imgInput" v-loading.fullscreen.lock="fullscreenLoading" element-loading-text="插入中,请稍候">点击上传</el-button>-->
          <!--</el-upload>-->

        </FormItem>
      </Form>
    </Modal>

    <Modal
      v-model="editArticle"
      title="编辑资讯"
      scrollable
      ok-text="修改"
      fullscreen
      @on-ok="okEdit('editArticleInfo')"
    >
      <Form ref="editArticleInfo" :model="editArticleInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="文章标题" prop="title">
          <Input v-model="editArticleInfo.title" placeholder=""></Input>
        </FormItem>
        <FormItem label="封面图片" prop="image">
          <div class="addUpload" @click="beforeUpload1">
            <Icon v-if=!this.editVisible type="ios-camera" size="25" ></Icon>
            <div v-else >
              <img :src="this.editAvatar" class="uploadImage">
            </div>
          </div>
          <input type="file" name="avatar" accept="image/gif,image/jpeg,image/jpg,image/png" style="display:none" @change="changeImage1($event)" ref="avatarInput1">
        </FormItem>
        <FormItem label="描述" prop="desc">
          <Input v-model="editArticleInfo.desc" placeholder=""></Input>
        </FormItem>
        <FormItem label="类别" prop="classify">
          <Select v-model="editArticleInfo.classify" placeholder="">
            <Option v-for="item in classifyList" :value="item.classifyName" :key="item.id">{{item.classifyName}}</Option>
          </Select>
        </FormItem>
        <FormItem label="内容" prop="content">
          <template v-if="editArticle">
            <!--<Input v-model="addArticleInfo.descri" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="书籍描述..."></Input>-->
            <quill-editor v-model="editArticleInfo.content"
                          ref="VueQuillEditor"
                          :content="content"
            >
            </quill-editor>
          </template>
          <!-- 文件上传input 将它隐藏-->
          <!--<el-upload class="upload-demo" :action="qnLocation" :before-upload='beforeUpload' :data="uploadData" :on-success='upScuccess'-->
          <!--ref="upload" style="display:none">-->
          <!--<el-button size="small" type="primary" id="imgInput" v-loading.fullscreen.lock="fullscreenLoading" element-loading-text="插入中,请稍候">点击上传</el-button>-->
          <!--</el-upload>-->

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
  name: 'ArticleManage',
  data () {
    return {
      total: 0,
      visible: false,
      deleteModel: false,
      deletesModel: false,
      delete_loading: false,
      deleteId: 0,
      avatar: '../assets/loginBg.gif',
      classifyList: [],
      addArticle: false,
      editArticle: false,
      editArticleInfo: {},
      editArticleImage: null,
      editAvatar: '',
      editVisible: false,
      content: '',
      currIndex: 0, // 最近被点击添加编号副本的图书编号
      formInline: {
        title: ''
      },
      ruleInline: {
      },
      addArticleInfo: {
        title: '',
        image: null,
        desc: '',
        classify: '',
        content: ''
      },
      addRuleItem: {
        title: [{
          required: false,
          message: '请填写文章标题！',
          trigger: 'blur'
        }],
        image: [{
          required: false,
          message: '请上传封面图片！'
        }],
        desc: [{
          required: false,
          message: '请填写描述！',
          trigger: 'blur'
        }],
        classify: [{
          required: false,
          message: '请选择分类',
          trigger: 'blur'
        }],
        content: [{
          required: false,
          message: '请填写文章内容',
          trigger: 'blur'
        }]
      },
      showCheckbox: false,
      deletesId: [],
      articleList: []
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
        title: '文章标题',
        key: 'title'
      })
      columns.push({
        title: '文章描述',
        key: 'desc'
      })
      columns.push({
        title: '发布时间',
        key: 'createTime',
        sortable: true
      })
      columns.push({
        title: '类别',
        key: 'classify',
        filters: [
          {
            label: 'Greater than 25',
            value: 1
          },
          {
            label: 'Less than 25',
            value: 2
          }
        ],
        filterMultiple: false,
        filterMethod (value, row) {
          if (value === 1) {
            return row.age > 25
          } else if (value === 2) {
            return row.age < 25
          }
        }
      })
      columns.push({
        title: '被收藏次数',
        key: 'collectCount'
      })
      if (!this.showCheckbox) {
        columns.push({
          title: '操作',
          key: 'action',
          width: 250,
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
    this.getClassify()
  },
  methods: {
    handleSubmit () {
      this.request(1)
    },
    show (index) {
      this.$Modal.info({
        title: '资讯信息',
        width: '1100',
        content: `<text class="textStyle">标题：</text>${this.articleList[index].title}<br><text class="textStyle">封面图片：</text><image src="${this.articleList[index].imageUrl}" style="width: 162;height:98px"></image><br><text class="textStyle">描述：</text>${this.articleList[index].desc}<br><text class="textStyle">内容：</text><div style="padding-left: 40px;">${this.articleList[index].content}</div>`
      })
    },
    edit (index) {
      this.editArticle = true
      this.editArticleInfo.id = this.articleList[index].id
      this.editArticleInfo.title = this.articleList[index].title
      this.editArticleInfo.desc = this.articleList[index].desc
      this.editArticleInfo.classify = this.articleList[index].classify
      this.editArticleInfo.content = this.articleList[index].content
      this.editAvatar = this.articleList[index].imageUrl
      this.editVisible = true
    },
    okDelete () {
      var that = this
      this.delete_loading = true
      this.$http.post(
        that.GLOBAL.serverPath + '/article/deleteArticle',
        {
          id: that.deleteId
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
      this.$http.post(that.GLOBAL.serverPath + '/article/getAllArticle',
        {
          title: that.formInline.title,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        that.articleList = []
        that.total = res.data.pageInfo.total
        res.data.article.forEach((e) => {
          let obj = {}
          obj.id = e.articleId
          obj.title = e.articleTitle
          obj.createTime = that.formatDate(e.createTime)
          obj.desc = e.articleDesc
          obj.imageUrl = e.imageUrl
          obj.classify = e.articleClassify
          obj.collectCount = e.collectCount
          obj.content = e.articleContent
          that.articleList.push(obj)
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
    ok (addArticleInfo) {
      var that = this
      var formData = new FormData()
      this.$refs[addArticleInfo].validate((valid) => {
        if (valid) {
          formData.append('title', that.addArticleInfo.title)
          formData.append('image', that.addArticleInfo.image)
          formData.append('desc', that.addArticleInfo.desc)
          formData.append('classify', that.addArticleInfo.classify)
          formData.append('content', that.addArticleInfo.content)
          that.$http.post(that.GLOBAL.serverPath + '/article/addArticle',
            formData,
            {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
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
              that.addArticleInfo.title = ''
              that.addArticleInfo.image = null
              that.addArticleInfo.desc = ''
              that.addArticleInfo.classify = ''
              that.addArticleInfo.content = ''
              that.request(1)
            }
          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    },
    // onEditorChange ({editor, html, text}) {
    //   // 富文本编辑器，文本改变时，设置字段值
    //   this.content = html
    // },
    beforeUpload () {
      this.$refs.avatarInput.click()
    },
    changeImage (e) {
      var file = e.target.files[0]
      this.addArticleInfo.image = file
      var reader = new FileReader()
      var that = this
      reader.readAsDataURL(file)
      reader.onload = function (e) {
        that.avatar = this.result
        that.visible = true
      }
    },
    beforeUpload1 () {
      this.$refs.avatarInput1.click()
    },
    changeImage1 (e) {
      var file = e.target.files[0]
      this.editArticleInfo.image = file
      var reader = new FileReader()
      var that = this
      reader.readAsDataURL(file)
      reader.onload = function (e) {
        that.editAvatar = this.result
        that.editVisible = true
      }
    },
    okEdit (editArticleInfo) {
      var that = this
      var editFormData = new FormData()
      this.$refs[editArticleInfo].validate((valid) => {
        if (valid) {
          console.log(that.editArticleInfo.id)
          editFormData.append('id', that.editArticleInfo.id)
          editFormData.append('title', that.editArticleInfo.title)
          editFormData.append('image', that.editArticleInfo.image)
          editFormData.append('desc', that.editArticleInfo.desc)
          editFormData.append('classify', that.editArticleInfo.classify)
          editFormData.append('content', that.editArticleInfo.content)
          console.log(editFormData)
          that.$http.post(that.GLOBAL.serverPath + '/article/editArticle',
            editFormData,
            {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
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
        that.GLOBAL.serverPath + '/article/deletesArticle',
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
  /*.limit {*/
  /*height: 30px;*/
  /*border: 1px solid #ccc;*/
  /*line-height: 30px;*/
  /*text-align: right;*/
  /*}*/
  /*.limit span {*/
  /*color: #ee2a7b;*/
  /*}*/
  .ql-snow .ql-editor img {
    max-width: 480px;
  }

  .ql-editor .ql-video {
    max-width: 480px;
  }
  .textStyle{
    font-size: 30px;
    font-family:Microsoft YaHei;
    font-weight:bold;
    line-height:200%;
    color: red;
  }

  .addUpload{
    width: 162px;
    height:98px;
    line-height: 98px;
    border: 1px dashed #ccc;
    text-align:center;
    vertical-align: middle;
  }
  .addUpload:hover{
    border:1px dashed #0000CD;
  }
  .uploadImage{
    width: 160px;
    height: 96px;
  }
</style>
