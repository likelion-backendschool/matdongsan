package com.matdongsan.domain.member;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    // 로그인 아이디
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    private LocalDateTime signUpDate;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부를 반환하는 메소드
        return true;
        // true -> 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금 되었는지 확인하는 로직
        return true;
        // true -> 잠금되지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호가 만료되었는지 확인하는 로직
        return true;
        // true -> 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true;
        // true -> 사용 가능
    }
}
