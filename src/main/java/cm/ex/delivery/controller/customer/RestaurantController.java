package cm.ex.delivery.controller.customer;

import cm.ex.delivery.response.BasicResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/restaurant")
public class RestaurantController {

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication User Restaurant Test"));
    }

    @GetMapping("/viewRestaurant/{restaurantId}")
    public ResponseEntity<BasicResponse> viewRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/viewRestaurantMenu/{restaurantId}")
    public ResponseEntity<BasicResponse> viewRestaurantMenu(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/menuItemDetails/{menuItemId}")
    public ResponseEntity<BasicResponse> menuItemDetails(@PathVariable String menuItemId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }
}
// restaurant and menu
