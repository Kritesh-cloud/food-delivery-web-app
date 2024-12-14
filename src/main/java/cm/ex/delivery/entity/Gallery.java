//package cm.ex.delivery.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.util.UUID;
//
//@Entity
//@Table(name = "gallery")
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class Gallery {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id", referencedColumnName = "id")
//    private Image imageId;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
//    private Restaurant restaurantId;
//
//    public Gallery(Image imageId, Restaurant restaurantId) {
//        this.imageId = imageId;
//        this.restaurantId = restaurantId;
//    }
//
//    public Gallery(Image imageId) {
//        this.imageId = imageId;
//    }
//}
