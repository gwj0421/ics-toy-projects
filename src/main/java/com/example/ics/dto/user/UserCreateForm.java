package com.example.ics.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCreateForm {
    @Size(min = 2, max = 20)
    @NotBlank(message = "사용자 이름 필수")
    private String name;

    @NotBlank(message = "사용자 ID는 필수")
    private String userID;

    @NotBlank(message = "비밀번호는 필수")
    private String password1;

    @NotBlank(message = "비밀번호 체크는 필수")
    private String password2;

    @NotBlank(message = "이메일은 필수")
    @Email
    private String email;
}
