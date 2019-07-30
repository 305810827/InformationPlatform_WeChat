<template>
  <div class="container">
    <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
      <FormItem prop="account">
        <Input type="text" v-model="formInline.classifyName" placeholder="类别名">
        <Icon type="ios-person-outline" slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit()">查找</Button>
      </FormItem>
      <FormItem>
          <Button type="primary" @click="addClassify = true">新增类别</Button>
      </FormItem>
    </Form>
    <Table border :columns="headerName" :data="classifyList"></Table>
    <Page :total="total" :page-size="5" @on-change="changePage" style="padding-top: 10px;"></Page>

    <Modal
      v-model="addClassify"
      title="新增类别"
      ok-text="添加"
      class-name="vertical-center-modal"
      :closable="false"
      :mask-closable="false"
      @on-ok="ok('addClassifyInfo')"
    >
      <Form ref="addClassifyInfo" :model="addClassifyInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="类别名称" prop="classifyName">
          <Input v-model="addClassifyInfo.classifyName" placeholder="" style="width: 300px"></Input>
        </FormItem>
        <FormItem label="类别图片" prop="file">
          <div class="addUpload" @click="beforeUpload">
            <Icon type="ios-camera" size="25" ></Icon>
          </div>
          <input type="file" name="avatar" accept="image/gif,image/jpeg,image/jpg,image/png" style="display:none" @change="changeImage($event)" ref="avatarInput">
          <div v-show="visible" >
            <img :src="this.avatar" class="uploadImage">
          </div>
        </FormItem>
      </Form>
    </Modal>

    <Modal
      v-model="editClassify"
      title="编辑类别"
      ok-text="修改"
      class-name="vertical-center-modal"
      :closable="false"
      :mask-closable="false"
      @on-ok="okEdit('editClassifyInfo')"
    >
      <Form ref="editClassifyInfo" :model="editClassifyInfo" :rules="addRuleItem" :label-width="80">
        <FormItem label="类别名称" prop="classifyName">
          <Input v-model="editClassifyInfo.classifyName" placeholder="" style="width: 300px"></Input>
        </FormItem>
        <FormItem label="类别图片" prop="file">
          <div class="addUpload" @click="beforeUpload1">
            <Icon type="ios-camera" size="25" ></Icon>
          </div>
          <input type="file" name="avatar" accept="image/gif,image/jpeg,image/jpg,image/png" style="display:none" @change="changeImage1($event)" ref="avatarInput1">
          <div v-show="editVisible" >
            <img :src="this.editAvatar" class="uploadImage">
          </div>
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

  </div>
</template>
<script type="es6">
export default {
  name: 'ClassifyManage',
  data () {
    return {
      visible: false,
      editVisible: false,
      deleteModel: false,
      delete_loading: false,
      deleteId: 0,
      total: 0,
      classifyList: [],
      addClassify: false,
      editClassify: false,
      avatar: '',
      editAvatar: '',
      formInline: {
        classifyName: ''
      },
      ruleInline: {
      },
      addClassifyInfo: {
        classifyName: '',
        file: null
      },
      editClassifyInfo: {
        id: 0,
        classifyName: '',
        file: null
      },
      addRuleItem: {
        classifyName: [{
          required: true,
          message: '请填写类别名称！',
          trigger: 'blur'
        }],
        file: [{
          required: true,
          message: '请上传封面图片！'
        }]
      },
      headerName: [
        {
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
        },
        {
          title: '类别名称',
          key: 'classifyName'
        },
        {
          title: '封面图片',
          key: 'image',
          render: (h, params) => {
            return h('div', [
              h('img', {
                attrs: {
                  src: params.row.image
                },
                style: {
                  width: '90px',
                  height: '90px',
                  paddingTop: '5px'
                }
              })
            ])
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 200,
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
        }
      ]
    }
  },
  // mounted是vue实例挂载的事件钩子函数
  mounted () {
    this.getClassify(1)
  },
  methods: {
    handleSubmit () {
      this.getClassify(1)
    },
    edit (index) {
      this.editClassify = true
      this.editClassifyInfo.id = this.classifyList[index].id
      this.editClassifyInfo.classifyName = this.classifyList[index].classifyName
      this.editAvatar = this.classifyList[index].image
      this.editVisible = true
    },
    beforeUpload1 () {
      this.$refs.avatarInput1.click()
    },
    changeImage1 (e) {
      var file = e.target.files[0]
      this.editClassifyInfo.file = file
      var reader = new FileReader()
      var that = this
      reader.readAsDataURL(file)
      reader.onload = function (e) {
        that.editAvatar = this.result
        that.editVisible = true
      }
    },
    okEdit (editClassifyInfo) {
      var that = this
      var editFormData = new FormData()
      this.$refs[editClassifyInfo].validate((valid) => {
        console.log(valid)
        console.log(this.$refs[editClassifyInfo])
        if (valid) {
          editFormData.append('id', that.editClassifyInfo.id)
          editFormData.append('classifyName', that.editClassifyInfo.classifyName)
          editFormData.append('image', that.editClassifyInfo.file)
          that.$http.post(that.GLOBAL.serverPath + '/classify/editClassify',
            editFormData,
            {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }
          ).then(function (res) {
            if (res.data.warning === 'successEdit') {
              that.$Message.success('更新成功')
              that.$Notice.config({
                top: 50,
                duration: 3,
                title: '通知',
                desc: '更新资讯成功!'
              })
              that.getClassify(1)
            }
          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    },
    remove (id) {
      console.log(id)
      this.deleteModel = true
      this.deleteId = id
    },
    okDelete () {
      var that = this
      this.$http.post(
        that.GLOBAL.serverPath + '/classify/deleteClassify',
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
        this.getClassify(1)
      })
    },
    getClassify (currentPage) {
      var that = this
      this.$http.post(that.GLOBAL.serverPath + '/classify/classifyListByName',
        {
          classifyName: that.formInline.classifyName,
          currentPage: currentPage
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        that.total = res.data.pageInfo.total
        that.classifyList = res.data.classifyList
      })
    },
    changePage: function (page) {
      this.getClassify(page)
    },
    ok (addClassifyInfo) {
      var that = this
      var formData = new FormData()
      this.$refs[addClassifyInfo].validate((valid) => {
        console.log(addClassifyInfo.classifyName)
        console.log(addClassifyInfo.file)
        if (valid) {
          formData.append('classifyName', that.addClassifyInfo.classifyName)
          formData.append('file', that.addClassifyInfo.file)
          that.$http.post(that.GLOBAL.serverPath + '/classify/addClassify',
            formData,
            {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }
          ).then(function (res) {
            console.log(res.data)
            if (res.data.warning === 'success') {
              // that.$Message.success('新增成功')
              that.$Notice.config({
                top: 50,
                duration: 3,
                title: '通知',
                desc: '新增类别成功!'
              })
              that.addClassifyInfo.classifyName = ''
              that.addClassifyInfo.file = null
              that.getClassify(1)
            }
          }).catch((e) => {
            that.$Message.fail('网络有误！')
          })
        }
      })
    },
    handleFormatError (file) {
      this.$Notice.warning({
        title: 'The file format is incorrect',
        desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
      })
    },
    beforeUpload () {
      this.$refs.avatarInput.click()
    },
    changeImage (e) {
      var file = e.target.files[0]
      this.addClassifyInfo.file = file
      var reader = new FileReader()
      var that = this
      reader.readAsDataURL(file)
      reader.onload = function (e) {
        that.avatar = this.result
        that.visible = true
      }
    }
  }
}
</script>

<style>
  .vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .ivu-modal{
    top: 0;
  }
  .ql-container {
    height: 200px;
  }

  .addUpload{
    width: 68px;height:68px;line-height: 68px;border: 1px dashed #ccc;text-align:center;
  }
  .addUpload:hover{
    border:1px dashed #0000CD;
  }
  .uploadImage{
    width: 150px;
    height: 150px;
  }
</style>
