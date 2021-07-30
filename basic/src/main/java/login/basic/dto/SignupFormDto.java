package login.basic.dto;

import lombok.Data;

@Data
public class SignupFormDto {
    private String email;
    private String password;
    private String name;
}
