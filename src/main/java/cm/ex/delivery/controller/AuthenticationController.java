package cm.ex.delivery.controller;

import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("")
@RestController
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/test")
    public String test() {
        return "Delivery Test Controller";
    }

    @PostMapping("/signUp")
    public ResponseEntity<BasicResponse> signUp(User user) throws IOException {
        BasicResponse response = userService.signUp(user, null);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<BasicResponse> signIn(User user) {
        BasicResponse response = userService.logIn(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode())).body(response);
    }

}

