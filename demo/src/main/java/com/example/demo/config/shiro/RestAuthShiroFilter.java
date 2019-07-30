package com.example.demo.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 授权相关
 */
public class RestAuthShiroFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        Subject subject = getSubject(servletRequest, servletResponse);
            if(subject.getPrincipal() == null) {
                PrintWriter out = null;
                try{
                    out = httpResponse.getWriter();
                    out.append(JSON.toJSONString (401));
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return false;
            } else {
                return true;
            }
    }
}
