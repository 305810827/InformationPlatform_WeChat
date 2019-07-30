package com.example.demo.dao;

import com.example.demo.entity.Classify;

import java.util.List;

public interface ClassifyDao {
    /**
     * 显示加载的分类
     * @return
     */
    List<Classify> queryClassify();

    /**
     * 根据分类名查询资讯
     * @return
     */
    List<Classify> classifyListByName(String classifyName);

    /**
     * 根据分类ID查询资讯
     * @return
     */
    Classify getClassify(int classifyId);

    /**
     * 添加新类别
     * @return
     */
    int AddClassify(Classify classify);

    /**
     * 修改类别内容
     * @param classify
     * @return
     */
    int updateClassify(Classify classify);

    /**
     * 根据id删除类别
     * @param classifyId
     * @return
     */
    int deleteClassify(Integer classifyId);

}
