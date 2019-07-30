<template>
  <div class="layout">
    <Row type="flex">
      <Col span="5" class="layout-menu-left">
      <Menu active-name="1-1" theme="dark" width="auto" :open-names="['1']">
        <div class="layout-logo-left">
          <h2 style="color:white;">大开眼界</h2>
        </div>
        <Submenu name="1">
          <template slot="title">
            <Icon type="ios-navigate"></Icon>
            后台管理
          </template>
          <MenuItem name="1-1" @click.native="lookUser"><span>用户管理</span></MenuItem>
          <MenuItem name="1-2" @click.native="lookArticle"><span>文章管理</span></MenuItem>
          <MenuItem name="1-3" @click.native="lookComment"><span>评论管理</span></MenuItem>
          <MenuItem name="1-4" @click.native="lookSensitive"><span>敏感字汇管理</span></MenuItem>
          <MenuItem name="1-5" @click.native="lookClassify"><span>文章类别管理</span></MenuItem>
          <!--ru-->
        </Submenu>
      </Menu>
      </Col>
      <Col span="19">
      <div class="layout-header"></div>
        <div class="layout-breadcrumb">
        <Breadcrumb>
          <BreadcrumbItem href="#">{{one_nav}}</BreadcrumbItem>
          <BreadcrumbItem href="#">{{two_nav}}</BreadcrumbItem>
          <BreadcrumbItem>{{three_nav}}</BreadcrumbItem>
        </Breadcrumb>
          <div class="user-avatar-dropdown">
            <p class="user-avatar-text">{{manageName}}，欢迎回来</p>
            <Dropdown @on-click="handleClick">
                <Avatar :src="userAvatar"/>
              <Icon :size="18" type="md-arrow-dropdown"></Icon>
              <DropdownMenu slot="list">
                <DropdownItem name="logout">退出登录</DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </div>
      </div>
        <div class="layout-content">
        <div class="layout-content-main">
          <template id="userManage"></template>
          <component :is="currentView"></component>
        </div>
      </div>
      <div class="layout-copy">
        2018-2019 &copy; 微信小程序大开眼界后台管理
      </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import userManage from '../components/UserManage.vue'
import Button from 'iview/src/components/button/button'
import articleManage from '../components/ArticleManage.vue'
import commentManage from '../components/CommentManage.vue'
import sensitiveManage from '../components/SensitiveManage.vue'
import historyManage from '../components/HistoryManage.vue'
import classifyManage from '../components/ClassifyManage.vue'
export default {
  name: 'Index',
  data () {
    return {
      msg: 'haha',
      one_nav: '主页',
      two_nav: '后台管理',
      three_nav: '用户管理',
      currentView: 'userManage',
      manageName: '',
      userAvatar: ''
    }
  },
  mounted () {
    this.manageName = window.localStorage.getItem('name')
    this.userAvatar = window.localStorage.getItem('imageUrl')
  },
  methods: {
    lookUser () {
      this.one_nav = '主页'
      this.two_nav = '后台管理'
      this.three_nav = '用户管理'
      this.currentView = 'userManage'
    },
    lookArticle () {
      this.one_nav = '主页'
      this.two_nav = '后台管理'
      this.three_nav = '文章资讯管理'
      this.currentView = 'articleManage'
    },
    lookComment () {
      this.one_nav = '主页'
      this.two_nav = '后台管理'
      this.three_nav = '评论管理'
      this.currentView = 'commentManage'
    },
    lookSensitive () {
      this.one_nav = '主页'
      this.two_nav = '后台管理'
      this.three_nav = '敏感字汇管理'
      this.currentView = 'sensitiveManage'
    },
    lookClassify () {
      this.one_nav = '主页'
      this.two_nav = '后台管理'
      this.three_nav = '文章类别管理'
      this.currentView = 'classifyManage'
    },
    lookHistory () {
      this.one_nav = '主页'
      this.two_nav = '后台管理'
      this.three_nav = '历史上今天的管理'
      this.currentView = 'historyManage'
    },
    logout () {
      this.$http.post(this.GLOBAL.serverPath + '/login/logout',
        {
        },
        {
          emulateJSON: true
        }
      ).then(function (res) {
        console.log(res.data.code)
        window.localStorage.clear()
        this.$router.replace({path: '/'})
      })
    },
    handleClick (name) {
      switch (name) {
        case 'logout': this.logout()
          break
      }
    }
  },
  components: {
    Button,
    userManage: userManage,
    articleManage: articleManage,
    commentManage: commentManage,
    sensitiveManage: sensitiveManage,
    classifyManage: classifyManage,
    historyManage: historyManage
  }
}
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    margin-top:-60px;
  }
  .layout-breadcrumb{
    padding: 10px 15px 0;
    /*text-align:left;*/
    display:flex;
  }
  .layout-content{
    min-height: 200px;
    margin: 15px;
    overflow: hidden;
    background: #fff;
    border-radius: 4px;
  }
  .layout-content-main{
    padding: 10px;
  }
  .layout-copy{
    text-align: center;
    padding: 10px 0 20px;
    color: #9ea7b4;
  }
  .layout-menu-left{
    background: #464c5b;
  }
  .layout-header{
    height: 60px;
    background: #fff;
    box-shadow: 0 1px 1px rgba(0,0,0,.1);
  }
  .layout-logo-left{
    width: 90%;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    margin: 15px auto;
  }
  Button{
    color:white;
  }
  .user-avatar-text{
    padding: 10px;
    font-weight: bold;
  }
  .user-avatar-dropdown{
    display: flex;
    margin-left: auto;
  }

</style>
