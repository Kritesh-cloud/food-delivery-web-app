package cm.ex.delivery.service;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.BasketRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @Override
    public Basket createBasket(User ownerId) {
        return basketRepository.save(new Basket(new HashSet<>(), ownerId));
    }

    @Override
    public List<Basket> listUserBasket() {
        List<Basket> basketList = basketRepository.findAll();
        return basketList.isEmpty() ? List.of() : basketList;
    }

    @Override
    public BasicResponse addItemToBasket(String itemId) {
        Basket basket = getUserBasket();

        MenuItem menuItem = menuItemService.getById(itemId);
        Set<MenuItem> menuItemSet = new HashSet<>(basket.getMenuItemSet());

        if (menuItemSet.contains(menuItem)) {
            menuItem.setQuantity(menuItem.getQuantity() + 1);
        }
        menuItem.setQuantity(1);
        menuItemSet.add(menuItem);

        basket.setMenuItemSet(menuItemSet);
        basketRepository.save(basket);
        return BasicResponse.builder().status(true).code(200).message("New item added to the basket").build();
    }

    @Override
    public BasicResponse updateItemQuantityOfBasket(String itemId, int quantity) {
        Basket basket = getUserBasket();

        MenuItem menuItem = menuItemService.getById(itemId);
        Set<MenuItem> menuItemSet = new HashSet<>(basket.getMenuItemSet());
        menuItemSet.remove(menuItem);

        menuItem.setQuantity(quantity);
        menuItemSet.add(menuItem);

        basket.setMenuItemSet(menuItemSet);
        basketRepository.save(basket);
        return BasicResponse.builder().status(true).code(200).message("Item quantity updated in the basket").build();
    }

    @Override
    public BasicResponse removeItemFromBasket(String itemId) {
        Basket basket = getUserBasket();

        MenuItem menuItem = menuItemService.getById(itemId);
        Set<MenuItem> menuItemSet = new HashSet<>(basket.getMenuItemSet());
        menuItemSet.remove(menuItem);
        basket.setMenuItemSet(menuItemSet);

        basketRepository.save(basket);
        return BasicResponse.builder().status(true).code(200).message("Item removed from the basket").build();
    }

    @Override
    public BasicResponse removeBasket(User ownerId){
        Basket basket = getUserBasket();
        basketRepository.delete(basket);
        return BasicResponse.builder().status(true).code(200).message("Basket removed succesfully").build();
    }

    public Basket getUserBasket() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Basket> basket = basketRepository.findByOwnerId(userAuth.getUser());
        if (basket.isEmpty()) throw new NoSuchElementException("User basket not found");

        return basket.get();
    }
}
