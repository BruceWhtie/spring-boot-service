package ewing.security;

import ewing.application.ResultMessage;
import ewing.application.common.GsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring Security 全局配置。
 *
 * @author Ewing
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SUCCESS = GsonUtils.toJson(
            new ResultMessage<>());

    private static final String FAILURE = GsonUtils.toJson(
            new ResultMessage<>().toFailure());

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new SecurityUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        // 允许跨域访问
        security.csrf().disable().headers()
                .frameOptions().sameOrigin()
                // 所有请求都需要验证
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 定义登录页面并允许所有人访问
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .permitAll()
                // 登出页面允许所有人访问
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            sendRedirectOrString(request, response, "/login.html", SUCCESS);
        };
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            sendRedirectOrString(request, response, "/index.html", SUCCESS);
        };
    }

    private AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, authentication) -> {
            sendRedirectOrString(request, response, "/login.html", FAILURE);
        };
    }

    private void sendRedirectOrString(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String url, String string) {
        response.setCharacterEncoding("UTF-8");
        String accept = request.getHeader("Accept");
        try {
            if (accept.toLowerCase().contains("text/html")) {
                response.sendRedirect(url);
            } else {
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.print(string);
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}