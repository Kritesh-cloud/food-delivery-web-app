package cm.ex.delivery.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicResponse {

    private boolean status;
    private boolean result;
    private int code;
    private String token;
    private String message;

    public BasicResponse(String message) {
        this.message = message;
    }
}
