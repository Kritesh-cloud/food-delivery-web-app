package cm.ex.delivery.controller;

import cm.ex.delivery.entity.Category;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
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

    @PostMapping("/listUserByAuthority")
    public ResponseEntity<List<User>> listUserByAuthority(String authority) {
        List<User> userList = userService.listUserByAuthority(authority);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userList);
    }

    @PostMapping("/assignAuthority")
    public ResponseEntity<BasicResponse> assignAuthority(@RequestParam String authority, @RequestParam String userId) {
        BasicResponse basicResponse = userService.assignAuthority(authority, userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/removeAuthority")
    public ResponseEntity<BasicResponse> removeAuthority(@RequestParam String authority, @RequestParam String userId) {
        BasicResponse basicResponse = userService.removeAuthority(authority, userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }
}