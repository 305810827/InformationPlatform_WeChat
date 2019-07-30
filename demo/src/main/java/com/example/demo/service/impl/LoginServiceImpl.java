package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import com.example.demo.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hxy
 * @description: 登录service实现类
 * @date: 2017/10/24 11:53
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserDao userDao;

	/**
	 * 登录表单提交
	 */
	@Override
	public JSONObject authLogin(String username,String password) {
		JSONObject info = new JSONObject();
//
//		Md5Hash md5Hash = new Md5Hash(password,"Mack");
//		System.out.println(md5Hash);

		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			User user = userDao.getUserByUsername(username);
			//User user = (User) currentUser.getPrincipal();
			System.out.println("登陆成功"+currentUser.isAuthenticated());
			String imageUrl = user.getImageUrl();
			String name = user.getName();
			info.put("result", "success");
			info.put("imageUrl",imageUrl);
			info.put("name",name);
		} catch (UnknownAccountException e) {
			//用户名不存在
			info.put("code", "-1");
			info.put("msg", "用户名不存在");
			//logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
		} catch (IncorrectCredentialsException e) {
			//密码错误
			info.put("code", "-1");
			info.put("msg", "用户名或密码错误");
			//logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
		} catch (LockedAccountException e) {
			//账户被锁定
			info.put("code", "-1");
			info.put("msg", "账户被锁定");
			//logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

		} catch (ExcessiveAttemptsException e) {
			//登录失败次数超过系统最大次数,请稍后重试
			info.put("code", "-1");
			info.put("msg", "登录失败次数超过系统最大次数,请稍后重试!");
			//logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

		} catch (DisabledAccountException e) {
			//验证未通过,帐号已经禁止登录
			info.put("code", "-1");
			info.put("msg", "验证未通过,帐号已经禁止登录!");
			//logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());

		} catch (AuthenticationException e) {
			//出现其他异常
			info.put("code", "-1");
			info.put("msg", e.getMessage());
			//logger.error("userName:{},passWord:{} login fail,error info is:{}", userName, passWord, e.getMessage());
		}
		return CommonUtil.successJson(info);
	}

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public User getUser(String username) {
		return userDao.getUserByUsername(username);
	}

//	/**
//	 * 查询当前登录用户的权限等信息
//	 */
//	@Override
//	public JSONObject getInfo() {
//		//从session获取用户信息
//		Session session = SecurityUtils.getSubject().getSession();
//		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
//		String username = userInfo.getString("username");
//		JSONObject info = new JSONObject();
//		JSONObject userPermission = permissionService.getUserPermission(username);
//		session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
//		info.put("userPermission", userPermission);
//		return CommonUtil.successJson(info);
//	}

	/**
	 * 退出登录
	 */
	@Override
	public JSONObject logout() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
		} catch (Exception e) {
		}
		return CommonUtil.successJson();
	}
}
