package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "id_holder")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataId;

    private String dataType;

    @ManyToOne
    @JoinColumn(name = "browse_content_id", referencedColumnName = "id")
    private BrowseContent browseContentId;

    public IdHolder(String dataId, String dataType, BrowseContent browseContentId) {
        this.dataId = dataId;
        this.dataType = dataType;
        this.browseContentId = browseContentId;
    }
}
