package com.example.demo.dao;

import com.example.demo.entity.Sensitive;

import java.util.List;

public interface SensitiveDao {
    /**
     * 读取所有敏感字汇
     * @return
     */
    List<Sensitive> selectAllSensitive(String word);

    /**
     * 添加新的敏感字汇
     * @return
     */
    int addSensitive(Sensitive sensitive);

    /**
     * 修改敏感字汇
     * @param sensitive
     * @return
     */
    int updateSensitive(Sensitive sensitive);

    /**
     * 删除敏感字汇
     * @return
     */
    int deleteSensitive(Integer id);
}
