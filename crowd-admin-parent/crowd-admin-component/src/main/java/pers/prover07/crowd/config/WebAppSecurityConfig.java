package pers.prover07.crowd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import pers.prover.crowd.constant.HttpRespMsgConstant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by Prover07
 * @classname WebAppSecurityApplication
 * @description TODO
 * @date 2022/2/19 15:02
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置用户认证
     * @param builder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置资源授权
     *
     * @param security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        // 配置 URL 权限
        security.authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/**/*.css", "/**/*.js", "/**/fonts/**", "/**/img/**")
                .permitAll()
                .antMatchers("/admin/page")
                .hasRole("经理")
                .anyRequest()
                .authenticated();

        // 配置表单登录
        security.formLogin()
                .loginPage("/auth/login/page")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/main", true);

        // 关闭 CSRF
        security.csrf().disable();

        // 配置注销
        security.logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login/page");

        // 配置403权限异常处理
        security
                .exceptionHandling()
                .accessDeniedHandler((request, response, exception) -> {
                    request.setAttribute("exception", new Exception(HttpRespMsgConstant.ACCESS_DENIED));
                    request.getRequestDispatcher("/WEB-INF/system/error.jsp").forward(request, response);
                });
    }
}
