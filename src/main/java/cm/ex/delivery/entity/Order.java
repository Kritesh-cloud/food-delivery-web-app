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

    private LocalDateTime createdAt;

    private LocalDateTime purchasedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basketId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User ownerId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
