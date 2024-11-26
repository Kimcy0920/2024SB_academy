package edu.du.sb1101.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/notice/**", "/member/**", "/admin/**", "/board/**", "/survey/**", "/store/**") // 보호할 URL
                .excludePathPatterns("/", "/register", "/login", "/logout", "/public/**"); // 로그인 없이 접근 가능
    }

    @Bean
    public Filter cacheControlFilter() {
        return (request, response, chain) -> {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 비활성화
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        };
    }
}