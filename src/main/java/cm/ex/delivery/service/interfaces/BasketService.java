package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BasketResponse;

import java.util.List;

public interface BasketService {

    public Basket createBasket(User ownerId);

    public List<Basket> listUserBasket();

    public BasketResponse userBasket();

    public BasicResponse addItemToBasket(String itemId);

    public BasicResponse updateItemQuantityOfBasket(String itemId, int quantity);

    public BasicResponse removeItemFromBasket(String itemId);

    public BasicResponse emptyUserBasket();

    public BasicResponse removeBasket(User ownerId);
}
