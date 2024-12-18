package cm.ex.delivery.service;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.entity.AuthorityChange;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.AuthorityChangeRepository;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.repository.RestaurantRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.AuthorityChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class AuthorityChangeServiceImpl implements AuthorityChangeService {

    @Autowired
    private AuthorityChangeRepository authorityChangeRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AuthorityServiceImpl authorityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserServiceImpl userService;


    @Override
    public BasicResponse requestAuthorityChange(String newAuthority, String restaurantId){
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Authority> authorityValidate = authorityRepository.findByAuthority(newAuthority);
        if(authorityValidate.isEmpty()) throw new NoSuchElementException("Authority not found");

        Optional<Restaurant> restaurant;
        AuthorityChange authorityChange = new AuthorityChange();
        authorityChange.setNewAuthority(newAuthority);
        authorityChange.setRestaurantId(null);
        authorityChange.setUserId(userAuth.getUser());

        if(!restaurantId.equalsIgnoreCase("null")){
            restaurant = restaurantRepository.findById(UUID.fromString(restaurantId));
            if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");
            authorityChange.setRestaurantId(restaurant.get());
        }

        return BasicResponse.builder().status(true).result(true).code(200).message("Authority change requested").build();
    }

    @Override
    public List<AuthorityChange> listAllAuthorityChangeRequest() {
        List<AuthorityChange> authorityChangeList = authorityChangeRepository.findAll();
        return authorityChangeList.isEmpty() ? List.of() : authorityChangeList;
    }

    @Override
    public List<AuthorityChange> listAllAuthorityChangeRequestByRestaurant(String restaurantId) {
        List<AuthorityChange> authorityChangeList = authorityChangeRepository.findByRestaurantId(getRestaurant());
        return authorityChangeList.isEmpty() ? List.of() : authorityChangeList;
    }

    @Override
    public BasicResponse authorityChangeUpdate(String userId, String newAuthority, boolean change) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if(user.isEmpty()) throw new NoSuchElementException("User not found");

        Optional<AuthorityChange> authorityChange = authorityChangeRepository.getAuthorityChangeByUserId(user.get());
        if(authorityChange.isEmpty()) throw new NoSuchElementException("User authority request not found");

        if(!change){
            authorityChangeRepository.delete(authorityChange.get());
            return BasicResponse.builder().status(true).result(false).code(200).message("Authority change request denied").build();
        }
        Optional<Authority> authorityValidate = authorityRepository.findByAuthority(newAuthority);
        if(authorityValidate.isEmpty()) throw new NoSuchElementException("Authority not found");

        Set<Authority> authorityList = user.get().getAuthoritySet();
        authorityList.add(authorityValidate.get());

        user.get().setAuthoritySet(authorityList);
        userRepository.save(user.get());

        return BasicResponse.builder().status(true).result(true).code(200).message("Authority change request accepted").build();
    }

    private Restaurant getRestaurant() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");
        return restaurant.get();
    }
}
