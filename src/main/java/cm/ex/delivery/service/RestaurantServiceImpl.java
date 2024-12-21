package cm.ex.delivery.service;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.entity.Image;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.request.UpdateRestaurant;
import cm.ex.delivery.repository.*;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.RestaurantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public BasicResponse addRestaurant(Restaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery) {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        List<String> validateList = new ArrayList<>(List.of("new restaurant", "icon image", "background image", "gallery"));
        validateRestaurant(restaurantInfo, new UpdateRestaurant(), validateList, icon, background, gallery);

        Optional<Authority> newAuthorityOwner = authorityRepository.findByAuthority("owner");
        Optional<Authority> newAuthorityStaff = authorityRepository.findByAuthority("staff");

        Set<Authority> authoritySet = userAuth.getUser().getAuthoritySet();
        authoritySet.add(newAuthorityOwner.get());
        authoritySet.add(newAuthorityStaff.get());
        userAuth.getUser().setAuthoritySet(authoritySet);

        String path = "http://localhost:8080/image/";
        try {
            Image iconImage = imageService.addImage(icon);
            Image backgroundImage = imageService.addImage(icon);
            Set<Image> imageSet = new HashSet<>();
            for (MultipartFile galleryImage : gallery) {
                Image savedGalleryImage = imageService.addImage(galleryImage);
                imageSet.add(savedGalleryImage);
            }
            restaurantInfo.setId(null);
            restaurantInfo.setIconUrl(path + iconImage.getId());
            restaurantInfo.setBackgroundUrl(path + backgroundImage.getId());
            restaurantInfo.setImageGallerySet(imageSet);
            restaurantInfo.setOwnerId(userAuth.getUser());

            restaurantRepository.save(restaurantInfo);
            return BasicResponse.builder().status(true).code(200).message("Restaurant account created successfully").build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            return BasicResponse.builder().status(false).code(409).message("Unable to create restaurant account").build();
        }
    }

    @Override
    public Restaurant getRestaurantByOwnerId(User ownerId) {
        return getRestaurant();
    }

    @Override
    public Restaurant getOwnerRestaurant() {
        return getRestaurant();
    }


    @Override
    public Restaurant getRestaurantById(String id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(UUID.fromString(id));
        if(restaurant.isEmpty()) throw new NoSuchElementException("restaurant not found");
        List<User> userList = new ArrayList<>();
        userList.add(restaurant.get().getOwnerId());
        List<User> newUserList = userListToSendableUserList(userList);
        restaurant.get().setOwnerId(newUserList.get(0));

        return restaurant.get();
    }

    @Override
    public List<Restaurant> listAllRestaurant() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        List<Restaurant> newRestaurantList = restaurantList.stream().map(
                restaurant -> {
                    List<User> userList = new ArrayList<>();
                    userList.add(restaurant.getOwnerId());
                    List<User> newUserList = userListToSendableUserList(userList);
                    restaurant.setOwnerId(newUserList.get(0));
                    return restaurant;
                }
        ).toList();

        return newRestaurantList.isEmpty() ? List.of() : newRestaurantList;
    }

    @Override
    public BasicResponse updateRestaurant(UpdateRestaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery) {
        Optional<Restaurant> updateRestaurant = restaurantRepository.findById(restaurantInfo.getId());

        if (updateRestaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");

        List<String> validateList = new ArrayList<>(List.of("update restaurant"));

        if (restaurantInfo.isIconChanged()) {
            validateList.add("icon image");
        }
        if (restaurantInfo.isBackgroundChanged()) {
            validateList.add("background image");
        }
        if (restaurantInfo.isNewGalleryImageAdded()) {
            validateList.add("gallery");
        }

        validateRestaurant(new Restaurant(), restaurantInfo, validateList, icon, background, gallery);
        updateRestaurant.get().setName(restaurantInfo.getName());
        updateRestaurant.get().setDescription(restaurantInfo.getDescription());
        updateRestaurant.get().setAddress(restaurantInfo.getAddress());
        updateRestaurant.get().setContactNumber(restaurantInfo.getContactNumber());
        updateRestaurant.get().setEmail(restaurantInfo.getEmail());
        updateRestaurant.get().setOpeningTime(restaurantInfo.getOpeningTime());
        updateRestaurant.get().setClosingTime(restaurantInfo.getClosingTime());

        String path = "http://localhost:8080/image/";
        try {
            if (restaurantInfo.isIconChanged()) {
                Image iconImage = imageService.addImage(icon);
                updateRestaurant.get().setIconUrl(path + iconImage.getId());
            }
            if (restaurantInfo.isBackgroundChanged()) {
                Image backgroundImage = imageService.addImage(icon);
                updateRestaurant.get().setBackgroundUrl(path + backgroundImage.getId());
            }
            Set<Image> imageSet = new HashSet<>();
            if (restaurantInfo.isNewGalleryImageAdded()) {
                for (MultipartFile galleryImage : gallery) {
                    Image savedGalleryImage = imageService.addImage(galleryImage);
                    imageSet.add(savedGalleryImage);
                }
                updateRestaurant.get().setImageGallerySet(imageSet);
            }
            restaurantRepository.save(updateRestaurant.get());
            return BasicResponse.builder().status(true).code(200).message("Restaurant account updated successfully").build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            return BasicResponse.builder().status(false).code(409).message("Unable to updated restaurant account").build();
        }

    }

    @Override
    public BasicResponse removeRestaurant() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        User user = userAuth.getUser();

        Set<Authority> authoritySet = new HashSet<>();
        Optional<Authority> authorityUser = authorityRepository.findByAuthority("user");
        authoritySet.add(authorityUser.get());



        for(Authority a : user.getAuthoritySet()){
            System.out.println("filtered user authority : "+a.getAuthority());
        }

        user.setAuthoritySet(Set.of());
        System.out.println("cleared");
        for(Authority a : user.getAuthoritySet()){
            System.out.println("user authority : "+a.getAuthority());
        }
        System.out.println("re");
        user.setAuthoritySet(authoritySet);
        for(Authority a : user.getAuthoritySet()){
            System.out.println("user authority : "+a.getAuthority());
        }
        userRepository.save(user);
        restaurantRepository.delete(getRestaurant());
        return BasicResponse.builder().status(true).code(200).message("Restaurant account deleted successfully").build();
    }

    private void validateRestaurant(Restaurant restaurantInfo, UpdateRestaurant updateRestaurantInfo, List<String> validateList, MultipartFile icon, MultipartFile background, MultipartFile... gallery) {
        if (validateList.contains("new restaurant")) {
            if (restaurantInfo.getName().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid name.");

            if (restaurantInfo.getDescription().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid description.");

            if (restaurantInfo.getAddress().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid address.");

            if (restaurantInfo.getContactNumber().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid contact number.");

            if (restaurantInfo.getEmail().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid email.");
        }
        if (validateList.contains("updated restaurant")) {
            if (updateRestaurantInfo.getName().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid name.");

            if (updateRestaurantInfo.getDescription().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid description.");

            if (updateRestaurantInfo.getAddress().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid address.");

            if (updateRestaurantInfo.getContactNumber().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid contact number.");

            if (updateRestaurantInfo.getEmail().isBlank())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid email.");
        }
        if (validateList.contains("icon image")) {
            if (Objects.requireNonNull(icon.getOriginalFilename()).isEmpty())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid icon image.");
        }
        if (validateList.contains("background image")) {
            if (Objects.requireNonNull(background.getOriginalFilename()).isEmpty())
                throw new IllegalArgumentException("Invalid restaurant info input. Invalid background image.");
        }
        if (validateList.contains("gallery")) {
            for (MultipartFile file : gallery) {
                if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty())
                    throw new IllegalArgumentException("Invalid restaurant info input. Invalid gallery images.");
            }

        }
    }

    public Restaurant getRestaurant() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");

        List<User> userList = new ArrayList<>();
        userList.add(restaurant.get().getOwnerId());
        List<User> newUserList = userListToSendableUserList(userList);
        restaurant.get().setOwnerId(newUserList.get(0));

        return restaurant.get();
    }

    private List<User> userListToSendableUserList(List<User> userList){
        List<User> newUserList = userList.stream().map(user -> {
            Set<Authority> authoritySet = user.getAuthoritySet();
            Set<Authority> newAuthoritySet = authoritySet.stream().map(
                    aut -> {
                        return Authority.builder().authority(aut.getAuthority()).level(aut.getLevel()).build();
                    }).collect(Collectors.toSet());
            return User.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .profileUrl(user.getProfileUrl())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .authoritySet(newAuthoritySet)
                    .build();
        }).toList();
        return newUserList.isEmpty() ? List.of() : newUserList;
    }
}
