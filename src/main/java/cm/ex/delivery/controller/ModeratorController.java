package cm.ex.delivery.controller;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.request.BrowseListDto;
import cm.ex.delivery.request.NotificationDto;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.BrowseContentServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
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

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/listRestaurant")
    public ResponseEntity<List<Restaurant>> listRestaurant(@RequestParam String option) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(restaurantService.listAllRestaurant());
    }

    @PostMapping("/acceptRestaurant/{restaurantId}")
    public ResponseEntity<Restaurant> acceptRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(restaurantService.getRestaurantById(restaurantId));
    }

    @PostMapping("/declineRestaurant")
    public ResponseEntity<BasicResponse> declineRestaurant() {
        BasicResponse basicResponse = restaurantService.removeRestaurant();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/createNotification")
    public ResponseEntity<BasicResponse> createNotification(@RequestBody NotificationDto notificationDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/listNotification")
    public ResponseEntity<BasicResponse> listNotification() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/listReport")
    public ResponseEntity<BasicResponse> listReport() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/createBrowseList")
    public ResponseEntity<BasicResponse> createBrowseList(@RequestBody BrowseListDto browseListDto) {
        BasicResponse basicResponse = browseContentService.createBrowseContent(browseListDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/listBrowseList")
    public ResponseEntity<List<BrowseContent> > listBrowseList() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(browseContentService.listAllBrowseContentByOrder());
    }

    @PostMapping("/getBrowseList/{browseListId}")
    public ResponseEntity<BrowseContent> getBrowseList(@PathVariable String browseListId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(browseContentService.getBrowseContentById(browseListId));
    }

    @PostMapping("/getBrowseListByTitle/{title}")
    public ResponseEntity<BrowseContent> getBrowseListByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(browseContentService.getBrowseContentByTitle(title));
    }

    @PostMapping("/updateBrowseList")
    public ResponseEntity<BasicResponse> updateBrowseList(@RequestBody BrowseListDto browseListDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. [remove]"));
    }

    @PostMapping("/deleteBrowseList/{browseListId}")
    public ResponseEntity<BasicResponse> deleteBrowseList(@PathVariable String browseListId) {
        BasicResponse basicResponse = browseContentService.removeBrowseContentById(browseListId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

}