package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
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
            inverseJoinColumns = @JoinColumn(name = "menu_item_id", updatable = true))
    private Set<MenuItem> menuItemSet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User ownerId;

    public Basket(Set<MenuItem> menuItemSet, User ownerId) {
        this.menuItemSet = menuItemSet;
        this.ownerId = ownerId;
    }
}
