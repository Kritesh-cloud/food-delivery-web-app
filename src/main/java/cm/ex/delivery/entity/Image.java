package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "image")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    private String extension;

    private LocalDateTime createdAt;

    public Image(String name, String image, String extension) {
        this.name = name;
        this.image = image;
        this.extension = extension;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
