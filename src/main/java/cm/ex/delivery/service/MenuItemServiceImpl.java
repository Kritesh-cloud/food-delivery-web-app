package cm.ex.delivery.service;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.repository.MenuCategoryRepository;
import cm.ex.delivery.repository.MenuItemRepository;
import cm.ex.delivery.repository.RestaurantRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public BasicResponse addMenuItem(String menuItem, String menuCategoryName, double price) {
        Optional<MenuCategory> menuCategory = menuCategoryRepository.findByNameAndRestaurant(menuCategoryName, getRestaurant());
        if (menuCategory.isEmpty()) throw new NoSuchElementException("Menu Category not found");

        List<MenuItem> menuItemList = menuItemRepository.findByMenuCategory(menuCategory.get());
        int itemOrder = menuItemList.isEmpty() ? 0 : menuItemList.size() + 1;
        menuItemRepository.save(new MenuItem(itemOrder, menuItem, price, menuCategory.get()));

        return BasicResponse.builder().status(true).code(200).message("Menu Item added successfully").build();
    }

    @Override
    public List<MenuItem> listMenuItemByOrder(String menuCategoryName) {
        Optional<MenuCategory> menuCategory = menuCategoryRepository.findByNameAndRestaurant(menuCategoryName, getRestaurant());
        if (menuCategory.isEmpty()) throw new NoSuchElementException("Menu Category not found");

        return menuItemRepository.findByMenuCategoryOrdered(menuCategory.get());
    }

    @Override
    public MenuItem getById(String id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById((long) Integer.parseInt(id));
        if (menuItem.isEmpty()) throw new NoSuchElementException("Menu Item not found");

        return menuItem.get();
    }

    @Override
    public BasicResponse updateOrder(int currentOrder, int newOrder) {
        return null;
    }

    @Override
    public BasicResponse removeByItemId(String id) throws AccessDeniedException {
        Optional<MenuItem> menuItem = menuItemRepository.findById((long) Integer.parseInt(id));
        if (menuItem.isEmpty()) throw new NoSuchElementException("Menu Item not found");

        List<MenuCategory> menuCategoryList = menuCategoryRepository.findByRestaurantId(getRestaurant());
        List<MenuItem> menuItemMainList = new ArrayList<>();
        for (MenuCategory menuCategory : menuCategoryList) {
            List<MenuItem> menuItemList = menuItemRepository.findByMenuCategoryOrdered(menuCategory);
            menuItemMainList.addAll(menuItemList);
        }

        if (!menuItemMainList.contains(menuItem.get()))
            throw new AccessDeniedException("Unauthorized for removing this menu item");

        menuItemRepository.delete(menuItem.get());
        return BasicResponse.builder().status(true).code(200).message("Menu item deleted successfully").build();
    }

    @Override
    public BasicResponse removeByMenuCategoryId(String id) throws AccessDeniedException {
        Optional<MenuCategory> menuCategory = menuCategoryRepository.findById((long) Integer.parseInt(id));
        if(menuCategory.isEmpty()) throw new NoSuchElementException("Menu Category not found");

        List<MenuCategory> menuCategoryList = menuCategoryRepository.findByRestaurantId(getRestaurant());
        List<MenuItem> menuItemMainList = new ArrayList<>();
        for (MenuCategory tempMenuCategory : menuCategoryList) {
            List<MenuItem> menuItemList = menuItemRepository.findByMenuCategoryOrdered(tempMenuCategory);
            menuItemMainList.addAll(menuItemList);
        }

        List<MenuItem> deletMenuItemList = menuItemRepository.findByMenuCategoryOrdered(menuCategory.get());
        if (!menuItemMainList.containsAll(deletMenuItemList)) throw new AccessDeniedException("Unauthorized for removing this menu item");

        menuItemRepository.deleteAllInBatch(deletMenuItemList);
        return BasicResponse.builder().status(true).code(200).message("Menu item of given category deleted successfully").build();
    }

    private Restaurant getRestaurant() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");
        return restaurant.get();
    }
}
