package com.ssafyebs.customerback.global.config;



import com.ssafyebs.customerback.global.jwt.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/members/sign-up")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/members/login")
                .excludePathPatterns("/api/members/email/**")
                .excludePathPatterns("/api/members/google-sign-up");


    }
}