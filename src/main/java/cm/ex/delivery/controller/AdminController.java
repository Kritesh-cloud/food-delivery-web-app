package cm.ex.delivery.controller;

import cm.ex.delivery.entity.AuthorityChange;
import cm.ex.delivery.entity.Category;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.AuthorityChangeServiceImpl;
import cm.ex.delivery.service.CategoryServiceImpl;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private AuthorityChangeServiceImpl authorityChangeService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Admin Test"));
    }

    @GetMapping("/userList")
    public ResponseEntity<List<User>> userList() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(userService.userList());
    }

    @PostMapping("/addNewCategory")
    public ResponseEntity<BasicResponse> addNewCategory(@RequestParam String category) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(categoryService.addCategory(category));
    }

    @GetMapping("/listAllCategory")
    public ResponseEntity<List<Category>> listAllCategory() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(categoryService.listCategory());
    }

    @PostMapping("/removeCategory")
    public ResponseEntity<BasicResponse> removeCategory(@RequestParam String category) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(categoryService.removeCategory(category));
    }

    @PostMapping("/listRequestAuthority")
    public ResponseEntity<List<AuthorityChange>> listRequestAuthority() {
        List<AuthorityChange> authorityChangeList = authorityChangeService.listAllAuthorityChangeRequest();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(authorityChangeList);
    }

    @PostMapping("/listRequestAuthorityByRestaurant/{restaurantId}")
    public ResponseEntity<List<AuthorityChange>> listRequestAuthorityByRestaurantId(@PathVariable String restaurantId) {
        List<AuthorityChange> authorityChangeList = authorityChangeService.listAllAuthorityChangeRequestByRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(authorityChangeList);
    }

    @PostMapping("/authorityUpdateRequest")
    public ResponseEntity<BasicResponse> authorityUpdateRequest(
            @RequestParam String newAuthority,
            @RequestParam(value = "restaurantId", defaultValue = "null") String restaurantId) {
        BasicResponse basicResponse = authorityChangeService.requestAuthorityChange(newAuthority, restaurantId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/authorityUpdate")
    public ResponseEntity<BasicResponse> authorityUpdate(
            @RequestParam String userId,
            @RequestParam String newAuthority,
            @RequestParam boolean change) {
        BasicResponse basicResponse = authorityChangeService.authorityChangeUpdate(userId, newAuthority, change);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

}