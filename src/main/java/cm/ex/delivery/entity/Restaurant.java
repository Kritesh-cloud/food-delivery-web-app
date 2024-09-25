package cm.ex.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "name")
    private String name;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "description")
    private String description;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "address")
    private String address;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "email")
    private String email;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "website")
    private String website;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "image_url")
    private String imageUrl;

//    @NotBlank(message="please enter restaurant data")
    @Column(name = "latitude")
    private int latitude;

    //    @NotBlank(message="please enter restaurant data")
    @Column(name = "longitude")
    private int longitude;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "city")
    private int city;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "cuisine_type")
    private String cuisineType;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "categories")
    private String categories;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "opening_time")
    private LocalTime openingTime;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "closing_time")
    private LocalTime closingTime;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "delivery_radius")
    private String deliveryRadius;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "delivery_fee")
    private String deliveryFee;

    @NotBlank(message="please enter restaurant data")
    @Column(name = "free_delivery_threshold")
    private String freeDeliveryThreshold;

    @Column(name = "is_active")
    private boolean isActive;

    @Transient
    private boolean isOpen = false;

    public void findIsOpen(){
        LocalTime currentTime = LocalTime.now();
        if (openingTime.isBefore(closingTime)) {
            // Case when start time is before end time (normal case)
            isOpen = currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime);
        } else {
            // Case when start time is after end time (e.g., overnight range)
            isOpen = currentTime.isAfter(openingTime) || currentTime.isBefore(closingTime);
        }
    }
}
