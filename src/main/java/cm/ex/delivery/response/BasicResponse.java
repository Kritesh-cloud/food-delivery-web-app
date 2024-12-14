package cm.ex.delivery.response;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NotNull
public class BasicResponse {

    private boolean status;
    private int code;
    private String token;
    private String message;
}
