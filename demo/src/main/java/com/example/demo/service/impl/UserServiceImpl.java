package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.entity.WxUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: xyj
 * @description: 登录service实现类
 * @date: 2019/3/24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional//抛出异常之后，事务会自动回滚
    public boolean addWxUser(WxUser wxUser) {
        if(wxUser.getOpenid()!=null && !"".equals(wxUser.getOpenid())){
            try {
                int row = userDao.addWxUser(wxUser);
                if(row>0){
                    return true;
                }else {
                    throw new RuntimeException("添加微信用户信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("添加微信用户信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("不能为空！");
        }
    }

    @Override
    @Transactional
    public boolean updateWxUser(WxUser wxUser) {
        if(wxUser.getOpenid()!=null){
            try {
                int row = userDao.updateWxUser(wxUser);
                if(row>0){
                    return true;
                }else {
                    throw new RuntimeException("更新微信用户信息失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("更新微信用户信息失败："+e.getMessage());
            }
        }else{
            throw new RuntimeException("微信用户openid不能为空！");
        }
    }

    /**
     * 根据openId查询对应的微信用户
     */
    @Override
    public WxUser getWxUserByOpenId(String openId) {
        return userDao.getWxUserByOpenId(openId);
    }

    /**
     * 后台根据标题查询所有微信用户
     * @param userName
     * @return
     */
    @Override
    public List<User> queryUserByName(String userName){
        return userDao.queryUserByName(userName);
    }

    /**
     * 根据用户名查询对应的后台用户
     * @param userName
     * @return
     */
    @Override
    public List<User> getUserListByUsername(String userName){
        return userDao.getUserListByUsername(userName);
    }

    @Override
    public String getCommentPraise(String openid){
        String commentPraiseId = userDao.getCommentPraise(openid);
        return commentPraiseId;
    }

    @Override
    @Transactional
    public boolean updateCommentPraise(WxUser wxUser) {
        if(wxUser.getOpenid()!=null){
            int row = userDao.updateCommentPraise(wxUser);
            if(row>0){
                return true;
            }else {
                throw new RuntimeException("更新区域信息失败！");
            }
        }else{
            throw new RuntimeException("区域id不能为空！");
        }
    }
}