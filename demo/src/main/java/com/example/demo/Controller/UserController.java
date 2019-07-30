package com.example.demo.Controller;


import com.example.demo.entity.User;
import com.example.demo.entity.WxUser;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jie on 2018/3/15.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @RequestMapping(value = "/getAllUsers",method = RequestMethod.POST)
    public Map<String,Object> getAllUsers(@RequestParam("userName")String userName, @RequestParam("currentPage")int currentPage){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(currentPage,10);
        List<User> list= userService.getUserListByUsername(userName);
        System.out.println("list"+list);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        map.put("users",list);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @RequestMapping(value = "/insertUserInfo",method = RequestMethod.POST)
    public Map<String,Object> insertUserInfo(@RequestParam("nickName")String nickName, @RequestParam("avatarUrl")String avatarUrl,  @RequestParam("gender")int gender, @RequestParam("openId")String openId){
        String sex;
        if(gender == 0){
            sex = "未知";
        }else if(gender == 1)
        {
            sex = "男性";
        }else{
            sex = "女性";
        }
        WxUser wxUser = new WxUser(sex,nickName,avatarUrl,openId);
        userService.updateWxUser(wxUser);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","success");
        return map;
    }

    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/userCommentPraise",method = RequestMethod.POST)
    public Map<String,Object> userCommentPraise(@RequestParam("openid")String openid){
        int max = commentService.getMaxCommentId()+1;
        String userCommentPraise =  userService.getCommentPraise(openid);
        String[] userPraiseArray;
        boolean[] Judgement = new boolean[max];

        if(userCommentPraise!=null&&!"".equals(userCommentPraise)&&!"null".equals(userCommentPraise)&&userCommentPraise!=""){
            userPraiseArray = userCommentPraise.split(",");
            for(int i = 0; i <  userPraiseArray.length; i++){
                Judgement[Integer.parseInt(userPraiseArray[i])] = true;
            }
        }else{

            Judgement[max-1] = false;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("commentPraise",Judgement);
        return map;
    }

}
