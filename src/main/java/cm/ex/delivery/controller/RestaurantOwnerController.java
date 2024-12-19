package cm.ex.delivery.controller;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.request.UpdateRestaurant;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.MenuCategoryServiceImpl;
import cm.ex.delivery.service.MenuItemServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class RestaurantOwnerController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private MenuCategoryServiceImpl menuCategoryService;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/registerRestaurant")
    public ResponseEntity<BasicResponse> registerRestaurant(
            @RequestPart("restaurantInfo") Restaurant restaurantInfo,
            @RequestPart("icon") MultipartFile icon,
            @RequestPart("background") MultipartFile background,
            @RequestPart("gallery") MultipartFile... gallery) {
        BasicResponse basicResponse = restaurantService.addRestaurant(restaurantInfo, icon, background, gallery);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/viewRestaurant/{restaurantId}")
    public ResponseEntity<Restaurant> viewRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(restaurantService.getRestaurantById(restaurantId));
    }

    @PostMapping("/updateRestaurant")
    public ResponseEntity<BasicResponse> updateRestaurant(
            @RequestPart("restaurantInfo") UpdateRestaurant restaurantInfo,
            @RequestPart("icon") MultipartFile icon,
            @RequestPart("background") MultipartFile background,
            @RequestPart("gallery") MultipartFile... gallery) {
        BasicResponse basicResponse = restaurantService.updateRestaurant(restaurantInfo, icon, background, gallery);
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(basicResponse);
    }

    @PostMapping("/deleteRestaurant/{restaurantId}")
    public ResponseEntity<BasicResponse> deleteRestaurant() {
        BasicResponse basicResponse = restaurantService.removeRestaurant();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/addMenuCategory")
    public ResponseEntity<BasicResponse> addMenuCategory(@RequestParam String menuCategory) throws AccessDeniedException {
        BasicResponse basicResponse = menuCategoryService.addMenuCategory(menuCategory);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/listMenuCategory/{restaurantId}")
    public ResponseEntity<List<MenuCategory>> listMenuCategory(@PathVariable String restaurantId) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(menuCategoryService.listMenuCategoryByOrder());
    }

    @PostMapping("/deleteMenuCategory/{menuCategory}")
    public ResponseEntity<BasicResponse> deleteMenuCategory(@PathVariable String menuCategory) {
        BasicResponse basicResponse = menuCategoryService.removeMenuCategory(menuCategory);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/addMenuItem")
    public ResponseEntity<BasicResponse> addMenuItem(@RequestParam String menuItem, @RequestParam String menuCategoryName, @RequestParam double price) {
        BasicResponse basicResponse = menuItemService.addMenuItem(menuItem, menuCategoryName, price);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/listMenuItem")
    public ResponseEntity<List<MenuItem>> listMenuItem(@RequestParam String menuCategoryName) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(menuItemService.listMenuItemByOrder(menuCategoryName));
    }

    @PostMapping("/updateMenuItem")
    public ResponseEntity<BasicResponse> updateMenuItem(@RequestParam String menuItem) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/deleteMenuItem/{menuItemId}")
    public ResponseEntity<BasicResponse> deleteMenuItem(@PathVariable String menuItemId) throws AccessDeniedException {
        BasicResponse basicResponse = menuItemService.removeByItemId(menuItemId);
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }
}
