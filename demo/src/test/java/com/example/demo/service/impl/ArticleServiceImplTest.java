package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Article;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private UserDao userDao;

//    @Test
//    @Ignore
//    public void queryArticle() {
//        List<Article> articleList = articleService.queryArticle();
//        assertEquals(1,articleList.size());
//    }

    @Test
    @Ignore
    public void insertArticle() {
        Article article = new Article();
        article.setArticleTitle("1");
        article.setArticleClassify("1");
        article.setArticleContent("1");
        article.setArticleDesc("1");
        boolean effectedNum = articleService.insertArticle(article);
        assertEquals(true,effectedNum);


    }

    @Test
    @Ignore
    public void updateArticle() {
        Article article = new Article();
        article.setArticleDesc("杰哥");
        article.setArticleId(1);
        boolean effectedNum = articleService.updateArticle(article);
        assertEquals(true,effectedNum);
    }

    @Test
    @Ignore
    public void deleteArticle() {

        boolean effectedNum = articleService.deleteArticle(2);
        assertEquals(true,effectedNum);
    }
}