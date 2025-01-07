package cm.ex.delivery.controller.customer;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.Category;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BrowseContentResponse;
import cm.ex.delivery.response.MenuCategoryResponse;
import cm.ex.delivery.response.MenuItemResponse;
import cm.ex.delivery.service.CategoryServiceImpl;
import cm.ex.delivery.service.MenuCategoryServiceImpl;
import cm.ex.delivery.service.MenuItemServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
import cm.ex.delivery.service.interfaces.BrowseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/user/browse")
public class BrowseContentController {

    @Autowired
    private BrowseContentService browseContentService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private MenuCategoryServiceImpl menuCategoryService;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication User Browse Test"));
    }

    @GetMapping("/list-main-category")
    public ResponseEntity<List<Category>> listAllCategory() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(categoryService.listCategory());
    }

    @GetMapping("/list-browse-content")
    public ResponseEntity<List<BrowseContentResponse>> listContent() {
        List<BrowseContentResponse> browseContentList = browseContentService.listAllBrowseContentByOrder();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentList);
    }

    @GetMapping("/list-browse-content/{contentId}")
    public ResponseEntity<BrowseContent> content(@PathVariable String contentId) {
        BrowseContent browseContent = browseContentService.getBrowseContentById(contentId);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContent);
    }

    @GetMapping("/list-menu-category")
    public ResponseEntity<List<MenuCategoryResponse>> listMenuCategory(@RequestParam String restaurantId) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(menuCategoryService.listMenuCategoryByOrder(restaurantId));
    }

    @GetMapping("/list-menu-item")
    public ResponseEntity<List<MenuItemResponse>> listMenuItem(@RequestParam String menuCategoryName, @RequestParam String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(menuItemService.listMenuItemByOrder(menuCategoryName, restaurantId));
    }

    @GetMapping("/list-all-restaurants")
    public ResponseEntity<List<Restaurant>> listByRestaurantTitle() {
        List<Restaurant> restaurantList = restaurantService.listAllRestaurant();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantList);
    }


}

// browse content and search and filter