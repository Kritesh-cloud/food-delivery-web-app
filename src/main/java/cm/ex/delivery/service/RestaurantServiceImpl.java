package cm.ex.delivery.service;

import cm.ex.delivery.entity.*;
import cm.ex.delivery.request.UpdateRestaurant;
import cm.ex.delivery.repository.*;
import cm.ex.delivery.response.*;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.RestaurantService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MenuCategoryServiceImpl menuCategoryService;

    @Autowired
    private MenuItemServiceImpl menuItemService;

    @Autowired
    private BrowseContentServiceImpl browseContentService;

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
        userRepository.save(userAuth.getUser());

        String path = "http://localhost:8080/image/";
        try {
            Image iconImage = imageService.addImage(icon);
            Image backgroundImage = imageService.addImage(icon);
            Set<Image> imageSet = new HashSet<>();
            if(gallery!=null) {
                for (MultipartFile galleryImage : gallery) {
                    Image savedGalleryImage = imageService.addImage(galleryImage);
                    imageSet.add(savedGalleryImage);
                }
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
        if (restaurant.isEmpty()) throw new NoSuchElementException("restaurant not found");
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

        return restaurantList.isEmpty() ? List.of() : restaurantList;
    }

    @Override
    public List<RestaurantResponse> listAllRestaurantDetails() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        System.out.println("restaurantList count: "+restaurantList.size());
        System.out.println("Test l 0");
        if (restaurantList.isEmpty()) {
            return List.of(); // Return empty list immediately if no restaurants found
        }
        System.out.println("Test l 1");
        List<RestaurantResponse> restaurantResponseList = restaurantList.stream().map(restaurant -> {
            // Map restaurant to RestaurantResponse
            RestaurantResponse restaurantResponse = modelMapper.map(restaurant, RestaurantResponse.class);
            System.out.println("Test l 2");
            // Set owner
            restaurantResponse.setOwner(userToSendableUser(restaurant.getOwnerId()));

            System.out.println("Test l 3");
            // Set image gallery
            restaurantResponse.setImageGalleryList(
                    restaurant.getImageGallerySet().stream()
                            .map(image -> image.getId().toString())
                            .toList()
            );

            System.out.println("Test l 4");
            // Set menu categories with items
            List<MenuCategoryResponse> menuCategoryResponses = menuCategoryService
                    .listMenuCategoryByOrder(restaurant.getId().toString())
                    .stream()
                    .peek(menuCategoryResponse -> {
                        // Set menu items for each category
                        System.out.println("Test l 4 1");
                        List<MenuItemResponse> menuItemResponses = menuItemService.listMenuItemByOrder(
                                menuCategoryResponse.getName(),
                                menuCategoryResponse.getRestaurantId()
                        );
                        menuCategoryResponse.setMenuItemResponseList(menuItemResponses);
                        System.out.println("Test l 4 2");
                    })
                    .toList();
            System.out.println("Test l 5");
            restaurantResponse.setMenuCategoryResponses(menuCategoryResponses);
            System.out.println("Test l 6");
            System.out.println();
            return restaurantResponse;
        }).toList();
        System.out.println("Test l 3");
        return restaurantResponseList;
    }

    @Override
    public List<ShortRestaurantResponse> listAllShortRestaurantDetails() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        if (restaurantList.isEmpty()) {
            return List.of(); // Return empty list immediately if no restaurants found
        }

        return restaurantList.stream().map(restaurant -> {
            return modelMapper.map(restaurant, ShortRestaurantResponse.class);
        }).toList();
    }

    @Override
    public RestaurantResponse getRestaurantDetailsById(String id) {

        Restaurant restaurant = getRestaurantById(id);

        // Map restaurant to RestaurantResponse
        RestaurantResponse restaurantResponse = modelMapper.map(restaurant, RestaurantResponse.class);

        // Set owner
        restaurantResponse.setOwner(userToSendableUser(restaurant.getOwnerId()));

        // Set image gallery
        restaurantResponse.setImageGalleryList(
                restaurant.getImageGallerySet().stream()
                        .map(image -> image.getId().toString())
                        .toList()
        );

        // Set menu categories with items
        List<MenuCategoryResponse> menuCategoryResponses = menuCategoryService
                .listMenuCategoryByOrder(restaurant.getId().toString())
                .stream()
                .peek(menuCategoryResponse -> {
                    // Set menu items for each category
                    List<MenuItemResponse> menuItemResponses = menuItemService.listMenuItemByOrder(
                            menuCategoryResponse.getName(),
                            menuCategoryResponse.getRestaurantId()
                    );
                    menuCategoryResponse.setMenuItemResponseList(menuItemResponses);
                })
                .toList();

        restaurantResponse.setMenuCategoryResponses(menuCategoryResponses);
        return restaurantResponse;
    }

    @Override
    public List<RestaurantResponse> listAllRestaurantDetailsByBrowseList(String browseId) {
        int browseIdInt;

        // Parse browseId into integer safely
        try {
            browseIdInt = Integer.parseInt(browseId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid browseId format: " + browseId, e);
        }

        System.out.println("browseIdInt: "+browseIdInt);
        System.out.println("Test 0");
        // Fetch restaurant and browse content details
        try {
            List<RestaurantResponse> restaurantResponseList = listAllRestaurantDetails();
            System.out.println("Test 00");
            List<BrowseContentResponse> browseContentResponseList = browseContentService.listAllBrowseContentByOrder();

            // Check if browseIdInt is within bounds
            System.out.println("Test 1");
            if (browseIdInt < 0 || browseIdInt >= browseContentResponseList.size()) {
                throw new IndexOutOfBoundsException("browseId index out of range: " + browseIdInt);
            }

            // Fetch the list of restaurant IDs from the browse content
            System.out.println("Test 2");
            List<String> restaurantIds = browseContentResponseList.get(browseIdInt).getIds();

            System.out.println("restaurantIds:");
            restaurantIds.forEach(System.out::println);

            System.out.println("Test 3");
            // Filter restaurant responses based on IDs
            List<RestaurantResponse> newRestaurantResponseList = restaurantResponseList.stream()
                    .filter(restaurantResponse -> restaurantIds.contains(restaurantResponse.getId().toString())) // Match IDs
                    .toList();

            System.out.println("Test 4");
            for (RestaurantResponse restaurant : newRestaurantResponseList) {
                System.out.println(restaurant.toString());
            }

            return newRestaurantResponseList;
        }
        catch (Exception e){
            System.out.println("Exc "+e);
        }

        return null;
    }


    @Override
    public List<ShortRestaurantResponse> listAllShortRestaurantDetailsByBrowseList(String browseId) {

        int browseIdInt;

        // Parse browseId into integer safely
        try {
            browseIdInt = Integer.parseInt(browseId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid browseId format: " + browseId, e);
        }

        // Fetch restaurant and browse content details
        List<ShortRestaurantResponse> shortRestaurantResponseList = restaurantRepository.findAll().stream().map(
                restaurant -> {
                    return modelMapper.map(restaurant, ShortRestaurantResponse.class);
                }
        ).toList();
        List<BrowseContentResponse> browseContentResponseList = browseContentService.listAllBrowseContentByOrder();

        // Check if browseIdInt is within bounds
        if (browseIdInt < 0 || browseIdInt >= browseContentResponseList.size()) {
            throw new IndexOutOfBoundsException("browseId index out of range: " + browseIdInt);
        }

        // Fetch the list of restaurant IDs from the browse content
        List<String> restaurantIds = browseContentResponseList.get(browseIdInt).getIds();

        // Filter restaurant responses based on IDs
        return shortRestaurantResponseList.stream()
                .filter(restaurantResponse -> restaurantIds.contains(restaurantResponse.getId().toString())) // Match IDs
                .toList();
    }

    @Override
    public List<MediumRestaurantResponse> listAllMediumRestaurantDetailsByBrowseList(String browseId) {
        return List.of();
    }

    @Override
    public List<RestaurantResponse> reverseListAllRestaurantDetailsByBrowseList(String browseId) {
        int browseIdInt;

        // Parse browseId into integer safely
        try {
            browseIdInt = Integer.parseInt(browseId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid browseId format: " + browseId, e);
        }

        // Fetch restaurant and browse content details
        List<RestaurantResponse> restaurantResponseList = listAllRestaurantDetails();
        List<BrowseContentResponse> browseContentResponseList = browseContentService.listAllBrowseContentByOrder();

        // Check if browseIdInt is within bounds
        if (browseIdInt < 0 || browseIdInt >= browseContentResponseList.size()) {
            throw new IndexOutOfBoundsException("browseId index out of range: " + browseIdInt);
        }

        // Fetch the list of restaurant IDs from the browse content
        List<String> restaurantIds = browseContentResponseList.get(browseIdInt).getIds();

        // Filter restaurant responses based on IDs
        return restaurantResponseList.stream()
                .filter(restaurantResponse -> !restaurantIds.contains(restaurantResponse.getId().toString())) // Match IDs
                .toList();
    }

    @Override
    public List<MediumRestaurantResponse> reverseListAllMeduimRestaurantDetailsByBrowseList(String browseId) {
        return List.of();
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


        for (Authority a : user.getAuthoritySet()) {
            System.out.println("filtered user authority : " + a.getAuthority());
        }

        user.setAuthoritySet(Set.of());
        System.out.println("cleared");
        for (Authority a : user.getAuthoritySet()) {
            System.out.println("user authority : " + a.getAuthority());
        }
        System.out.println("re");
        user.setAuthoritySet(authoritySet);
        for (Authority a : user.getAuthoritySet()) {
            System.out.println("user authority : " + a.getAuthority());
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
//        if (validateList.contains("gallery")) {
//            for (MultipartFile file : gallery) {
//                if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty())
//                    throw new IllegalArgumentException("Invalid restaurant info input. Invalid gallery images.");
//            }
//
//        }
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

    private User userToSendableUser(User user) {
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
    }

    private List<User> userListToSendableUserList(List<User> userList) {
        List<User> newUserList = userList.stream().map(user -> {
            return userToSendableUser(user);
        }).toList();
        return newUserList.isEmpty() ? List.of() : newUserList;
    }
}
