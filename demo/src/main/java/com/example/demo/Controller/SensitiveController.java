package com.example.demo.Controller;


import com.example.demo.entity.Sensitive;
import com.example.demo.service.SensitiveService;
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
@RequestMapping(value = "/sensitive")
public class SensitiveController {

    @Autowired
    private SensitiveService sensitiveService;

    @RequestMapping(value = "/selectAllSensitive", method = RequestMethod.POST)
    public Map<String,Object> selectAllSensitive(@RequestParam("word")String word, @RequestParam("currentPage")int currentPage){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(currentPage,10);
        List<Sensitive> sensitiveList = sensitiveService.selectAllSensitive(word);
        PageInfo<Sensitive> pageInfo = new PageInfo<>(sensitiveList);
        map.put("sensitiveList",sensitiveList);
        map.put("pageInfo",pageInfo);
        return map;
    }

    @RequestMapping(value = "/addSensitive", method = RequestMethod.POST)
    public Map<String,Object> addSensitive(@RequestParam("word")String word){
        Sensitive sensitive = new Sensitive();
        sensitive.setWord(word);
        sensitiveService.addSensitive(sensitive);
        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        return map;
    }

    @RequestMapping(value = "/updateSensitive", method = RequestMethod.POST)
    public Map<String, Object> updateSensitive(@RequestParam("id")Integer id, @RequestParam("word")String word) {
        Sensitive sensitive = new Sensitive(id,word);
        sensitiveService.updateSensitive(sensitive);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "success");
        return map;
    }

    @RequestMapping(value = "/deleteSensitive",method = RequestMethod.POST)
    public Map<String,Object> deleteSensitive(@RequestParam("id")int id){
        sensitiveService.deleteSensitive(id);
        Map<String,Object> map = new HashMap<>();
        map.put("success","true");
        return map;
    }

    @RequestMapping(value = "/deletesSensitive",method = RequestMethod.POST)
    public Map<String,Object> deleteSensitive(@RequestParam("id[]")int[] id){
        for(int i = 0 ; i < id.length; i++)
        {
            sensitiveService.deleteSensitive(id[i]);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success","true");
        return map;
    }
}
