package com.fuuzii.config;

import com.vteba.web.filter.DefaultContextFilter;
import com.vteba.web.filter.XssDefenderFilter;
import com.vteba.web.listener.SessionTimeoutListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpSessionListener;

/**
 * Servlet 相关的组件的配置。Servlet，Filter，Listener 等。
 *
 * @author yinlei
 * @since 2017/11/10 13:52
 */
@Configuration
public class ServletComponentConfig {

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionTimeoutListener() {
        SessionTimeoutListener listener = new SessionTimeoutListener();
        return new ServletListenerRegistrationBean<>(listener);
    }

    @Bean
    public FilterRegistrationBean xssFilter() {
        XssDefenderFilter xssDefenderFilter = new XssDefenderFilter();
        FilterRegistrationBean filter = new FilterRegistrationBean(xssDefenderFilter);
        filter.addUrlPatterns("/*");
        return filter;
    }

    @Bean
    public FilterRegistrationBean contextFilter() {
        DefaultContextFilter contextFilter = new DefaultContextFilter();
        FilterRegistrationBean filter = new FilterRegistrationBean(contextFilter);
        filter.addUrlPatterns("/*");
        return filter;
    }
}
