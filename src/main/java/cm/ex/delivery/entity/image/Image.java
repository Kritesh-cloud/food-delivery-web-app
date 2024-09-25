package cm.ex.delivery.entity.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
