package cm.ex.delivery.service;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.repository.MenuCategoryRepository;
import cm.ex.delivery.repository.RestaurantRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class MenuCategoryServiceImpl implements MenuCategoryService {

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public BasicResponse addMenuCategory(String category) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant for this user not found");

        if (category.isBlank()) throw new IllegalArgumentException("Input cannot be blank.");

        Optional<MenuCategory> menuCategoryCheck = menuCategoryRepository.findByName(category);

        List<MenuCategory> menuCategoryList = menuCategoryRepository.findByRestaurantOrdered(restaurant.get());

        if (menuCategoryCheck.isPresent())
            return BasicResponse.builder().status(true).code(200).message("This menu category is already added").build();

        int menuCategoryOrder = menuCategoryList.isEmpty() ? 1 : menuCategoryList.get(menuCategoryList.size() - 1).getCategoryOrder() + 1;

        MenuCategory newCategory = new MenuCategory(menuCategoryOrder, category, restaurant.get());
        return BasicResponse.builder().status(true).code(200).message("New menu category added successfully").build();
    }

    @Override
    public List<MenuCategory> listMenuCategoryByOrder() throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant for this user not found");

        List<MenuCategory> menuCategoryList = menuCategoryRepository.findByRestaurantOrdered(restaurant.get());
        return menuCategoryList.isEmpty() ? new ArrayList<>() : menuCategoryList;
    }

    @Override
    public BasicResponse updateOrder(int currentOrder, int newOrder) throws AccessDeniedException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant for this user not found");

        List<MenuCategory> menuCategoryList = menuCategoryRepository.findByRestaurantOrdered(restaurant.get());

        if (currentOrder < 1 || currentOrder > menuCategoryList.size() || newOrder < 1 || newOrder > menuCategoryList.size())
            throw new IllegalArgumentException("Invalid new order");

        Optional<MenuCategory> currentMenuCategory = menuCategoryRepository.findByCategoryOrderAndRestaurant(currentOrder, restaurant.get());
        if (currentMenuCategory.isEmpty()) throw new NoSuchElementException("Menu category not found");
        currentMenuCategory.get().setCategoryOrder(newOrder);
        menuCategoryRepository.save(currentMenuCategory.get());

        Optional<MenuCategory> newMenuCategory = menuCategoryRepository.findByCategoryOrderAndRestaurant(currentOrder, restaurant.get());
        if (newMenuCategory.isEmpty()) throw new NoSuchElementException("Menu category not found");
        newMenuCategory.get().setCategoryOrder(currentOrder);
        menuCategoryRepository.save(newMenuCategory.get());

        return BasicResponse.builder().status(true).code(200).message("Menu category order updated successfully").build();
    }

    @Override
    public BasicResponse removeMenuCategory(String category) {
        Optional<MenuCategory> menuCategoryCheck = menuCategoryRepository.findByName(category);
        if (menuCategoryCheck.isEmpty())
            return BasicResponse.builder().status(false).code(404).message("MenuCategory not found").build();

        menuCategoryRepository.delete(menuCategoryCheck.get());
        return BasicResponse.builder().status(true).code(200).message("MenuCategory deleted successfully").build();
    }
}

