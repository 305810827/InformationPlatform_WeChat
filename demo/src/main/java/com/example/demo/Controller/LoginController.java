package com.example.demo.Controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.WxUser;
import com.example.demo.service.LoginService;
import com.example.demo.service.UserService;
import com.example.demo.util.constants.AppUtil;
import com.example.demo.util.CommonUtil;
import com.example.demo.util.HttpUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/login")
public class LoginController{

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    /**
     * 微信小程序用户登录
     */
    @RequestMapping(value = "/wxDoLogin", method = RequestMethod.GET)
    public Map<String, Object> addWxUserInfo(HttpServletResponse resp, HttpServletRequest req){

        System.out.println(resp + " --- " + req);

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/html;charset=UTF-8");
        String code = req.getParameter("code");
//        String nickName = req.getParameter("name");
//        String avatarUrl = req.getParameter("url");
//        String sex = req.getParameter("sex");

        System.out.println("code:" + code);

        String url = AppUtil.wxLoginUrl;
        String param = "appid=" + AppUtil.appId + "&secret=" + AppUtil.secret + "&js_code=" + code + "&grant_type=authorization_code";

        Map<String, Object> result = new HashMap<String, Object>();
        int error = 0;

        try {
            String ret = HttpUtil.post(url, param);
            System.out.println(ret);
            JSONObject obj = JSONObject.parseObject(ret);
            String openid = obj.getString("openid");
            String session_key = obj.getString("session_key");
            result.put("session_key", session_key);
            result.put("openid", openid);
            System.out.println("session_key:" + session_key);
            System.out.println(userService.getWxUserByOpenId(openid));
            if(userService.getWxUserByOpenId(openid) == null)
            {
                WxUser user = new WxUser();
                user.setOpenid(openid);
                userService.addWxUser(user);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            error = -1;
        }
        result.put("code", error);
        System.out.println(result);
        return result;
    }

    /**
     * web后台登录
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public JSONObject authLogin(@RequestParam("username")String username,@RequestParam("password")String password) {
        JSONObject data = loginService.authLogin(username,password);
        System.out.println(data);
        return data;
    }


//    @RequestMapping("/doLogin")
//    public String doLogin(
//            @RequestParam(value = "username", required = true) String userName,
//            @RequestParam(value = "password", required = true) String password,
//            Model model)
//    {
//        String passwordmd5 = new Md5Hash(password, "2").toString();
//        System.out.println(passwordmd5);
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(userName, passwordmd5);
//        System.out.println(token);
//        try {
//            subject.login(token);
//            System.out.println("login(token)");
//            return "redirect:/home";
//        }catch (UnknownAccountException e) {
//            model.addAttribute("error", "账号不存在!");
//            return "login";
//        }catch (IncorrectCredentialsException e) {
//            model.addAttribute("error","账号密码错误!");
//            return "login";
//        }catch (AuthenticationException e) {
//            model.addAttribute("error","登录异常!请联系管理员!");
//            return "login";
//        }catch (Exception e) {
//            model.addAttribute("error", "系统异常!");
//            return "login";
//        }
//    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public JSONObject logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }

}
