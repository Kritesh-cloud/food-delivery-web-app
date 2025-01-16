package cm.ex.delivery.controller;

import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BrowseContentResponse;
import cm.ex.delivery.response.RestaurantResponse;
import cm.ex.delivery.response.ShortRestaurantResponse;
import cm.ex.delivery.service.BrowseContentServiceImpl;
import cm.ex.delivery.service.ImageServiceImpl;
import cm.ex.delivery.service.RestaurantServiceImpl;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("")
@RestController
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private BrowseContentServiceImpl browseContentService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication Controller Test"));
    }

    @PostMapping("/signUp")
    public ResponseEntity<BasicResponse> signUp(@RequestBody User user) throws IOException {
        BasicResponse response = userService.signUp(user, null);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<BasicResponse> signIn(@RequestBody User user) {
        BasicResponse response = userService.logIn(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @GetMapping("/user-list")
    public ResponseEntity<List<User>> userList() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.userList());
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.findUserById(userId));
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getProductImageByUrl(@PathVariable String imageId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageService.getImageById(imageId),headers, HttpStatus.OK);
    }

    @GetMapping("/list-restaurant-details")
    public ResponseEntity<List<RestaurantResponse>> listRestaurantDetails() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.listAllRestaurantDetails());
    }

    @GetMapping("/list-short-restaurant-details")
    public ResponseEntity<List<ShortRestaurantResponse>> listShortRestaurantDetails() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.listAllShortRestaurantDetails());
    }

    @GetMapping("/list-restaurant-details-by-id/{id}")
    public ResponseEntity<RestaurantResponse> listRestaurantDetailsById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.getRestaurantDetailsById(id));
    }

    @GetMapping("/list-restaurant-details/{browseId}")
    public ResponseEntity<List<RestaurantResponse>> listRestaurantDetailsByBrowseList(@PathVariable String browseId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.listAllRestaurantDetailsByBrowseList(browseId));
    }

    @GetMapping("/list-short-restaurant-details/{browseId}")
    public ResponseEntity<List<ShortRestaurantResponse>> listShortRestaurantDetailsByBrowseList(@PathVariable String browseId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.listAllShortRestaurantDetailsByBrowseList(browseId));
    }

    @GetMapping("/reverse-list-restaurant-details/{browseId}")
    public ResponseEntity<List<RestaurantResponse>> reverseListRestaurantDetailsByBrowseList(@PathVariable String browseId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(restaurantService.reverseListAllRestaurantDetailsByBrowseList(browseId));
    }



//    listAllRestaurantDetails

}

