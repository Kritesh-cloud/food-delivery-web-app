package cm.ex.delivery.response;

import lombok.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponse {

    private UUID id;

    private Set<MenuItemResponse> menuItemResponses;

}
