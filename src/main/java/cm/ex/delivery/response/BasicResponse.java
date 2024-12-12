package cm.ex.delivery.response;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {

    private boolean status;
    private String message;
}
