package cm.ex.delivery.controller;

import cm.ex.delivery.entity.Order;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Staff Controller Test"));
    }

    @GetMapping("/listMenu")
    public ResponseEntity<BasicResponse> listMenu() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @GetMapping("/listOrders/{restaurantId}")
    public ResponseEntity<List<Order>> listOrders(@PathVariable String restaurantId) {
        List<Order> orderList = orderService.listAllByRestaurantId(restaurantId);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderList);
    }

    @PostMapping("/acceptOrder/{restaurantId}")
    public ResponseEntity<BasicResponse> acceptOrder(@PathVariable String restaurantId) {
        BasicResponse basicResponse = orderService.orderUpdateAcceptedPreparing(restaurantId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/declineOrder/{restaurantId}")
    public ResponseEntity<BasicResponse> declineOrder(@PathVariable String restaurantId) {
        BasicResponse basicResponse = orderService.orderUpdateDeclined(restaurantId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/sendReport")
    public ResponseEntity<BasicResponse> sendReport() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }
}
