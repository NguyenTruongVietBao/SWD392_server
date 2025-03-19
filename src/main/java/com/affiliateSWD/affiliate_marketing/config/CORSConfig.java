package com.affiliateSWD.affiliate_marketing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowedMethods("*")
//                .maxAge(1440000);
//    }
//@Override
//public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")
//            .allowedOrigins("*")  // Cấu hình để chỉ cho phép từ localhost:5173
//            .allowedHeaders("*")
//            .exposedHeaders("*")
//            .allowedMethods("GET", "POST", "PUT", "DELETE")  // Cấu hình các phương thức HTTP mà bạn muốn cho phép
//            .allowCredentials(true)  // Cho phép gửi cookies hoặc authorization headers
//            .maxAge(1440000);  // Thời gian cache cho CORS preflight request
//}
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS policy to all endpoints
//                .allowedOrigins("http://localhost:5173", "https://vercel.com/nt-vbaos-projects/swd-392-client/2VvnWZmhVWiJzbN6R6SP8nr9aHkP") // Allowed origins
                .allowedOrigins("*") // Allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP methods
                .allowedHeaders("Authorization", "Content-Type") // Allowed headers
                .allowCredentials(true); // Allow credentials (cookies, Authorization headers, etc.)
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
