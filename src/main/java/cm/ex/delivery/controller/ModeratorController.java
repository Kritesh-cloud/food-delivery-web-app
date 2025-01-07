package cm.ex.delivery.controller;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.request.BrowseListDto;
import cm.ex.delivery.request.NotificationDto;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BrowseContentResponse;
import cm.ex.delivery.service.BrowseContentServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {


    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private BrowseContentServiceImpl browseContentService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication Moderator Test"));
    }

    @GetMapping("/list-restaurant")
    public ResponseEntity<List<Restaurant>> listRestaurant() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.listAllRestaurant());
    }

    @PostMapping("/accept-restaurant/{restaurantId}")
    public ResponseEntity<Restaurant> acceptRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.getRestaurantById(restaurantId));
    }

    @PostMapping("/decline-restaurant/{restaurantId}")
    public ResponseEntity<BasicResponse> declineRestaurant(@PathVariable String restaurantId) {
        BasicResponse basicResponse = restaurantService.removeRestaurant();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/create-notification")
    public ResponseEntity<BasicResponse> createNotification(@RequestBody NotificationDto notificationDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/list-notification")
    public ResponseEntity<BasicResponse> listNotification() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/list-report")
    public ResponseEntity<BasicResponse> listReport() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/create-browse-content")
    public ResponseEntity<BasicResponse> createBrowseList(@RequestBody BrowseListDto browseListDto) {
        BasicResponse basicResponse = browseContentService.createBrowseContent(browseListDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/list-browse-content")
    public ResponseEntity<List<BrowseContentResponse> > listBrowseList() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentService.listAllBrowseContentByOrder());
    }

    @GetMapping("/get-browse-content/{browseContentId}")
    public ResponseEntity<BrowseContent> getBrowseList(@PathVariable String browseContentId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentService.getBrowseContentById(browseContentId));
    }

    @GetMapping("/get-browse-content-by-title/{title}")
    public ResponseEntity<BrowseContent> getBrowseListByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentService.getBrowseContentByTitle(title));
    }

    @PostMapping("/update-browse-content")
    public ResponseEntity<BasicResponse> updateBrowseList(@RequestBody BrowseListDto browseListDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(new BasicResponse("I'm a Tea pot. [remove]"));
    }

    @PostMapping("/delete-browse-content/{browseListId}")
    public ResponseEntity<BasicResponse> deleteBrowseList(@PathVariable String browseListId) {
        BasicResponse basicResponse = browseContentService.removeBrowseContentById(browseListId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
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




    @GetMapping("/list-restaurant-browse-content")
    public ResponseEntity<List<BrowseContentResponse>> listBrowseContent() {
        List<BrowseContentResponse> browseContentResponseList = browseContentService.listBrowseContentIdResponse();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentResponseList);
    }

    @GetMapping("/list-restaurant-browse-content/{id}")
    public ResponseEntity<BrowseContentResponse> listBrowseContentById(@PathVariable String id) {
        BrowseContentResponse browseContentResponse = browseContentService.getBrowseContentIdResponseById(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(browseContentResponse);
    }

    @PostMapping("/add-item-to-browse-content")
    public ResponseEntity<BasicResponse> addItemToBrowseContent(@RequestParam String browseContentId, @RequestParam String itemId) {
        System.out.println("add browseContentId: "+browseContentId+ ", itemId:"+itemId);
        BasicResponse basicResponse = browseContentService.addBrowseContentItem(browseContentId,itemId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/remove-item-from-browse-content")
    public ResponseEntity<BasicResponse> removeItemFromBrowseContent(@RequestParam String browseContentId, @RequestParam String itemId) {
        System.out.println("rmv browseContentId: "+browseContentId+ ", itemId:"+itemId);
        BasicResponse basicResponse = browseContentService.removeBrowseContentItem(browseContentId,itemId);
//        basicResponse = new BasicResponse("just msg");
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

}