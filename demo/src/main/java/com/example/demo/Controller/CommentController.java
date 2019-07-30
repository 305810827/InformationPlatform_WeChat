package com.example.demo.Controller;


import com.example.demo.entity.Comment;
import com.example.demo.entity.Sensitive;
import com.example.demo.entity.WxUser;
import com.example.demo.service.CommentService;
import com.example.demo.service.SensitiveService;
import com.example.demo.service.UserService;
import com.example.demo.util.SensiteWord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * Created by Jie on 2018/3/15.
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //微信小程序展示评论区数据
    @RequestMapping(value = "/getCommentList", method = RequestMethod.GET)
    public Map<String,Object> getCommentList(@RequestParam("articleId")int articleId, @RequestParam("pages")int pages){
        int start = pages;
        int end = start + 10;
        List<Comment> commentList = commentService.commentListByArticleId(articleId,start,end);
        //int count = commentService.commentCountByArticleId(articleId);
       // System.out.println("count"+count);
        Map<String,Object> map = new HashMap<>();
        map.put("commentList",commentList);
//        map.put("count",count);
        return map;
    }

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/commentPraise", method = RequestMethod.POST)
    public Map<String,Object> commentPraise(@RequestParam("commentId")int commentId,@RequestParam("isCommentPraise")boolean isCommentPraise,@RequestParam("openid")String openid){
        int count = commentService.poinsCountBycommentId(commentId);
        String commentPraiseId = userService.getCommentPraise(openid);
        System.out.println("commentPraiseId.length()"+commentPraiseId.length());
        if(isCommentPraise)
        {
            count++;
            if(commentPraiseId.equals("") || commentPraiseId.equals(null)){
                commentPraiseId = String.valueOf(commentId);
                System.out.println(commentPraiseId+" System.out.println(commentPraiseId)1");
            }else{
                commentPraiseId = commentPraiseId.concat(","+commentId);
                System.out.println(commentPraiseId+" System.out.println(commentPraiseId)2");
            }
            System.out.println(commentPraiseId+"commentPraiseId1");
        }else
        {
            count--;
            if(commentPraiseId.length() == 1){
                commentPraiseId = commentPraiseId.substring(0,commentPraiseId.length()-1);
                System.out.println(commentPraiseId+"commentPraiseId2");
            }else{
                commentPraiseId = commentPraiseId.substring(0,commentPraiseId.length()-2);
                System.out.println(commentPraiseId+"commentPraiseId3");
            }
        }
        System.out.println(count);
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setPointsCount(count);
        WxUser wxuser = new WxUser();
        wxuser.setOpenid(openid);
        wxuser.setCommentPraise(commentPraiseId);
        commentService.updateCommentPraise(comment);
        userService.updateCommentPraise(wxuser);

        int max = commentService.getMaxCommentId()+1;
        String userCommentPraise =  userService.getCommentPraise(openid);
        String[] userPraiseArray;
        boolean[] Judgement = new boolean[max];
        if(userCommentPraise!=null&&!"".equals(userCommentPraise)&&!"null".equals(userCommentPraise)&&userCommentPraise!=""){
            userPraiseArray = userCommentPraise.split(",");
            int len = userPraiseArray.length;
            for(int i = 0; i < len; i++){
                Judgement[Integer.parseInt(userPraiseArray[i])] = true;
                System.out.println(Integer.parseInt(userPraiseArray[i]));
            }
        }
        int counts = commentService.poinsCountBycommentId(commentId);
        System.out.println(Judgement);
        Map<String, Object> map = new HashMap<>();
        map.put("commentPraise",Judgement);
        map.put("counts", counts);
        return map;
    }

    //后台获取所有评论数据
    @RequestMapping(value = "/queryComment", method = RequestMethod.POST)
    public Map<String,Object> queryComment(@RequestParam("keyword")String keyword, @RequestParam("currentPage")int currentPage){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(currentPage,10);
        List<Comment> list= commentService.queryComment(keyword);
        PageInfo<Comment> pageInfo=new PageInfo<>(list);
        map.put("commentList",list);
        map.put("pageInfo",pageInfo);
        return map;
    }

    //显示用户发过的评论记录
    @RequestMapping(value = "/commentListByUser", method = RequestMethod.POST)
    public Map<String,Object> commentListByUser(@RequestParam("openId")String openId){
        List<Comment> commentList = commentService.commentListByUser(openId);
        Map<String,Object> map = new HashMap<>();
        map.put("commentList",commentList);
        return map;
    }

    @Autowired
    private SensitiveService sensitiveService;
    //微信小程序发表评论
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public Map<String,Object> addComment(@RequestParam("articleId")int articleId,
                                         @RequestParam("nickName")String nickName,
                                         @RequestParam("headImageUrl")String headImageUrl,
                                         @RequestParam("content")String content,
                                         @RequestParam("openId")String openId
                                         ){
        System.out.println(openId);
        //敏感词过滤,获得敏感词库
        List<Sensitive> sensitiveList = sensitiveService.selectAllSensitive("");
        System.out.println(sensitiveList);
        Set<String> set = new HashSet<>(sensitiveList.size());
        for(Sensitive sensitive:sensitiveList){
            set.add(sensitive.getWord());
        }
        //初始化敏感词库
        HashMap map = SensiteWord.initSensitivateWord(set);
        System.out.println(map);

        String word = SensiteWord.replaceSensitiveWord(content,"*",set);
        System.out.println("content:"+word);
        //把过滤后的内容添加到数据库
        Comment comment = new Comment(articleId,nickName,headImageUrl,word,openId,0);
        commentService.addComment(comment);

        Map<String,Object> map1 = new HashMap<>();
        List<Comment> commentList = commentService.commentListByArticleId(articleId,0,10);
        map1.put("commentList",commentList);
        return map1;
    }

    //后台删除评论
    @RequestMapping(value = "/deleteComment",method = RequestMethod.POST)
    public Map<String,Object> deleteComment(@RequestParam("id") int id){
        commentService.deleteComment(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success","true");
        return map;
    }

    //后台批量删除评论
    @RequestMapping(value = "/deletesComment", method = RequestMethod.POST)
    public Map<String, Object> deletesComment(@RequestParam("id[]")int[] id) {
        // int[] 转 List<Integer>
        //List<Integer> list = Arrays.stream(id).boxed().collect(Collectors.toList());
        System.out.println(id.length);
        for(int i = 0 ; i < id.length; i++)
        {
            commentService.deleteComment(id[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", "true");
        return map;
    }
}
