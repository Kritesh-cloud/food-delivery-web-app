package cm.ex.delivery.service;

import cm.ex.delivery.entity.Image;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.entity.restaurant.UpdateRestaurant;
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

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ImageServiceImpl imageService;

    @Transactional
    @Override
    public BasicResponse addRestaurant(Restaurant restaurantInfo, MultipartFile icon, MultipartFile background, MultipartFile... gallery) {
        List<String> validateList = new ArrayList<>(List.of("new restaurant", "icon image", "background image", "gallery"));
        validateRestaurant(restaurantInfo, new UpdateRestaurant(), validateList, icon, background, gallery);

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
    public Restaurant getRestaurantById(String id) {
        return restaurantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
    }

    @Override
    public List<Restaurant> listAllRestaurant() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return restaurantList.isEmpty() ? List.of() : restaurantList;
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

    private Restaurant getRestaurant() {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userAuth.getUser());
        if (restaurant.isEmpty()) throw new NoSuchElementException("Restaurant not found");
        return restaurant.get();
    }
}
