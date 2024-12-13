package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface RestaurantService {

    public BasicResponse addRestaurant(Restaurant restaurantInfo);

    public Restaurant getRestaurantByOwnerId(User ownerId);

    public Restaurant getRestaurantById(String id);

    public List<Restaurant> listAllRestaurant();

    public BasicResponse updateRestaurant(Restaurant restaurantInfo);

    public BasicResponse removeRestaurant();
}
