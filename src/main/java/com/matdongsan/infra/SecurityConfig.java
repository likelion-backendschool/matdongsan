package com.matdongsan.infra;

import com.matdongsan.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/login", "/signup", "/user", "/").permitAll()
                    // 누구나 접근 허용하는 페이지
                    .antMatchers("/admin").hasRole("ADMIN")
                    // ADMIN 권한이 있는 사람만 이용 가능한 페이지
                    .anyRequest().authenticated()
                    // 나머지 요청은 모두 권한(로그인)이 있어야지 접근 가능
                .and()
                    .formLogin()
                    .loginPage("/login")
                    // 로그인 페이지 링크
                    .defaultSuccessUrl("/")
                    // 로그인 완료 시 리다이렉트 주소
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    // 로그아웃 성공 시 리다이렉트 주소
                    .invalidateHttpSession(true);
                    // 로그아웃 후 세션 날리기
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                // MemberService implements UserDetailsService -> loadUserByUsername() 구현
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
