package com.matdongsan.web.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ProfileWithdrawalDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
