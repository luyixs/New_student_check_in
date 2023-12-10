package com.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 自定义拦截器
 * 这个配置类的作用是将自定义的拦截器MyInterceptor注册到Spring MVC框架中，并指定了应该被拦截或排除的路径。
 * 这意味着，当请求匹配到"/end/page/**"这个路径模式时（除了登录页和注册页），它会被MyInterceptor处理。
 * MyInterceptor会检查用户是否已登录。
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个新的拦截器实例MyInterceptor到拦截器注册表中。  
        // 实现WebMvcConfigurer不会导致静态资源被拦截，这意味着静态资源（如CSS、JS、图片等）的请求不会被这个拦截器处理。  
        registry.addInterceptor(new MyInterceptor())
                // 通过addPathPatterns方法，指定哪些URL路径应该被这个拦截器处理。"/end/page/**"表示处理所有以"/end/page/"开头的路径。
                .addPathPatterns("/end/page/**")
                 // 通过excludePathPatterns方法，指定哪些URL路径应该被排除，不被这个拦截器处理。这里排除了登录页和注册页的路径。  
                .excludePathPatterns("/end/page/login.html", "/end/page/register.html");
    }
}
