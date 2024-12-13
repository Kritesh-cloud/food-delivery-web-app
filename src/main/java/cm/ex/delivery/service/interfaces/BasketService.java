package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface BasketService {

    public BasicResponse createBasket();

    public List<Basket> listUserBasket();

    public BasicResponse addItemToBasket(String itemId);

    public BasicResponse updateItemQuantityOfBasket(String itemId, int quantity);

    public BasicResponse removeItemFromBasket(String itemId);
}
