package com.example.common.config;
import com.example.entity.Account;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 这个Java类是一个Spring MVC的拦截器，它的主要作用是检查用户的登录状态。
 * 如果用户未登录（即在session中找不到用户信息），它会将请求重定向到登录页面；
 * 如果用户已登录，请求会继续被处理。
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
          // 从HTTP请求的session中获取名为"user"的属性值，并将其转换为Account类型。该值为用户登录后存储的用户信息。  
          Account user = (Account) request.getSession().getAttribute("user");  
          // 如果从session中获取的user对象为null，说明用户未登录。  
          if (user == null) {  
              // 如果用户未登录，则通过response对象将请求重定向到登录页面。  
              response.sendRedirect("/end/page/login.html");  
              // 返回false，表示请求已被拦截，不再继续处理。  
              return false;  
          }  
          // 如果用户已登录，返回true，表示请求可以继续被处理。  
          return true;  
    }  
}