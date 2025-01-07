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

    private String openingTime;

    private String closingTime;

    private boolean approved;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User ownerId;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "restaurant__staff__user",
//            joinColumns = @JoinColumn(name = "restaurant_id", updatable = true),
//            inverseJoinColumns = @JoinColumn(name = "staff_id", updatable = true))
//    private Set<User> staffSet;

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

    public Restaurant(String name, String description, String address, String contactNumber, String email, String backgroundUrl, String openingTime, String closingTime, String iconUrl, Set<Category> categorySet, User ownerId, Set<Image> imageGallerySet) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.backgroundUrl = backgroundUrl;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.iconUrl = iconUrl;
        this.categorySet = categorySet;
        this.ownerId = ownerId;
//        this.staffSet = staffSet;
        this.imageGallerySet = imageGallerySet;
    }



}
