package cm.ex.delivery.entity.food;

import cm.ex.delivery.entity.Food;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter discount data")
    @Column(name = "type")
    private String type; // normal, seasonal, offers, deals, promotional

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;

    @NotBlank(message="please enter discount data")
    @Column(name = "amount", columnDefinition = "INT DEFAULT 0")
    private int amount;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Food food;

}
