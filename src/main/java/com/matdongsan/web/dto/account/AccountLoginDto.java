package com.matdongsan.web.dto.account;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class AccountLoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
