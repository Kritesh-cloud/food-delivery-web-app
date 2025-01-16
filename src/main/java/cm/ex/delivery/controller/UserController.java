package cm.ex.delivery.controller;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.RestaurantServiceImpl;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/account")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication User Account Test"));
    }

    @PostMapping("/register-restaurant")
    public ResponseEntity<BasicResponse> registerRestaurant(
            @RequestPart("restaurantInfo") Restaurant restaurantInfo,
            @RequestPart("icon") MultipartFile icon,
            @RequestPart("background") MultipartFile background,
            @RequestPart("gallery") MultipartFile... gallery
    ) {
        System.out.println(restaurantInfo.toString());
        System.out.println(icon.getOriginalFilename());
        System.out.println(background.getOriginalFilename());
        System.out.println(gallery.length);

        BasicResponse basicResponse = restaurantService.addRestaurant(restaurantInfo, icon, background, gallery);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/view-profile")
    public ResponseEntity<User> viewProfile() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.userInfo());
    }

    @PostMapping("/update-profile")
    public ResponseEntity<BasicResponse> updateProfile(@RequestBody User user) {
        BasicResponse basicResponse = userService.updateUser(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/delete-profile")
    public ResponseEntity<BasicResponse> deleteProfile() {
        BasicResponse basicResponse = userService.deleteUser();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/requestAuthorityModerator")
    public ResponseEntity<BasicResponse> requestAuthorityModerator() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/requestAuthorityStaff")
    public ResponseEntity<BasicResponse> requestAuthorityStaff(@RequestParam String restaurant) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/requestAuthorityDeliveryPerson")
    public ResponseEntity<BasicResponse> requestAuthorityDeliveryPerson() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }
}
// account & request role

