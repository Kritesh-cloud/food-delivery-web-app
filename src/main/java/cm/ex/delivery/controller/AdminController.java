package cm.ex.delivery.controller;

import cm.ex.delivery.entity.Category;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.CategoryServiceImpl;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Admin Test"));
    }

    @GetMapping("/user-list")
    public ResponseEntity<List<User>> userList() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(userService.userList());
    }

    @PostMapping("/add-category")
    public ResponseEntity<BasicResponse> addCategory(@RequestParam String category) {
        BasicResponse basicResponse = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/add-new-category")
    public ResponseEntity<BasicResponse> addNewCategory(
            @RequestPart("category") String category,
            @RequestPart("image") MultipartFile image) throws IOException {
        BasicResponse basicResponse = categoryService.addNewCategory(category,image);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(basicResponse);
    }

    @GetMapping("/list-category")
    public ResponseEntity<List<Category>> listAllCategory() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(categoryService.listCategory());
    }

    @PostMapping("/remove-category")
    public ResponseEntity<BasicResponse> removeCategory(@RequestParam String category) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(categoryService.removeCategory(category));
    }

    @GetMapping("/list-user/{authority}")
    public ResponseEntity<List<User>> listUserByAuthority(@PathVariable String authority) {
        List<User> userList = userService.listUserByAuthority(authority);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userList);
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