package com.matdongsan.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MemberSignUpDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$")
    private String username;

    private String password;

    @NotBlank
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;

}
