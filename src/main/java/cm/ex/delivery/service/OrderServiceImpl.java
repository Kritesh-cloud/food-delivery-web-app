package cm.ex.delivery.service;

import cm.ex.delivery.entity.Order;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.OrderRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketServiceImpl basketService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Override
    public BasicResponse createOrder() {
        Order oldOrder = getByUserId();
        if (!oldOrder.isActive()) {
            Order order = new Order("pending", true, basketService.getUserBasket());
            orderRepository.save(order);
        }
        return BasicResponse.builder().status(true).code(200).message("New order created successfully").build();
    }

    @Override
    public Order getById(String id) {
        Optional<Order> order = orderRepository.findById(UUID.fromString(id));
        if (order.isEmpty()) throw new NoSuchElementException("Order not found");
        return order.get();
    }

    @Override
    public Order getByUserId() {
        return getUserOrder();
    }

    @Override
    public List<Order> listAllOrderByStatus(String status) {
        List<Order> orderList = orderRepository.findAllByStatus(status);
        return orderList.isEmpty() ? List.of() : orderList;
    }

    @Override
    public List<Order> listAllByUserId() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        List<Order> orderList = orderRepository.findByBuyerId(userAuth.getUser());
        return orderList.isEmpty() ? List.of() : orderList;
    }

    //TODO update this to get order list by restaurant id
    //update sql query in repository
    @Override
    public List<Order> listAllByRestaurantId(String restaurantId){
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        List<Order> orderList = orderRepository.listByRestaurantId(restaurant);
        return orderList.isEmpty() ? List.of() : orderList;
    }

    @Override
    public List<Order> listAllByDeliveryId(){
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        verifyDeliveryAccount();
        User deliveryId = userAuth.getUser();
        List<Order> orderList = orderRepository.findByDeliveryPersonId(deliveryId);
        return orderList.isEmpty() ? List.of() : orderList;
    }

    @Override
    public BasicResponse orderUpdateAcceptedPreparing(String restaurantId) {
        Restaurant restaurant = verifyStaffAccountWithRestaurant(restaurantId);
        Order order = getUserOrder();

        if(!order.getStatus().equalsIgnoreCase("pending")) throw new AccessDeniedException("Can not accept this order.");

        order.setStatus("accepted-preparing");
        order.setRestaurant(restaurant);
        orderRepository.save(order);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to accepted and pending").build();
    }

    @Override
    public BasicResponse orderUpdatePreparedDelivering() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        verifyDeliveryAccount();
        Order order = getUserOrder();
        if(!order.getStatus().equalsIgnoreCase("accepted-preparing")) throw new AccessDeniedException("Can not deliver this order.");
        order.setStatus("prepared-delivering");
        order.setDeliveryPerson(userAuth.getUser());
        orderRepository.save(order);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to prepared and delivering").build();
    }

    @Override
    public BasicResponse orderUpdateDelivered() {
        Order order = getUserOrder();
        if(!order.getStatus().equalsIgnoreCase("prepared-delivering")) throw new AccessDeniedException("Can not deliver this order.");
        order.setStatus("update-delivered");
        orderRepository.save(order);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to update and delivered").build();
    }

    @Override
    public BasicResponse orderUpdateDeclined(String restaurantId) {
        Order order = getUserOrder();
        if(!order.getStatus().equalsIgnoreCase("pending")) throw new AccessDeniedException("Can not decline this order.");
        order.setStatus("declined");
        orderRepository.save(order);
        order.setActive(false);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to cancelled").build();
    }

    private Order getUserOrder() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        Optional<Order> order = orderRepository.findActiveOrderByUserId(userAuth.getUser());
        if (order.isEmpty()) throw new NoSuchElementException("Order not found");
        return order.get();
    }

    private Restaurant verifyStaffAccountWithRestaurant(String restaurantId){
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if(!userAuth.getAuthorities().contains(new SimpleGrantedAuthority("staff"))){
            throw new AccessDeniedException("Access denied. Staff account required");
        }
        return restaurantService.getRestaurantById(restaurantId);
    }

    private void verifyDeliveryAccount(){
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        if(!userAuth.getAuthorities().contains(new SimpleGrantedAuthority("delivery"))){
            throw new AccessDeniedException("Access denied. Staff account required");
        }
    }

}

