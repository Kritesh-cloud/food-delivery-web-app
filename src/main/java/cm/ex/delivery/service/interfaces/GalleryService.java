package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Gallery;
import cm.ex.delivery.entity.Image;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface GalleryService {

    public BasicResponse addToGallery(Image imageId, Restaurant restaurantId, User userId);

    public List<Gallery> listGalleryByRestaurantId(Restaurant restaurantId);

    public List<Gallery> listGalleryByUserId(User userId);

    public List<Gallery> listAllGallery();

    public  BasicResponse removeFromGalleryByRestaurantId(Restaurant restaurantId);

    public  BasicResponse removeFromGallery(String galleryId);

}
