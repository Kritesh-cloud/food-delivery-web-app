package cm.ex.delivery.service;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.repository.MenuCategoryRepository;
import cm.ex.delivery.repository.MenuItemRepository;
import cm.ex.delivery.repository.RestaurantRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.MenuItemResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.MenuItemService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BasicResponse addMenuItem(String menuItem, String menuCategoryId, double price) {
        Optional<MenuCategory> menuCategory = menuCategoryRepository.findByIdAndRestaurant(menuCategoryId, getRestaurant());
        if (menuCategory.isEmpty()) throw new NoSuchElementException("Menu Category not found");

        List<MenuItem> menuItemList = menuItemRepository.findByMenuCategory(menuCategory.get());
        int itemOrder = menuItemList.isEmpty() ? 0 : menuItemList.size() + 1;
        menuItemRepository.save(new MenuItem( menuItem, price, menuCategory.get()));

        return BasicResponse.builder().status(true).code(200).message("Menu Item added successfully").build();
    }

    @Override
    public List<MenuItemResponse> listMenuItemByOrder(String menuCategoryName) {
        System.out.println("menuCategoryName: "+menuCategoryName);
        Optional<MenuCategory> menuCategory = menuCategoryRepository.findByNameAndRestaurant(menuCategoryName, getRestaurant());
        if (menuCategory.isEmpty()) throw new NoSuchElementException("Menu Category not found");

        List<MenuItem> menuItemList = menuItemRepository.findByMenuCategory(menuCategory.get());
        return  menuItemList.stream().map(
                menuItem -> {
                    MenuItemResponse menuItemResponse = modelMapper.map(menuItem,MenuItemResponse.class);
                    menuItemResponse.setMenuCategoryId(String.valueOf(menuItem.getMenuCategory().getId()));
                    return menuItemResponse;
                }
        ).toList();
    }

    @Override
    public List<MenuItemResponse> listMenuItemByOrder(String menuCategoryName, String restaurantId){
        Optional<Restaurant> restaurant = restaurantRepository.findById(UUID.fromString(restaurantId));
        if(restaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");

        Optional<MenuCategory> menuCategory = menuCategoryRepository.findByNameAndRestaurant(menuCategoryName, restaurant.get());
        if (menuCategory.isEmpty()) throw new NoSuchElementException("Menu Category not found");

        List<MenuItem> menuItemList = menuItemRepository.findByMenuCategory(menuCategory.get());
        return  menuItemList.stream().map(
                menuItem -> {
                    MenuItemResponse menuItemResponse = modelMapper.map(menuItem,MenuItemResponse.class);
                    menuItemResponse.setMenuCategoryId(String.valueOf(menuItem.getMenuCategory().getId()));
                    return menuItemResponse;
                }
        ).toList();
    }

    @Override
    public MenuItem getById(String id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById((long) Integer.parseInt(id));
        if (menuItem.isEmpty()) throw new NoSuchElementException("Menu Item not found");

        return menuItem.get();
    }

    @Override
    public MenuItemResponse getByIdResponse(String id){
        Optional<MenuItem> menuItem = menuItemRepository.findById((long) Integer.parseInt(id));
        if (menuItem.isEmpty()) throw new NoSuchElementException("Menu Item not found");
        MenuItemResponse menuItemResponse = modelMapper.map(menuItem.get(),MenuItemResponse.class);
        menuItemResponse.setMenuCategoryId(String.valueOf(menuItem.get().getMenuCategory().getId()));
        return menuItemResponse;
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
            List<MenuItem> menuItemList = menuItemRepository.findByMenuCategory(menuCategory);
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
            List<MenuItem> menuItemList = menuItemRepository.findByMenuCategory(tempMenuCategory);
            menuItemMainList.addAll(menuItemList);
        }

        List<MenuItem> deletMenuItemList = menuItemRepository.findByMenuCategory(menuCategory.get());
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
