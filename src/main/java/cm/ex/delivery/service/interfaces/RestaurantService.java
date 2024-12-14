package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.entity.restaurant.UpdateRestaurant;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {

    public BasicResponse addRestaurant(Restaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery);

    public Restaurant getRestaurantByOwnerId(User ownerId);

    public Restaurant getRestaurantById(String id);

    public List<Restaurant> listAllRestaurant();

    public BasicResponse updateRestaurant(UpdateRestaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery);

    public BasicResponse removeRestaurant();
}
