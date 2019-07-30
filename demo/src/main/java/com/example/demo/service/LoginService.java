package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;

/**
 * @author: hxy
 * @description: 登录Service
 * @date: 2017/10/24 11:02
 */
public interface LoginService {
	/**
	 * 登录表单提交
	 */
	JSONObject authLogin(String username,String password);

	/**
	 * 根据用户名查询对应的用户
	 *
	 * @param username 用户名
	 */
	User getUser(String username);

//	/**
//	 * 查询当前登录用户的权限等信息
//	 */
//	JSONObject getInfo();

	/**
	 * 退出登录
	 */
	JSONObject logout();
}
