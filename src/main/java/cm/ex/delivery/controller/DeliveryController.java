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
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication Delivery Test"));
    }

    @PostMapping("/listOrderToReceive")
    public ResponseEntity<BasicResponse> listOrderToReceive(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/acceptOrderToReceive/{orderId}")
    public ResponseEntity<BasicResponse> acceptOrderToReceive(@PathVariable String orderId) {
        BasicResponse basicResponse = orderService.orderUpdatePreparedDelivering();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/declineOrderToReceive/{orderId}")//[remove zzz]
    public ResponseEntity<List<Order>> declineOrderToReceive(@PathVariable String orderId) {
        List<Order> orderList = orderService.listAllOrderByStatus("accepted-preparing");
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderList);
    }

    @PostMapping("/listOrderToDeliver")
    public ResponseEntity<List<Order>> listOrderToDeliver() {
        List<Order> orderList = orderService.listAllByDeliveryId();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderList);
    }

    @PostMapping("/acceptOrderToDeliver/{orderId}")
    public ResponseEntity<BasicResponse> acceptOrderToDeliver(@PathVariable String orderId) {
        BasicResponse basicResponse = orderService.orderUpdatePreparedDelivering();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/declineOrderToDeliver/{orderId}")
    public ResponseEntity<BasicResponse> declineOrderToDeliver(@PathVariable String orderId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/listOrderDeliverHistory")
    public ResponseEntity<BasicResponse> listOrderDeliverHistory() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

    @PostMapping("/sendReport")
    public ResponseEntity<BasicResponse> sendReport() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

}
