package com.example.demo.dao;

import com.example.demo.entity.Comment;

import java.util.List;

public interface CommentDao {
    /**
     * 显示该资讯的评论
     * @return
     */
    List<Comment> CommentListByArticleId(int articleId,int start,int end);

    /**
     * 后台显示所有评论
     * @return
     */
    List<Comment> queryComment(String keyword);

    /**
     * 查询某资讯评论总数
     * @return
     */
    int CommentCountByArticleId(int articleId);

    /**
     * 显示该用户的评论
     * @return
     */
    List<Comment> CommentListByUser(String openId);


    /**
     * 添加新评论
     * @return
     */
    int AddComment(Comment comment);

    /**
     * 根据id删除评论
     * @param commentId
     * @return
     */
    int deleteComment(Integer commentId);

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
    int updateCommentPraise(Comment comment);

    /**
     * 获取评论表最大ID
     * @param
     */
    int getMaxCommentId();
}
