package cm.ex.delivery.dto.respone.authentication;

import cm.ex.delivery.dto.respone.basic.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends Response {

    private String token;

    public LoginResponse(boolean status, String message, String token) {
        super(status, message);
        this.token = token;
    }
}

