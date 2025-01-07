package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private double discount;

    @ManyToOne
    @JoinColumn(name = "menu_category_id") // Foreign key column
    private MenuCategory menuCategory;

    public MenuItem(String name, double price, MenuCategory menuCategory) {
        this.name = name;
        this.price = price;
        this.menuCategory = menuCategory;
    }

    public MenuItem(String name, double price, double discount, MenuCategory menuCategory) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.menuCategory = menuCategory;
    }
}
