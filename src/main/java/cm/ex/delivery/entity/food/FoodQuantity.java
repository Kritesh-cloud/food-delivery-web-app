package cm.ex.delivery.entity.food;

import cm.ex.delivery.entity.Food;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_quantity")
public class FoodQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "quantity", columnDefinition = "INT DEFAULT 1")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;

}
