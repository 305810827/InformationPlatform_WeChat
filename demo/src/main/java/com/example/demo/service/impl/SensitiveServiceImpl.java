package com.example.demo.service.impl;


import com.example.demo.dao.SensitiveDao;
import com.example.demo.entity.Sensitive;
import com.example.demo.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SensitiveServiceImpl implements SensitiveService {

    @Autowired
    private SensitiveDao sensitiveDao;

    @Override
    public List<Sensitive> selectAllSensitive(String word) {
        List<Sensitive> sensitiveList = sensitiveDao.selectAllSensitive(word);
        return sensitiveList;
    }

    @Override
    @Transactional//抛出异常之后，事务会自动回滚
    public boolean addSensitive(Sensitive sensitive) {
        if(sensitive.getWord()!=null && !"".equals(sensitive.getWord())){
            Date time = new Date();
            sensitive.setTime(time);
            try {
                int row = sensitiveDao.addSensitive(sensitive);
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
    public boolean updateSensitive(Sensitive sensitive) {
        if(sensitive.getId()!=null && sensitive.getId()>0){
            sensitive.setTime(new Date());
            try {
                int row = sensitiveDao.updateSensitive(sensitive);
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
    public boolean deleteSensitive(Integer id) {
        if(id!=null && id>0){
            try {
                int row = sensitiveDao.deleteSensitive(id);
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
