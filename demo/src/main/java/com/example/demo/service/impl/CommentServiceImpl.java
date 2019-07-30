package com.example.demo.service.impl;


import com.example.demo.dao.CommentDao;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> commentListByArticleId(int article, int start, int end) {

        List<Comment> commentList = commentDao.CommentListByArticleId(article,start,end);
        return commentList;
    }

    @Override
    public List<Comment> queryComment(String keyword) {
        List<Comment> commentList = commentDao.queryComment(keyword);
        return commentList;
    }


    @Override
    public int commentCountByArticleId(int articleId){
        int count = commentDao.CommentCountByArticleId(articleId);
        return count;
    }

    @Override
    public List<Comment> commentListByUser(String openId) {

        List<Comment> commentList = commentDao.CommentListByUser(openId);
        return commentList;
    }

    @Override
    @Transactional//抛出异常之后，事务会自动回滚
    public boolean addComment(Comment comment) {
        if(comment.getContent()!=null && !"".equals(comment.getContent())){
            Date time = new Date();
            comment.setDate(time);
            try {
                int row = commentDao.AddComment(comment);
                if(row>0){
                    return true;
                }else {
                    throw new RuntimeException("插入评论信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("插入评论信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("评论信息不能为空！");
        }
    }

    @Override
    @Transactional
    public boolean deleteComment(Integer commentId) {
        if(commentId!=null && commentId>0){
            try {
                int row = commentDao.deleteComment(commentId);
                if(row>0){
                    return true;
                }else {
                    throw new RuntimeException("删除区域信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("删除区域信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("区域id不能为空！");
        }
    }

    @Override
    public int poinsCountBycommentId(Integer commentId){
        int count = commentDao.poinsCountBycommentId(commentId);
        return count;
    }

    @Override
    @Transactional
    public boolean updateCommentPraise(Comment comment) {
        if(comment.getId()!=null && comment.getId()>0){
            try {
                int row = commentDao.updateCommentPraise(comment);
                if(row>0){
                    return true;
                }else {
                    throw new RuntimeException("更新区域信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("更新区域信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("区域id不能为空！");
        }
    }

    @Override
    public int getMaxCommentId(){
        int count = commentDao.getMaxCommentId();
        return count;
    }
}
