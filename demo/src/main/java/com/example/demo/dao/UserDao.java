package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    /**
     * 根据用户名和密码查询对应的后台用户
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 根据用户名所有后台用户
     */
    List<User> getUserListByUsername(String username);

    /**
     * 后台根据标题查询所有微信用户
     * @param userName
     * @return
     */
    List<User> queryUserByName(String userName);

    /**
     * 添加新的微信用户信息
     */
    int addWxUser(WxUser wxUser);

    /**
     * 更新微信用户信息
     */
    int updateWxUser(WxUser wxUser);

    /**
     * 根据openId查询微信用户信息
     */
    WxUser getWxUserByOpenId(String openId);

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
    int updateCommentPraise(WxUser wxUser);
}
