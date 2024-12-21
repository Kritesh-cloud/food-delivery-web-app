package cm.ex.delivery.controller;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.request.UpdateRestaurant;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.MenuCategoryResponse;
import cm.ex.delivery.response.MenuItemResponse;
import cm.ex.delivery.service.MenuCategoryServiceImpl;
import cm.ex.delivery.service.MenuItemServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private MenuCategoryServiceImpl menuCategoryService;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Owner Controller Test"));
    }

//    @PostMapping("/registerRestaurant")
//    public ResponseEntity<BasicResponse> registerRestaurant(
//            @RequestPart("restaurantInfo") Restaurant restaurantInfo,
//            @RequestPart("icon") MultipartFile icon,
//            @RequestPart("background") MultipartFile background,
//            @RequestPart("gallery") MultipartFile... gallery) {
//        BasicResponse basicResponse = restaurantService.addRestaurant(restaurantInfo, icon, background, gallery);
//        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
//    }

    @GetMapping("/view-restaurant")
    public ResponseEntity<Restaurant> viewRestaurant() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.getOwnerRestaurant());
    }

    @PostMapping("/update-restaurant")
    public ResponseEntity<BasicResponse> updateRestaurant(
            @RequestPart("restaurantInfo") UpdateRestaurant restaurantInfo,
            @RequestPart("icon") MultipartFile icon,
            @RequestPart("background") MultipartFile background,
            @RequestPart("gallery") MultipartFile... gallery) {
        BasicResponse basicResponse = restaurantService.updateRestaurant(restaurantInfo, icon, background, gallery);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(basicResponse);
    }

    @PostMapping("/delete-restaurant")
    public ResponseEntity<BasicResponse> deleteRestaurant() {
        BasicResponse basicResponse = restaurantService.removeRestaurant();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/add-menu-category")
    public ResponseEntity<BasicResponse> addMenuCategory(@RequestParam String menuCategory) throws AccessDeniedException {
        BasicResponse basicResponse = menuCategoryService.addMenuCategory(menuCategory);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/list-menu-category")
    public ResponseEntity<List<MenuCategoryResponse>> listMenuCategory() throws AccessDeniedException {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(menuCategoryService.listMenuCategoryByOrder());
    }

    @PostMapping("/delete-menu-category")
    public ResponseEntity<BasicResponse> deleteMenuCategory(@RequestParam String menuCategoryId) {
        BasicResponse basicResponse = menuCategoryService.removeMenuCategory(menuCategoryId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/add-menu-item")
    public ResponseEntity<BasicResponse> addMenuItem(@RequestParam String menuItem, @RequestParam String menuCategoryId, @RequestParam double price) {
        BasicResponse basicResponse = menuItemService.addMenuItem(menuItem, menuCategoryId, price);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/list-menu-item")
    public ResponseEntity<List<MenuItemResponse>> listMenuItem(@RequestParam String menuCategoryName) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(menuItemService.listMenuItemByOrder(menuCategoryName));
    }

    @GetMapping("/get-menu-item/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable String menuItemId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(menuItemService.getByIdResponse(menuItemId));
    }

    @PostMapping("/update-menu-item")
    public ResponseEntity<BasicResponse> updateMenuItem(@RequestParam String menuItem) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/delete-menu-item/{menuItemId}")
    public ResponseEntity<BasicResponse> deleteMenuItem(@PathVariable String menuItemId) throws AccessDeniedException {
        BasicResponse basicResponse = menuItemService.removeByItemId(menuItemId);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(basicResponse);
    }

    @PostMapping("/assign-authority")
    public ResponseEntity<BasicResponse> assignAuthority(@RequestParam String authority, @RequestParam String userId) {
        BasicResponse basicResponse = userService.assignAuthority(authority, userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/remove-authority")
    public ResponseEntity<BasicResponse> removeAuthority(@RequestParam String authority, @RequestParam String userId) {
        BasicResponse basicResponse = userService.removeAuthority(authority, userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }
}
