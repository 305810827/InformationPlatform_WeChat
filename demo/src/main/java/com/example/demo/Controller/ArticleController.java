package com.example.demo.Controller;


import com.example.demo.entity.Article;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import com.example.demo.util.FileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jie on 2018/3/15.
 */
@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Value("${web.upload-path}")
    private String path;


    @RequestMapping(value = "/getAllArticle",method = RequestMethod.POST)
    public Map<String,Object> getAllArticle(@RequestParam("title")String title, @RequestParam("currentPage")int currentPage){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(currentPage,7);
        List<Article> list= articleService.queryArticleByTitle(title);
        PageInfo<Article> pageInfo=new PageInfo<>(list);
        map.put("article",list);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @RequestMapping(value = "/addArticle",method = RequestMethod.POST)
    public Map<String,Object> addArticle(@RequestParam("title")String title,
                                         @RequestParam("image")MultipartFile image,
                                         @RequestParam("desc")String desc,
                                         @RequestParam("classify")String classify,
                                         @RequestParam("content")String content){
        String paths = path + "article";
        //String fileName = image.getOriginalFilename();
        //2上传失败提示
        String warning = "";
        String imageUrl = FileUtils.upload(image, paths);
        if (imageUrl != "false") {
            //上传成功
            warning = "success";

        } else {
            warning = "fail";
        }
        System.out.println(imageUrl);
        Map<String,Object> map = new HashMap<>();
        articleService.insertArticle(new Article(title,imageUrl,desc,classify,content,0));
        map.put("warning",warning);
        return map;
    }

    @RequestMapping(value = "/wxGetArticleList",method = RequestMethod.GET)
    public Map<String,Object> wxGetArticleList(@RequestParam("pages")int pages) {
        int number = 5 + pages;
        List<Article> ArticleList= articleService.queryArticle(pages,number);
        Map<String,Object> map = new HashMap<>();
        map.put("article",ArticleList);
        return map;
    }

    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/wxGetArticleDetail",method = RequestMethod.GET)
    public Map<String,Object> wxGetArticleDetail(@RequestParam("id")int id) {
        Article article = articleService.queryArticleById(id);
        int count = commentService.commentCountByArticleId(id);
        System.out.println("count"+count);
        Map<String,Object> map = new HashMap<>();
        map.put("count",count);
        map.put("article",article);
        return map;
    }

    @RequestMapping(value = "/getArticleByClassify",method = RequestMethod.GET)
    public Map<String,Object> getArticleByClassify(@RequestParam("classifyName")String classifyName, @RequestParam("pages")int pages) {
        int start = pages;
        int end = pages + 5;
        List<Article> articleList = articleService.getArticleByClassify(classifyName,start,end);
        System.out.println(classifyName+pages);
        Map<String,Object> map = new HashMap<>();
        map.put("articleList",articleList);
        return map;
    }

    @RequestMapping(value = "/searchArticle",method = RequestMethod.GET)
    public Map<String,Object> searchArticle(@RequestParam("searchData")String searchData, @RequestParam("pages")int pages) {
        int start = pages;
        int end = pages + 5;
        List<Article> articleList = articleService.searchArticle(searchData,start,end);
        System.out.println(searchData+pages);
        Map<String,Object> map = new HashMap<>();
        map.put("articleList",articleList);
        return map;
    }

    @RequestMapping(value = "/editArticle", method = RequestMethod.POST)
    public Map<String, Object> editArticle(@RequestParam("id")Integer id,
                                           @RequestParam("title")String title,
                                           @RequestParam(value = "image",required=false)MultipartFile image,
                                           @RequestParam("desc")String desc,
                                           @RequestParam("classify")String classify,
                                           @RequestParam("content")String content) {
        String imageUrl = new String();
        String warning = "";
        if(image != null){
            String paths = path + "article";
            imageUrl = FileUtils.upload(image, paths);
            if (imageUrl != "false") {
                //上传成功
                warning = "successEdit";
            } else {
                warning = "failEdit";
            }
        }
        Article article = new Article(id,title,imageUrl,desc,classify,content);
        articleService.updateArticle(article);
        Map<String, Object> map = new HashMap<>();
        map.put("warning", warning);
        return map;
    }

    @RequestMapping(value = "/updateArticleCollect", method = RequestMethod.GET)
    public Map<String, Object> updateArticleCollect(@RequestParam("id")int id, @RequestParam("isCollect")boolean isCollect){
        int count = articleService.queryArticleById(id).getCollectCount();
        if(isCollect)
        {
            count++;
        }else
        {
            count--;
        }
        System.out.println(count);
        Article article = new Article();
        article.setCollectCount(count);
        article.setArticleId(id);
        articleService.updateArticleCollect(article);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "true");
        return map;
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST)
    public Map<String, Object> deleteArticle(@RequestParam("id") int id) {
        Article article = articleService.queryArticleById(id);
        String imageUrl = article.getImageUrl();
        FileUtils.deleteFile(imageUrl);
        articleService.deleteArticle(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "true");
        return map;
    }

    @RequestMapping(value = "/deletesArticle", method = RequestMethod.POST)
    public Map<String, Object> deletesArticle(@RequestParam("id[]")int[] id) {
        // int[] 转 List<Integer>
        //List<Integer> list = Arrays.stream(id).boxed().collect(Collectors.toList());
        for(int i = 0 ; i < id.length; i++)
        {
            Article article = articleService.queryArticleById(id[i]);
            String imageUrl = article.getImageUrl();
            FileUtils.deleteFile(imageUrl);
            articleService.deleteArticle(id[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", "true");
        return map;
    }



//    @Autowired
//    private CommentService commentService;
//
//    @RequestMapping(value = "/getCommentList", method = RequestMethod.GET)
//    public Map<String,Object> getCommentList(@RequestParam("articleId")int articleId, @RequestParam("pages")int pages){
//        System.out.println("articleId"+articleId);
//        int start = pages;
//        int end = start + 10;
//        List<Comment> commentList = commentService.CommentListByArticleId(articleId,start,end);
//        Map<String,Object> map = new HashMap<>();
//        map.put("commentList",commentList);
//        return map;
//    }
}
