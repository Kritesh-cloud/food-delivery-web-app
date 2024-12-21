package cm.ex.delivery.response;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {

    private Long id;

    private int quantity;

    private int itemOrder;

    private String name;

    private double price;

    private double discount;

    private String menuCategoryId;
}
