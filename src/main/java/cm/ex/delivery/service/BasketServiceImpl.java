package cm.ex.delivery.service;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.ItemQuantity;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.BasketRepository;
import cm.ex.delivery.repository.ItemQuantityRepository;
import cm.ex.delivery.repository.MenuItemRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BasketResponse;
import cm.ex.delivery.response.MenuItemResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.BasketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @Autowired
    private ItemQuantityRepository itemQuantityRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public BasketResponse userBasket() {
        Basket basket = getUserBasket();

        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setId(basket.getId());
        basketResponse.setMenuItemResponses(
                basket.getItemQuantitySet().stream().map(
                        itemQuantity -> {
                            MenuItemResponse menuItemResponse = modelMapper.map(itemQuantity.getMenuItem(), MenuItemResponse.class);
                            menuItemResponse.setMenuCategoryId(String.valueOf(itemQuantity.getMenuItem().getMenuCategory().getId()));
                            menuItemResponse.setQuantity(itemQuantity.getQuantity());
                            return menuItemResponse;
                        }
                ).collect(Collectors.toSet())
        );
        return basketResponse;
    }

    @Override
    public BasicResponse addItemToBasket(String itemId) {
        Basket basket = getUserBasket();
        Optional<MenuItem> menuItem = menuItemRepository.findById(Long.valueOf(itemId));
        if (menuItem.isEmpty()) throw new NoSuchElementException("Menu item not found");

        boolean itemExists = false;
        Set<ItemQuantity> itemQuantitySet = new HashSet<>(basket.getItemQuantitySet());
        for (ItemQuantity iq : itemQuantitySet) {
            if (iq.getMenuItem().equals(menuItem.get())) {
                iq.setQuantity(iq.getQuantity() + 1);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            ItemQuantity itemQuantity = itemQuantityRepository.save(new ItemQuantity(1, menuItem.get()));
            itemQuantitySet.add(itemQuantity);
        }


        basket.setItemQuantitySet(itemQuantitySet);
        basketRepository.save(basket);
        return BasicResponse.builder().status(true).code(200).message("New item added to the basket").build();
    }

    @Override
    public BasicResponse updateItemQuantityOfBasket(String itemId, int quantity) {

        if (quantity < 0 || quantity > 1000)
            return BasicResponse.builder().status(true).code(400).message("Bad request. Invalid item quantity").build();
        if (quantity == 0) return removeItemFromBasket(itemId);

        Basket basket = getUserBasket();
        MenuItem menuItem = menuItemService.getById(itemId);

        Set<ItemQuantity> itemQuantitySet = new HashSet<>(basket.getItemQuantitySet());
        for (ItemQuantity iq : itemQuantitySet) {
            if (iq.getMenuItem().equals(menuItem)) {
                iq.setQuantity(quantity);
                break;
            }
        }
        basket.setItemQuantitySet(itemQuantitySet);
        basketRepository.save(basket);
        return BasicResponse.builder().status(true).code(200).message("Item quantity updated in the basket").build();
    }

    @Override
    public BasicResponse removeItemFromBasket(String itemId) {
        Basket basket = getUserBasket();
        MenuItem menuItem = menuItemService.getById(itemId);

        Set<ItemQuantity> itemQuantitySet = new HashSet<>(basket.getItemQuantitySet());
        ItemQuantity removeItemQuantity = null;
        for (ItemQuantity iq : itemQuantitySet) {
            if (iq.getMenuItem().equals(menuItem)) {
                itemQuantitySet.remove(iq);
                removeItemQuantity = iq;
                break;
            }
        }

        if (removeItemQuantity != null) {
            basket.setItemQuantitySet(itemQuantitySet);
            basketRepository.save(basket);
            itemQuantityRepository.delete(removeItemQuantity);
        }

        return BasicResponse.builder().status(true).code(200).message("Item removed from the basket").build();
    }

    @Override
    public BasicResponse emptyUserBasket(){
        Basket basket = getUserBasket();
        basket.setItemQuantitySet(new HashSet<>());
        basketRepository.save(basket);
        return BasicResponse.builder().status(true).code(200).message("All Items removed from the basket").build();
    }

    @Override
    public BasicResponse removeBasket(User ownerId) {
        Basket basket = getUserBasket();
        basketRepository.delete(basket);
        return BasicResponse.builder().status(true).code(200).message("Basket removed succesfully").build();
    }

    public Basket getUserBasket() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        Optional<Basket> basket = basketRepository.findByOwnerId(userAuth.getUser());
        if (basket.isPresent()) return basket.get();
        return basketRepository.save(new Basket(userAuth.getUser()));
    }
}
