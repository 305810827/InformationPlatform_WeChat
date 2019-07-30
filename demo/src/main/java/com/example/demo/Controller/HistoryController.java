package com.example.demo.Controller;


import com.example.demo.entity.History;
import com.example.demo.service.HistoryService;
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
@RequestMapping(value = "/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/getAllHistory", method = RequestMethod.GET)
    public Map<String,Object> getAllHistory(@RequestParam("month")int month, @RequestParam("day")int day){
        List<History> historyList = historyService.queryHistory(month,day);
        System.out.println("historyList"+historyList);
        Map<String,Object> map = new HashMap<>();
        map.put("historyList",historyList);
        return map;
    }

    @RequestMapping(value = "/historyDetailById", method = RequestMethod.GET)
    public Map<String,Object> historyDetailById(@RequestParam("historyId")int historyId){
        History history = historyService.historyDetailById(historyId);
        System.out.println("history"+history);
        Map<String,Object> map = new HashMap<>();
        map.put("history",history);
        return map;
    }
}
