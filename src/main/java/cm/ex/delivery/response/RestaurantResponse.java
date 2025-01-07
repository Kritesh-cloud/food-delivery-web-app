package cm.ex.delivery.response;

import cm.ex.delivery.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {

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

    private User owner;

    private List<String> categoryList;

    private List<String> imageGalleryList;

    private List<MenuCategoryResponse> menuCategoryResponses;

}
