package com.example.demo.service.impl;


import com.example.demo.dao.ClassifyDao;
import com.example.demo.entity.Classify;
import com.example.demo.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyDao classifyDao;

    @Override
    public List<Classify> queryClassify() {
        List<Classify> classifyList = classifyDao.queryClassify();
        return classifyList;
    }

    @Override
    public List<Classify> classifyListByName(String classifyName) {
        List<Classify> classifyList = classifyDao.classifyListByName(classifyName);
        return classifyList;
    }

    @Override
    public  Classify getClassify(int classifyId){
        Classify classify = classifyDao.getClassify(classifyId);
        return classify;
    }

    @Override
    @Transactional//抛出异常之后，事务会自动回滚
    public boolean AddClassify(Classify classify) {
        if(classify.getClassifyName()!=null && !"".equals(classify.getClassifyName())){
            try {
                int row = classifyDao.AddClassify(classify);
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
    public boolean updateClassify(Classify classify) {
        if(classify.getId()!=null && classify.getId()>0){
            try {
                int row = classifyDao.updateClassify(classify);
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
    public boolean deleteClassify(Integer classifyId) {
        if(classifyId!=null && classifyId>0){
            try {
                int row = classifyDao.deleteClassify(classifyId);
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
