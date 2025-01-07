package cm.ex.delivery.service;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.StaffId;
import cm.ex.delivery.repository.StaffIdRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.StaffIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StaffIdServiceImpl implements StaffIdService {

    @Autowired
    private StaffIdRepository staffIdRepository;

    @Override
    public StaffId addToStaffId(String staffId, Restaurant restaurant) {
        return staffIdRepository.save(new StaffId(staffId, restaurant));
    }

    @Override
    public StaffId findById(String staffId) {
        return staffIdRepository.findById(Long.valueOf(staffId)).orElseThrow(() -> new NoSuchElementException("Staff id not found"));
    }

    @Override
    public List<StaffId> listStaffIdByRestaurant(Restaurant restaurant) {
        List<StaffId> staffIdList = staffIdRepository.findByRestaurantId(restaurant.getId());
        return staffIdList.isEmpty() ? List.of() : staffIdList;
    }

    @Override
    public List<StaffId> listAllStaffId() {
        List<StaffId> staffIdList = staffIdRepository.findAll();
        return staffIdList.isEmpty() ? List.of() : staffIdList;
    }

    @Override
    public BasicResponse removeByStaffId(String staffId, Restaurant restaurant) {
        Optional<StaffId> removeStaff = staffIdRepository.findByStaffIdAndRestaurant(staffId, restaurant);
        if(removeStaff.isEmpty()) throw new NoSuchElementException("Staff not found");

        staffIdRepository.delete(removeStaff.get());
        return BasicResponse.builder().status(true).result(true).code(200).message("Staff Id removed").build();
    }

    @Override
    public BasicResponse removeByRestaurant(Restaurant restaurant) {
        staffIdRepository.deleteAll(listStaffIdByRestaurant(restaurant));
        return BasicResponse.builder().status(true).result(true).code(200).message("Staff Id removed of given restaurant").build();
    }
}
