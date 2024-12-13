package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Order;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface OrderService {

    public BasicResponse createOrder();

    public Order getById(String id);

    public Order getByUserId(); //status on progress or active order

    public List<Order> listAllOrderByStatus(String status);

    public List<Order> listAllByUserId();

    public BasicResponse cancelOrder();
}
