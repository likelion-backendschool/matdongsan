package com.matdongsan.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberLoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
