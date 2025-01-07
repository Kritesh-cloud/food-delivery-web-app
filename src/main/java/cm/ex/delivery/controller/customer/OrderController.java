package cm.ex.delivery.controller.customer;

import cm.ex.delivery.request.StripeRequestDto;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BasketResponse;
import cm.ex.delivery.response.StripeResponseDto;
import cm.ex.delivery.service.BasketServiceImpl;
import cm.ex.delivery.service.OrderServiceImpl;
import cm.ex.delivery.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private BasketServiceImpl basketService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private StripeService stripeService;

    @PostMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot. Authentication User Order Test"));
    }

    @PostMapping("/add-to-basket/{menuItemId}")
    public ResponseEntity<BasicResponse> addToBasket(@PathVariable String menuItemId) {
        System.out.println("menuItemId: "+menuItemId);
        BasicResponse basicResponse = basketService.addItemToBasket(menuItemId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @GetMapping("/get-user-basket")
    public ResponseEntity<BasketResponse> getUserBasket() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(basketService.userBasket());
    }

    @GetMapping("/list-basket-item")
    public ResponseEntity<BasicResponse> listBasketItem() {
        BasicResponse basicResponse = new BasicResponse();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/update-item-quantity")
    public ResponseEntity<BasicResponse> updateItemQuantity(@RequestParam String menuItemId, @RequestParam Integer quantity) {
        BasicResponse basicResponse = basketService.updateItemQuantityOfBasket(menuItemId, quantity);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/remove-from-basket/{menuItemId}")
    public ResponseEntity<BasicResponse> removeFromBasket(@PathVariable String menuItemId) {
        BasicResponse basicResponse = basketService.removeItemFromBasket(menuItemId);
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/place-order")
    public ResponseEntity<BasicResponse> placeOrder() {
        BasicResponse basicResponse = orderService.createOrder();
        return ResponseEntity.status(HttpStatusCode.valueOf(basicResponse.getCode())).body(basicResponse);
    }

    @PostMapping("/payment-detail")
    public ResponseEntity<StripeResponseDto> paymentDetail(@RequestBody StripeRequestDto stripeRequestDto) {
        StripeResponseDto stripeResponse = stripeService.checkoutProducts(stripeRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("/orderHistory")
    public ResponseEntity<BasicResponse> orderHistory() {
        return ResponseEntity.status(HttpStatusCode.valueOf(418)).body(new BasicResponse("I'm a Tea pot"));
    }

}

// basket and order