package com.matdongsan.infra;

import com.matdongsan.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/login", "/signup", "/account/idCheck", "/account/kakao/**").permitAll() // 누구나 접근 가능
                .antMatchers("/manager/*").hasAnyRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated(); // 나머지 요청은 권한이 있어야함
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        // 인증 필요시 로그인 페이지와 로그인 성공시 리다이랙팅 경로 지정
        http.formLogin()
                .loginPage("/login")    // 로그인 페이지 링크
                .defaultSuccessUrl("/", true); // 로그인 성공 후 리다이렉트 주소

        http.exceptionHandling().accessDeniedPage("/");

        http.userDetailsService(accountService);

        http.rememberMe()
                .rememberMeParameter("remember-me")
                .userDetailsService(accountService);

        http.exceptionHandling()
                .accessDeniedPage("/error-page");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/dependencies/**", "/assets/**", "/media/**", "/php/**", "/sass/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
