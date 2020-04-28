//package org.itachi.codestar.controller;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.dianping.cat.servlet.CatFilter;
//
///**
// * Created by kyo on 2019/2/1.
// */
//@Configuration
//public class CatFilterConfigure {
//
//    @Bean
//    public FilterRegistrationBean catFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        CatFilter filter = new CatFilter();
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
//        registration.setName("cat-filter");
//        registration.setOrder(1);
//        return registration;
//    }
//
//}