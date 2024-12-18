package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String address;

    private String contactNumber;

    private String email;

    private String iconUrl;

    private String backgroundUrl;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User ownerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurant__category",
            joinColumns = @JoinColumn(name = "restaurant_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "category_id", updatable = true))
    private Set<Category> categorySet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurant__image__gallery",
            joinColumns = @JoinColumn(name = "restaurant_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "image_id", updatable = true))
    private Set<Image> imageGallerySet;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
