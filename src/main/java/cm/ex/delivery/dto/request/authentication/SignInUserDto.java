package cm.ex.delivery.dto.request.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserDto {

    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "input field cannot be blank")
    private String password;

}

