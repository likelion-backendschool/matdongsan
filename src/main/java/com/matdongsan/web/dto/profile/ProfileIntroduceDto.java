package com.matdongsan.web.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class ProfileIntroduceDto {

    @NotBlank(message = "글을 입력해주세요.")
    @Size(min = 4, max = 25, message = "소개글은 4글자 이상 25글자 이내로 작성해주세요.")
    String introduce;
}
