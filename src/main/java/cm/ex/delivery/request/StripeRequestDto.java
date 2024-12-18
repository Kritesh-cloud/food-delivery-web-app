package cm.ex.delivery.request;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequestDto {

    private Long amount;

    private Long quantity;

    private String name;

    private String currency;
}