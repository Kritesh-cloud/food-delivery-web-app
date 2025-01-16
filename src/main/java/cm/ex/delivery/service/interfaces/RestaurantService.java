package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.request.UpdateRestaurant;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.MediumRestaurantResponse;
import cm.ex.delivery.response.RestaurantResponse;
import cm.ex.delivery.response.ShortRestaurantResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {

    public BasicResponse addRestaurant(Restaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery);

    public Restaurant getRestaurantByOwnerId(User ownerId);

    public Restaurant getOwnerRestaurant();

    public Restaurant getRestaurantById(String id);

    public List<Restaurant> listAllRestaurant();

    public List<RestaurantResponse> listAllRestaurantDetails();

    public List<ShortRestaurantResponse> listAllShortRestaurantDetails();

    public RestaurantResponse getRestaurantDetailsById(String id);

    public List<RestaurantResponse> listAllRestaurantDetailsByBrowseList(String browseId);

    public List<ShortRestaurantResponse> listAllShortRestaurantDetailsByBrowseList(String browseId);

    public List<MediumRestaurantResponse> listAllMediumRestaurantDetailsByBrowseList(String browseId);

    public List<RestaurantResponse> reverseListAllRestaurantDetailsByBrowseList(String browseId);

    public List<MediumRestaurantResponse> reverseListAllMeduimRestaurantDetailsByBrowseList(String browseId);

    public BasicResponse updateRestaurant(UpdateRestaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery);

    public BasicResponse removeRestaurant();
}
