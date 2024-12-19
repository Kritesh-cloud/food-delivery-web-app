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

    @Column(name = "content_order")
    private Integer contentOrder;

    private String title;

    private String type;

    public BrowseContent(String title, String type) {
        this.title = title;
        this.type = type;
    }
}
