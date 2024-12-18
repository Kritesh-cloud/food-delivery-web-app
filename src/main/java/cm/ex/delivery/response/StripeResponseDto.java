package cm.ex.delivery.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeResponseDto {

    private String status;

    private String message;

    private String sessionId;

    private String sessionUrl;
}