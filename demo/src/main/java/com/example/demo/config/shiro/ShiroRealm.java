package com.example.demo.config.shiro;

import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import com.example.demo.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm.
 */
public class ShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    LoginService loginService;

    //做授权的抽象方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        Session session = SecurityUtils.getSubject().getSession();
////        //查询用户的权限
////        JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
////        logger.info("permission的值为:" + permission);
////        logger.info("本用户权限为:" + permission.get("permissionList"));
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
        return authorizationInfo;
    }

    //做认证的抽象方法 参数 1.主体传过来的认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从主体传过来的认证信息中，获取用户名
        String userName=token.getUsername();
        System.out.println("userName"+userName);
        //通过用户名到数据库中获取用户
        User user = loginService.getUser(userName);
        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                //ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
                user.getName()
        );
        //authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));
        //设置用户session,将用户信息放入session中
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Constants.SESSION_USER_INFO, user);
        return authenticationInfo;
    }
}
