package cm.ex.delivery.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MediumRestaurantResponse {

    private UUID id;

    private String name;

    private String email;

    private boolean approved;

    private String address;

    private String contactNumber;

    private String iconUrl;

    private String backgroundUrl;
}
