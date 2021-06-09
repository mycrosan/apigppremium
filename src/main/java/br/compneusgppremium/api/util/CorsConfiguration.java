//package br.compneusgppremium.api.util;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//@EnableWebMvc
//class WebConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //liberando app cliente 1
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
//
//        //liberando app cliente 2
//        registry.addMapping("/pneu/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "OPTIONS", "HEAD", "TRACE", "CONNECT");
//    }
//}
