package com.example.demo.service;

import com.example.demo.entity.History;

import java.util.List;

public interface HistoryService {
    /**
     * 查询显示所有的历史
     * @return
     */
    List<History> queryHistory(int month, int day);

    /**
     * 根据ID查询详细内容
     * @return
     */
    History historyDetailById(int historyId);
}
