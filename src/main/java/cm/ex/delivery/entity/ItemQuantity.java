package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "item_quantity")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int quantity;

//    private String menuItemId;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id") // Fixed FK name
    private MenuItem menuItem;

    public ItemQuantity(int quantity, MenuItem menuItem) {
        this.quantity = quantity;
        this.menuItem = menuItem;
    }
}
