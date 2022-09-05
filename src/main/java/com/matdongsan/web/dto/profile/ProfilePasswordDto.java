package com.matdongsan.web.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class ProfilePasswordDto {

    private String originalPassword;

    @NotBlank(message = "비밀번호에 공백을 포함할 수 없습니다!")
    @Size(min = 8, max = 20, message = "비밀번호 길이를 확인해주세요!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리 이상이며, 숫자, 문자, 특수문자 각각 1개 이상 포함해주세요.")
    private String newPassword;
}
