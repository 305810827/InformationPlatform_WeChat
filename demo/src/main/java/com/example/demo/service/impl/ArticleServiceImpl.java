package com.example.demo.service.impl;


import com.example.demo.dao.ArticleDao;
import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> queryArticle(int start,int end) {
        return articleDao.queryArticle(start,end);
    }

    @Override
    public Article queryArticleById(int articleId) {
        return articleDao.queryArticleById(articleId);
    }

    @Override
    public List<Article> queryArticleByTitle(String articleTitle) {
        return articleDao.queryArticleByTitle(articleTitle);
    }

    @Override
    public List<Article> getArticleByClassify(String classifyName,int start,int end) {
        return articleDao.getArticleByClassify(classifyName,start,end);
    }

    @Override
    public List<Article> searchArticle(String searchData,int start,int end) {
        return articleDao.searchArticle(searchData,start,end);
    }


    @Override
    @Transactional//抛出异常之后，事务会自动回滚
    public boolean insertArticle(Article article) {
        if(article.getArticleTitle()!=null && !"".equals(article.getArticleTitle())){
            Date time = new Date();
            article.setCreateTime(time);
            try {
                int row = articleDao.insertArticle(article);
                if(row>0){
                    return true;
                }else {
                    throw new RuntimeException("插入区域信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("插入区域信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("区域信息不能为空！");
        }
    }

    @Override
    @Transactional
    public boolean updateArticle(Article article) {
        if(article.getArticleId()!=null && article.getArticleId()>0){
            article.setCreateTime(new Date());
            try {
                int row = articleDao.updateArticle(article);
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
    @Transactional
    public boolean updateArticleCollect(Article article) {
        if(article.getArticleId()!=null && article.getArticleId()>0){
            try {
                int row = articleDao.updateArticleCollect(article);
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
    @Transactional
    public boolean deleteArticle(Integer articleId) {
        if(articleId!=null && articleId>0){
            try {
                int row = articleDao.deleteArticle(articleId);
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
    @Transactional
    public boolean deletesArticle(List<Integer> list) {
        if(list!=null && list.size()>0){
            try {
                int row = articleDao.deletesArticle(list);
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
}
