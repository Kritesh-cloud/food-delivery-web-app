package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.AuthorityChange;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;
import java.util.Optional;

public interface AuthorityChangeService {

    public BasicResponse requestAuthorityChange(String newAuthority, String restaurantId);

    public List<AuthorityChange> listAllAuthorityChangeRequest();

    public List<AuthorityChange> listAllAuthorityChangeRequestByRestaurant(String restaurantId);

    BasicResponse authorityChangeUpdate(String userId, String newAuthority, boolean change);



}
/*

list all authority change request
authority change update (boolean change)
list by restaurant

*/