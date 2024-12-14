package cm.ex.delivery.service;

import cm.ex.delivery.entity.Order;
import cm.ex.delivery.repository.OrderRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Order> orderList = orderRepository.findByOwnerId(userAuth.getUser());
        return orderList.isEmpty() ? List.of() : orderList;
    }

    @Override
    public BasicResponse orderUpdateAcceptedPreparing() {
        Order order = getUserOrder();
        order.setStatus("accepted-preparing");
        orderRepository.save(order);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to accepted and pending").build();
    }

    @Override
    public BasicResponse orderUpdatePreparedDelivering() {
        Order order = getUserOrder();
        order.setStatus("prepared-delivering");
        orderRepository.save(order);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to prepared and delivering").build();
    }

    @Override
    public BasicResponse orderUpdateDelivered() {
        Order order = getUserOrder();
        order.setStatus("update-delivered");
        orderRepository.save(order);
        return BasicResponse.builder().status(true).code(200).message("Order status updated to update and delivered").build();
    }

    @Override
    public BasicResponse orderUpdateCancelled() {
        Order order = getUserOrder();
        order.setStatus("cancelled");
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

}
