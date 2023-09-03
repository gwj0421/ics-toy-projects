package com.example.ics.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginForm {
    @NotBlank(message = "사용자 ID는 필수")
    private String userID;

    @NotBlank(message = "비밀번호는 필수")
    private String password;
}
