package com.fuuzii.security;

import com.vteba.security.spi.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
        super.configure(http);
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
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
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

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/common/platform");
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/employee/login?authError=true");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
