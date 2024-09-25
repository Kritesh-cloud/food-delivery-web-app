package cm.ex.delivery.entity;

import cm.ex.delivery.entity.food.Category;
import cm.ex.delivery.entity.food.Tags;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter food data")
    @Column(name = "name")
    private String name;

    @NotBlank(message="please enter food data")
    @Column(name = "description")
    private String description;

    @NotBlank(message="please enter food data")
    @Column(name = "price")
    private double price;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "imageUrls")
    private String[] imageUrls;

    @NotBlank(message="please enter food data")
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @NotBlank(message="please enter food data")
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @NotBlank(message="please enter food data")
    @Column(name = "calories")
    private String calories;

    @NotBlank(message="please enter food data")
    @Column(name = "fat")
    private String fat;

    @NotBlank(message="please enter food data")
    @Column(name = "protein")
    private String protein;

    @NotBlank(message="please enter food data")
    @Column(name = "carbs")
    private String carbs;

    @NotBlank(message="please enter food data")
    @Column(name = "is_available")
    private boolean isAvailable;

    @NotBlank(message="please enter food data")
    @Column(name = "prep_time")
    private int prepTime;

    @NotBlank(message="please enter food data")
    @Column(name = "serving_size")
    private String serving_size;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Category cuisine;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "food_tag_list",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tags> tags;
}