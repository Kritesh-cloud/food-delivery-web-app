package cm.ex.delivery.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShortRestaurantResponse {

    private UUID id;

    private String name;

    private String email;

    private boolean approved;

}
