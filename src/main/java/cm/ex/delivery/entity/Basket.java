package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "basket")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "basket__item",
            joinColumns = @JoinColumn(name = "basket_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "item_quantity_id", updatable = true))
    private Set<ItemQuantity> itemQuantitySet = new HashSet<>();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User ownerId;

    public Basket(Set<ItemQuantity> itemQuantitySet, User ownerId) {
        this.itemQuantitySet = itemQuantitySet;
        this.ownerId = ownerId;
    }

    public Basket(User ownerId) {
        this.ownerId = ownerId;
    }
}
