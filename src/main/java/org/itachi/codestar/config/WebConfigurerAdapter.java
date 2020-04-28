package org.itachi.codestar.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itachi.codestar.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 09:41
 * @author itachi
 */
@Configuration
public class WebConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includePatterns;
        List<String> excludePatterns;
        // 添加需要验证的路径正则表达式集合
        try {
            includePatterns = mapper.readValue(messageSource.getMessage("include.patterns",null, null, Locale.getDefault()), new TypeReference<List<String>>(){});
         } catch (Exception e) {
            includePatterns = new ArrayList<>();
        }
        // 添加需要忽略，不进入验证的正则表达式集合
        try {
            excludePatterns = mapper.readValue(messageSource.getMessage("exclude.patterns",null, null, Locale.getDefault()), new TypeReference<List<String>>(){});
        } catch (Exception e) {
            excludePatterns = new ArrayList<>();
        }
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns(includePatterns.toArray(new String[]{}))
                .excludePathPatterns(excludePatterns.toArray(new String[]{}));
    }
}

