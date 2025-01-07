package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status; //Pending, accepted-preparing, prepared-delivering and delivering OR Declined.

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime purchasedAt;

    @ManyToOne
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basketId;

    @ManyToOne
    @JoinColumn(name = "restaurant", referencedColumnName = "id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "buyer", referencedColumnName = "id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "delivery_person", referencedColumnName = "id")
    private User deliveryPerson;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        deliveryPerson = null;
    }

    public Order(String status, boolean active, Basket basketId) {
        this.status = status;
        this.active = active;
        this.basketId = basketId;
    }
}
