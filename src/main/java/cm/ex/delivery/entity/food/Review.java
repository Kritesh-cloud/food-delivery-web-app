package cm.ex.delivery.entity.food;

import cm.ex.delivery.entity.Food;
import cm.ex.delivery.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter review data")
    @Column(name = "review")
    private String review;

    @NotBlank(message="please enter review data")
    @Column(name = "priority", columnDefinition = "INT DEFAULT 4")//1-5, 1 highest, 5 lowest, 4 default
    private int priority;

    @Column(name = "hidden", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean hidden;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;
}
