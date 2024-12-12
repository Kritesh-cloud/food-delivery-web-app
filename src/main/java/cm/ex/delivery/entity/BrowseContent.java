package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "browse_content")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrowseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int order;

    private String title;

    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "browse__content__id",
            joinColumns = @JoinColumn(name = "browse_content_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "id_holder_id", updatable = true))
    private Set<IdHolder> idHolderSet;

}
