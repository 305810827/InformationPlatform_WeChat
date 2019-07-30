package com.example.demo.service.impl;


import com.example.demo.dao.HistoryDao;
import com.example.demo.entity.History;
import com.example.demo.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Override
    public List<History> queryHistory(int month, int day) {
        return historyDao.queryHistory(month,day);
    }

    @Override
    public History historyDetailById(int historyId) {
        return historyDao.historyDetailById(historyId);
    }

}
