package com.matdongsan.infra;

import com.matdongsan.service.MemberService;
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
    private final MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/login", "/signup").permitAll() // 누구나 접근 가능
                .antMatchers("/manager/*").hasAnyRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated(); // 나머지 요청은 권한이 있어야함
        /*
        * 기존 formLogin 로직
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        */
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        // 인증 필요시 로그인 페이지와 로그인 성공시 리다이랙팅 경로 지정
        http.formLogin()
                .loginPage("/login")    // 로그인 페이지 링크
                .defaultSuccessUrl("/", true); // 로그인 성공 후 리다이렉트 주소

        // 로그인이 수행될 uri 매핑 (post 요청이 기본)
        /*http.formLogin()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true);*/

        http.exceptionHandling().accessDeniedPage("/");

        http.userDetailsService(memberService);

        http.rememberMe()
                .userDetailsService(memberService);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/dependencies/**", "/assets/**", "/media/**", "/php/**", "/sass/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
