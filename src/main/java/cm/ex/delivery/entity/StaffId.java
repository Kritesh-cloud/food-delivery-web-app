package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "staff_id")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StaffId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public StaffId(String staffId, Restaurant restaurant) {
        this.staffId = staffId;
        this.restaurant = restaurant;
    }
}
