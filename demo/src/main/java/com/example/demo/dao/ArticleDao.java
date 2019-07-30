package com.example.demo.dao;

import com.example.demo.entity.Article;

import java.util.List;

public interface ArticleDao {
    /**
     * 显示加载的资讯
     * @return
     */
    List<Article> queryArticle(int start,int end);

    /**
     * 根据id查询资讯
     * @param articleId
     * @return
     */
    Article queryArticleById(int articleId);

    /**
     * 根据标题查询资讯
     * @param articleTitle
     * @return
     */
    List<Article> queryArticleByTitle(String articleTitle);

    /**
     * 根据分类查询资讯
     * @param classifyName
     * @return
     */
    List<Article> getArticleByClassify(String classifyName,int start,int end);

    /**
     * 根据标题描述搜索资讯
     * @param searchData
     * @return
     */
    List<Article> searchArticle(String searchData,int start,int end);


    /**
     * 发布新资讯
     * @param article
     * @return
     */
    int insertArticle(Article article);

    /**
     * 修改资讯内容
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 更新收藏次数
     * @param article
     * @return
     */
    int updateArticleCollect(Article article);


    /**
     * 根据id删除资讯
     * @param articleId
     * @return
     */
    int deleteArticle(Integer articleId);

    /**
     * 根据id删除资讯
     * @param list
     * @return
     */
    int deletesArticle(List<Integer> list);
}
