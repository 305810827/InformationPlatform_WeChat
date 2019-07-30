package com.example.demo.Controller;


import com.example.demo.entity.Classify;
import com.example.demo.service.ClassifyService;
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
@RequestMapping(value = "/classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "/getClassifyList", method = RequestMethod.GET)
    public Map<String, Object> getClassifyList() {
        List<Classify> classifyList = classifyService.queryClassify();
        Map<String, Object> map = new HashMap<>();
        map.put("classifyList", classifyList);
        return map;
    }

    @RequestMapping(value = "/classifyListByName", method = RequestMethod.POST)
    public Map<String, Object> classifyListByName(@RequestParam("classifyName")String classifyName,@RequestParam("currentPage") int currentPage) {
        PageHelper.startPage(currentPage,5);
        List<Classify> classifyList = classifyService.classifyListByName(classifyName);
        PageInfo<Classify> pageInfo = new PageInfo<>(classifyList);
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("classifyList", classifyList);
        return map;
    }

    @RequestMapping(value = "/getClassify", method = RequestMethod.GET)
    public Map<String, Object> getClassify(@RequestParam("classifyId") int classifyId) {
        Classify classify = classifyService.getClassify(classifyId);
        System.out.println(classify.getImage());
        System.out.println(classify.getClassifyName());
        Map<String, Object> map = new HashMap<>();
        map.put("classify", classify);
        return map;
    }

    @RequestMapping(value = "/addClassify", method = RequestMethod.POST)
    public Map<String, Object> addClassify( @RequestParam("file")MultipartFile file, @RequestParam("classifyName")String classifyName) {
        String paths = path + "classify/";
        //1获得文件名字
        //String fileName = file.getOriginalFilename();
        //2上传失败提示
        String imageUrl = FileUtils.upload(file, paths);
        String warning = "";
        if (imageUrl != "false") {
            //上传成功
            warning = "success";
        } else {
            warning = "fail";
        }
        System.out.println(imageUrl);
        Classify classify = new Classify(classifyName, imageUrl);
        classifyService.AddClassify(classify);
        Map<String, Object> map = new HashMap<>();
        map.put("warning", warning);
        return map;
    }

    @RequestMapping(value = "/editClassify", method = RequestMethod.POST)
    public Map<String, Object> editClassify(@RequestParam("id")Integer id,
                                           @RequestParam("classifyName")String classifyName,
                                           @RequestParam(value = "image",required=false)MultipartFile image) {
        String warning = "";
        String imageUrl = new String();
        if(image != null){
            String paths = path + "classify";
            imageUrl = FileUtils.upload(image, paths);
            if (imageUrl != "false") {
                //上传成功
                warning = "successEdit";
            } else {
                warning = "failEdit";
            }
        }
        Classify classify = new Classify(id,classifyName,imageUrl);
        classifyService.updateClassify(classify);
        Map<String, Object> map = new HashMap<>();
        map.put("warning", warning);
        return map;
    }

    @RequestMapping(value = "/deleteClassify", method = RequestMethod.POST)
    public Map<String, Object> deleteClassify(@RequestParam("id") int id) {
        Classify classify = classifyService.getClassify(id);
        String imageUrl = classify.getImage();
        FileUtils.deleteFile(imageUrl);
        classifyService.deleteClassify(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "true");
        return map;
    }

}
