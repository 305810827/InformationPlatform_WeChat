package com.example.demo.dao;

import com.example.demo.entity.History;

import java.util.List;

public interface HistoryDao {
    /**
     * 显示的历史资讯列表
     * @return
     */
    List<History> queryHistory(int month, int day);

    /**
     * 根据ID查询详细内容
     * @return
     */
    History historyDetailById(int historyId);


}
