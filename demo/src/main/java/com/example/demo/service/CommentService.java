package com.example.demo.service;

import com.example.demo.entity.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 查询显示所有的评论
     * @return
     */
    List<Comment> commentListByArticleId(int articleId, int start, int end);

    /**
     * 后台显示所有评论
     * @return
     */
    List<Comment> queryComment(String keyword);

    /**
     * 查询某资讯评论总数
     * @return
     */
    int commentCountByArticleId(int articleId);

    /**
     * 查询该用户的评论
     * @return
     */
    List<Comment> commentListByUser(String openId);

    /**
     * 发布新资讯
     * @param comment
     * @return
     */
    boolean addComment(Comment comment);

    /**
     * 根据id删除评论
     * @param commentId
     * @return
     */
    boolean deleteComment(Integer commentId);

    /**
     * 查询某个评论点赞数
     * @param commentId
     * @return
     */
    int poinsCountBycommentId(Integer commentId);

    /**
     * 更新某个评论点赞数
     * @param comment
     * @return
     */
    boolean updateCommentPraise(Comment comment);

    /**
     * 获取评论表最大ID
     * @param
     */
    int getMaxCommentId();

}
