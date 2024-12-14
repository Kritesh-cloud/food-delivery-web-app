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

    public BasicResponse orderUpdateAcceptedPreparing();

    public BasicResponse orderUpdatePreparedDelivering();

    public BasicResponse orderUpdateDelivered();

    public BasicResponse orderUpdateCancelled();

}

/*
order process update

public void orderUpdateAcceptedPreparing();

public void orderUpdatePreparedDelivering();

public void orderUpdateDelivered();

public void orderUpdateCanceled();

* */

