package cm.ex.delivery.controller;

import cm.ex.delivery.response.BasicResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gUser")
public class GuestUserController {

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication Guest User Test"));
    }
}
// later