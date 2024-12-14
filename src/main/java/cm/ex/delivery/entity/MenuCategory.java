package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "menu_category")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int categoryOrder;

    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurantId;

    public MenuCategory(int categoryOrder, String name, Restaurant restaurantId) {
        this.categoryOrder = categoryOrder;
        this.name = name;
        this.restaurantId = restaurantId;
    }
}
