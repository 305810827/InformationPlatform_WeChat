package com.example.demo.service;

import com.example.demo.entity.Sensitive;

import java.util.List;

public interface SensitiveService {

    /**
     * 读取所有敏感字汇
     * @return
     */
    List<Sensitive> selectAllSensitive(String word);

    /**
     * 添加新的敏感字汇
     * @return
     */
    boolean addSensitive(Sensitive sensitive);

    /**
     * 修改敏感字汇
     * @param sensitive
     * @return
     */
    boolean updateSensitive(Sensitive sensitive);

    /**
     * 删除敏感字汇
     * @return
     */
    boolean deleteSensitive(Integer id);
}
