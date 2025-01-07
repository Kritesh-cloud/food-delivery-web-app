package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.StaffId;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface StaffIdService {

    public StaffId addToStaffId(String staffId, Restaurant restaurant);

    public StaffId findById(String staffId);

    public List<StaffId> listStaffIdByRestaurant(Restaurant restaurant);

    public List<StaffId> listAllStaffId();

    public BasicResponse removeByStaffId(String staffId, Restaurant restaurant);

    public BasicResponse removeByRestaurant(Restaurant restaurant);
}
