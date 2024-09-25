package cm.ex.delivery.entity.food;

import cm.ex.delivery.entity.Food;
import cm.ex.delivery.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_favourite")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;
}
