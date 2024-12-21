package cm.ex.delivery.controller.customer;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BrowseContentResponse;
import cm.ex.delivery.service.MenuItemServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
import cm.ex.delivery.service.interfaces.BrowseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/browse")
public class BrowseContentController {

    @Autowired
    private BrowseContentService browseContentService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication User Browse Test"));
    }

    @GetMapping("/listContent")
    public ResponseEntity<List<BrowseContentResponse>> listContent() {
        List<BrowseContentResponse> browseContentList = browseContentService.listAllBrowseContentByOrder();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentList);
    }

    @GetMapping("/content/{contentId}")
    public ResponseEntity<BrowseContent> content(@PathVariable String contentId) {
        BrowseContent browseContent = browseContentService.getBrowseContentById(contentId);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContent);
    }

    @GetMapping("/listByRestaurantTitle")
    public ResponseEntity<BasicResponse> listByRestaurantTitle(@RequestParam String restaurant) {
        List<Restaurant> restaurantList = restaurantService.listAllRestaurant();
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/listByFoodTitle")
    public ResponseEntity<BasicResponse> listByFoodTitle(@RequestParam String food) {
//        List<MenuItem> menuItemList = menuItemService.listMenuItemByOrder();
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }


}

// browse content and search and filter