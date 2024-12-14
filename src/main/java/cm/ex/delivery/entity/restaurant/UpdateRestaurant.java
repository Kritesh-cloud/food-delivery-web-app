package cm.ex.delivery.entity.restaurant;

import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRestaurant {

    private UUID id;
    private String name;
    private String description;
    private String address;
    private String contactNumber;
    private String email;
    private boolean iconChanged;
    private boolean backgroundChanged;
    private boolean newGalleryImageAdded;
    private List<String> removeGalleryImageIds;
    private LocalTime openingTime;
    private LocalTime closingTime;

}
