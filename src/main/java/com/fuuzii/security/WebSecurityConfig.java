package com.fuuzii.security;

import com.vteba.cache.redis.SpringSecurityUserCache;
import com.vteba.security.filter.DefaultUserAuthenticationFilter;
import com.vteba.security.filter.logout.SecurityContextCacheLogoutHandler;
import com.vteba.security.filter.logout.UserLogoutFilter;
import com.vteba.security.interceptor.FilterSecurityInterceptorImpl;
import com.vteba.security.manager.AccessDecisionManagerImpl;
import com.vteba.security.meta.FilterInvocationSecurityMetadataSourceImpl;
import com.vteba.security.provider.DefaultAuthenticationProvider;
import com.vteba.security.spi.AuthoritiesService;
import com.vteba.security.spi.UserDetailsService;
import com.vteba.security.spi.cache.SecurityUserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yinlei
 * @since 2017/11/9 17:03
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userServiceImpl;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/*.jpg").permitAll()
                .antMatchers("/*.png").permitAll()
                .antMatchers("/*.jpeg").permitAll()
                .antMatchers("/*.gif").permitAll()
                .antMatchers("/*.bmp").permitAll()
                .antMatchers("/*.ico").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/*.map").permitAll()
                .antMatchers("/*.svg").permitAll()
                .antMatchers("/*.ttf").permitAll()
                .antMatchers("/*.otf").permitAll()
                .antMatchers("/*.eot").permitAll()
                .antMatchers("/*.woff").permitAll()
                .antMatchers("/*.woff2").permitAll()
                .antMatchers("/employee/login").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/index.jsp").permitAll()
                .antMatchers("/user/list").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/employee/login")
//                .successHandler(loginSuccessHandler())
//                .failureHandler(loginFailureHandler())
                .failureForwardUrl("/employee/login")
                .and()
                .logout()
                .logoutSuccessUrl("/employee/login")
                .permitAll()
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl)
            .passwordEncoder(passwordEncoder());
    }

    /**
     * 用户登录认证过滤器。
     *
     * @return 登录过滤器
     */
    @Bean
    public DefaultUserAuthenticationFilter userAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                    AuthenticationSuccessHandler successHandler,
                                                                    AuthenticationFailureHandler failureHandler,
                                                                    SessionAuthenticationStrategy sessionAuthenticationStrategy) {
        DefaultUserAuthenticationFilter authenticationFilter = new DefaultUserAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        authenticationFilter.setCaseSensitive(true);
        return authenticationFilter;
    }

    /**
     * 认证（登录）成功处理器
     *
     * @return 登录成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/common/platform");
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }

    /**
     * 认证（登录）失败处理器
     *
     * @return 登录失败处理器
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/employee/login?authError=true");
    }

    /**
     * 密码加密算法
     *
     * @return 加密实例
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    /**
     * 用户登出成功处理器，跳到登录页面
     *
     * @return 登出成功处理器
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
        handler.setDefaultTargetUrl("/employee/login");
        return handler;
    }

    /**
     * 用户登出成功，处理缓存中的用户信息
     *
     * @return 登出缓存处理器
     */
    @Bean
    @Autowired
    public SecurityContextCacheLogoutHandler cacheLogoutHandler(SecurityUserCache securityUserCache) {
        SecurityContextCacheLogoutHandler handler = new SecurityContextCacheLogoutHandler();
        handler.setSecurityUserCache(securityUserCache);
        return handler;
    }

    /**
     * 基于Redis的 Spring Security User Cache 实现
     *
     * @return 缓存实例
     */
    @Bean
    public SecurityUserCache securityUserCache() {
        return new SpringSecurityUserCache();
    }

    /**
     * 用户登出过滤器
     *
     * @param successHandler 登出成功处理器
     * @param securityContextCacheLogoutHandler 登出处理器，处理缓存中和 SecurityContext 中的用户信息
     * @return 过滤器实例
     */
    @Bean
    @Autowired
    public UserLogoutFilter userLogoutFilter(LogoutSuccessHandler successHandler, LogoutHandler securityContextCacheLogoutHandler) {
        return new UserLogoutFilter(successHandler, securityContextCacheLogoutHandler);
    }

    /**
     * Session 认证策略。采用并发 Session 认证策略，只能有一个在线
     *
     * @return Session 认证策略实例
     */
    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        List<SessionAuthenticationStrategy> strategyList = new ArrayList<>(1);
        strategyList.add(concurrentSessionControlAuthenticationStrategy);
        return new CompositeSessionAuthenticationStrategy(strategyList);
    }

    /**
     * 访问决策管理器
     *
     * @return 访问决策管理器
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new AccessDecisionManagerImpl();
    }

    /**
     * 基于DAO的认证提供者
     *
     * @param securityUserCache 用户缓存
     * @param passwordEncoder 密码加密器
     * @return 认证提供者实例
     */
    @Bean
    @Autowired
    public AuthenticationProvider authenticationProvider(SecurityUserCache securityUserCache, PasswordEncoder passwordEncoder) {
        DefaultAuthenticationProvider provider = new DefaultAuthenticationProvider();
        provider.setSecurityUserCache(securityUserCache);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }

    /**
     * 认证提供者管理器，[这个 Bean 的Id被Spring写死了("org.springframework.security.authenticationManager")] 已经改正了。
     *
     * @param authenticationProvider dao 认证提供者
     * @return 认证提供者管理器
     */
    @Bean
    @Autowired
    public ProviderManager providerManager(AuthenticationProvider authenticationProvider) {
        List<AuthenticationProvider> list = new ArrayList<>(1);
        list.add(authenticationProvider);
        return new ProviderManager(list);
    }

    /**
     * 过滤器调用元数据，初始化加载系统中的权限和资源
     *
     * @return 过滤器调用元数据
     */
    @Bean
    public FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource() {
        FilterInvocationSecurityMetadataSourceImpl invocationSecurityMetadataSource = new FilterInvocationSecurityMetadataSourceImpl((AuthoritiesService) userServiceImpl);
        invocationSecurityMetadataSource.setTenantSchema(true);
        List<String> whiteListTenantIds = new ArrayList<>();
        whiteListTenantIds.add("Default001");
        whiteListTenantIds.add("Default002");
        invocationSecurityMetadataSource.setWhiteListTenantIds(whiteListTenantIds);
        return invocationSecurityMetadataSource;
    }

    /**
     * Spring Security 核心拦截器
     *
     * @return 拦截器
     */
    @Bean
    @Autowired
    public FilterSecurityInterceptorImpl filterSecurityInterceptor(AccessDecisionManager accessDecisionManager,
                                                                   FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource,
                                                                   AuthenticationManager authenticationManager) {
        FilterSecurityInterceptorImpl interceptor = new FilterSecurityInterceptorImpl();
        interceptor.setAccessDecisionManager(accessDecisionManager);
        interceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
        interceptor.setAuthenticationManager(authenticationManager);
        interceptor.setCluster(true);
        interceptor.setRedirectUrl("/employee/login");

        Set<String> whiteList = new HashSet<>();
        whiteList.add("/employee/login");
        whiteList.add("/employee/login?authError=true");
        whiteList.add("/logout");
        whiteList.add("/common/platform");
        whiteList.add("/user/login");
        whiteList.add("/user/list");
        interceptor.setWhiteListUrl(whiteList);

        return interceptor;
    }
}
