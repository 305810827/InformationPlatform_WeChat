package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.WxUser;

import java.util.List;

/**
 * @author: hxy
 * @description: 登录Service
 * @date: 2017/10/24 11:02
 */
public interface UserService {
    /**
     * 添加微信新用户
     * @param wxUser
     * @return
     */
    boolean addWxUser(WxUser wxUser);

    /**
     * 更新微信用户信息
     */
    boolean updateWxUser(WxUser wxUser);

    /**
     * 后台根据标题查询所有微信用户
     * @param userName
     * @return
     */
    List<User> queryUserByName(String userName);

    /**
     * 根据唯一标识openId查询微信用户
     * @param openId 唯一标识
     * @return
     */
    WxUser getWxUserByOpenId(String openId);

    /**
     * 根据用户名查询对应的后台用户
     */
    List<User> getUserListByUsername(String username);

    /**
     * 查询用户点赞过的评论
     * @param openid
     * @return
     */
    String getCommentPraise(String openid);

    /**
     * 更新用户点赞过的评论
     * @param wxUser
     * @return
     */
    boolean updateCommentPraise(WxUser wxUser);
}
