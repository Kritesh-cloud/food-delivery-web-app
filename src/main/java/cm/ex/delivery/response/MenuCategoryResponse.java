package cm.ex.delivery.response;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategoryResponse {

    private Long id;

    private int categoryOrder;

    private String name;

    private String restaurantId;
}
