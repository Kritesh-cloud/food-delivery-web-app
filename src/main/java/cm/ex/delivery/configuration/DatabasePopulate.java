package cm.ex.delivery.configuration;

import cm.ex.delivery.entity.*;

import cm.ex.delivery.repository.*;
import cm.ex.delivery.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Configuration
public class DatabasePopulate {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private BrowseContentRepository browseContentRepository;

    @Autowired
    private IdHolderRepository idHolderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    CommandLineRunner initDatabase() {
        DatabasePopulate dbp = new DatabasePopulate();
        return args -> {

            dbp.populateCategory(categoryRepository, imageRepository);
            dbp.populateAuthority(authorityRepository);
            dbp.populateUser(userRepository, authorityRepository, passwordEncoder, basketRepository);
            dbp.populateRestaurant(restaurantRepository, userRepository, imageRepository);
            dbp.populateMenu(userRepository, restaurantRepository, menuCategoryRepository, menuItemRepository);
//            dbp.populateBasket(menuItemRepository, userRepository, basketRepository);
            dbp.populateBrowseContent(browseContentRepository, restaurantRepository, idHolderRepository);
        };
    }


    private void populateCategory(CategoryRepository categoryRepository, ImageRepository imageRepository) throws IOException {
        if (categoryRepository.count() == 0) {
            String path = "http://localhost:8080/image/";
            Image barbecue = saveImage("/category/barbecue", ".png", imageRepository);
            Image burger = saveImage("/category/burger", ".png", imageRepository);
            Image chinese = saveImage("/category/chinese", ".png", imageRepository);
            Image friedChicken = saveImage("/category/fried-chicken", ".png", imageRepository);
            Image friedRice = saveImage("/category/fried-rice", ".png", imageRepository);
            Image indian = saveImage("/category/indian", ".png", imageRepository);
            Image italian = saveImage("/category/italian", ".png", imageRepository);
            Image mexican = saveImage("/category/mexican", ".png", imageRepository);
            Image momo = saveImage("/category/momo", ".png", imageRepository);
            Image sandwich = saveImage("/category/sandwich", ".png", imageRepository);
            Image softDrink = saveImage("/category/soft-drink", ".png", imageRepository);
            Image sushi = saveImage("/category/sushi", ".png", imageRepository);

            categoryRepository.save(new Category("Barbecue", path + barbecue.getId()));
            categoryRepository.save(new Category("Burger", path + burger.getId()));
            categoryRepository.save(new Category("Chinese", path + chinese.getId()));
            categoryRepository.save(new Category("Fried Chicken", path + friedChicken.getId()));
            categoryRepository.save(new Category("Fried Rice", path + friedRice.getId()));
            categoryRepository.save(new Category("Indian", path + indian.getId()));
            categoryRepository.save(new Category("Italian", path + italian.getId()));
            categoryRepository.save(new Category("Mexican", path + mexican.getId()));
            categoryRepository.save(new Category("Momo", path + momo.getId()));
            categoryRepository.save(new Category("Sandwich", path + sandwich.getId()));
            categoryRepository.save(new Category("Soft Drink", path + softDrink.getId()));
            categoryRepository.save(new Category("Sushi", path + sushi.getId()));

            System.out.println("Category Repository has been populated with 12 initial categories.");
        }
    }

    private void populateAuthority(AuthorityRepository authorityRepository) {
        if (authorityRepository.count() == 0) {
            authorityRepository.save(new Authority("admin", 1, "admin"));
            authorityRepository.save(new Authority("moderator", 2, "admin"));
            authorityRepository.save(new Authority("delivery", 3, "moderator"));
            authorityRepository.save(new Authority("owner", 3, "moderator"));
            authorityRepository.save(new Authority("staff", 4, "owner"));
            authorityRepository.save(new Authority("user", 5, "auto"));

            System.out.println("Authority Repository has been populated with three(3) initial authorities.");
        }
    }

    private void populateUser(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, BasketRepository basketRepository) {
        if (userRepository.count() == 0) {
            Optional<Authority> authorityAdmin = authorityRepository.findByAuthority("admin");
            Optional<Authority> authorityModerator = authorityRepository.findByAuthority("moderator");
            Optional<Authority> authorityDelivery = authorityRepository.findByAuthority("delivery");
            Optional<Authority> authorityOwner = authorityRepository.findByAuthority("owner");
            Optional<Authority> authorityStaff = authorityRepository.findByAuthority("staff");
            Optional<Authority> authorityUser = authorityRepository.findByAuthority("user");
            Set<Authority> authoritySet = new HashSet<>();

            authoritySet.add(authorityAdmin.get());
            authoritySet.add(authorityModerator.get());
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("Aakrity Simkhada", "aakriti@gmail.com", passwordEncoder.encode("password"), "", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityModerator.get());
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("Moderator One", "moderator1@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Moderator Two", "moderator2@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Moderator Three", "moderator3@gmail.com", passwordEncoder.encode("password"), "", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityDelivery.get());
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("Delivery Guy One", "delivery1@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Delivery Guy Two", "delivery2@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Delivery Guy Three", "delivery3@gmail.com", passwordEncoder.encode("password"), "", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityOwner.get());
            authoritySet.add(authorityStaff.get());
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("Owner One", "owner1@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Two", "owner2@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Three", "owner3@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Four", "owner4@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Five", "owner5@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Six", "owner6@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Seven", "owner7@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Eight", "owner8@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Nine", "owner9@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Owner Ten", "owner10@gmail.com", passwordEncoder.encode("password"), "", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityStaff.get());
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("Staff One", "staff1@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Staff Two", "staff2@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("Staff Three", "staff3@gmail.com", passwordEncoder.encode("password"), "", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("User One", "user1@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Two", "user2@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Three", "user3@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Four", "user4@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Five", "user5@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Six", "user6@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Seven", "user7@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Eight", "user8@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Nine", "user9@gmail.com", passwordEncoder.encode("password"), "", authoritySet));
            userRepository.save(new User("User Ten", "user10@gmail.com", passwordEncoder.encode("password"), "", authoritySet));


            Set<MenuItem> menuItemSett = new HashSet<>();
            Optional<User> user1 = userRepository.findByEmail("user1@gmail.com");
            Optional<User> user2 = userRepository.findByEmail("user2@gmail.com");
            Optional<User> user3 = userRepository.findByEmail("user3@gmail.com");
            Optional<User> user4 = userRepository.findByEmail("user4@gmail.com");
            Optional<User> user5 = userRepository.findByEmail("user5@gmail.com");
            Optional<User> user6 = userRepository.findByEmail("user6@gmail.com");
            Optional<User> user7 = userRepository.findByEmail("user7@gmail.com");
            Optional<User> user8 = userRepository.findByEmail("user8@gmail.com");
            Optional<User> user9 = userRepository.findByEmail("user9@gmail.com");
            Optional<User> user10 = userRepository.findByEmail("user10@gmail.com");

//            System.out.println("menuItemSett: "+menuItemSett.size());
//            System.out.println("user: "+user.get());
//
//            Basket basket = basketRepository.save(new Basket(menuItemSett,user.get()));
//            System.out.println("basket : "+basket.toString());

//            Set<MenuItem> menuItemSet = new HashSet<>();
//
//            basketRepository.save(new Basket(menuItemSet, user1.get()));
//            basketRepository.save(new Basket(menuItemSet, user2.get()));
//            basketRepository.save(new Basket(menuItemSet, user3.get()));
//            basketRepository.save(new Basket(menuItemSet, user4.get()));
//            basketRepository.save(new Basket(menuItemSet, user5.get()));
//            basketRepository.save(new Basket(menuItemSet, user6.get()));
//            basketRepository.save(new Basket(menuItemSet, user7.get()));
//            basketRepository.save(new Basket(menuItemSet, user8.get()));
//            basketRepository.save(new Basket(menuItemSet, user9.get()));
//            basketRepository.save(new Basket(menuItemSet, user10.get()));

            System.out.println("User Repository has been populated with Ten(10) initial users.");
        }
    }

    public void populateRestaurant(RestaurantRepository restaurantRepository, UserRepository userRepository, ImageRepository imageRepository) throws IOException {
        if (restaurantRepository.count() == 0) {

            Optional<User> user1 = userRepository.findByEmail("owner1@gmail.com");
            Optional<User> user2 = userRepository.findByEmail("owner2@gmail.com");
            Optional<User> user3 = userRepository.findByEmail("owner3@gmail.com");
            Optional<User> user4 = userRepository.findByEmail("owner4@gmail.com");
            Optional<User> user5 = userRepository.findByEmail("owner5@gmail.com");
            Optional<User> user6 = userRepository.findByEmail("owner6@gmail.com");
            Optional<User> user7 = userRepository.findByEmail("owner7@gmail.com");
            Optional<User> user8 = userRepository.findByEmail("owner8@gmail.com");
            Optional<User> user9 = userRepository.findByEmail("owner9@gmail.com");
            Optional<User> user10 = userRepository.findByEmail("owner10@gmail.com");


            Set<User> staffSet = new HashSet<>();
            Set<Category> categorySet = new HashSet<>();
            Set<Image> imageGallerySet = new HashSet<>();
            Set<Image> imageGallerySet1 = new HashSet<>();
            Set<Image> imageGallerySet2 = new HashSet<>();
            Set<Image> imageGallerySet3 = new HashSet<>();
            Set<Image> imageGallerySet4 = new HashSet<>();
            String imagePath = "/home/kritesh-thapa/allfile/coding/backend/SpringApi/FoodDeliveryWebApp/src/main/java/cm/ex/delivery/configuration/images";
            String path = "http://localhost:8080/image/";

            Image icon1 = saveImage("/restaurant/icon/icon1", ".png", imageRepository);
            Image icon2 = saveImage("/restaurant/icon/icon2", ".png", imageRepository);
            Image icon3 = saveImage("/restaurant/icon/icon3", ".png", imageRepository);
            Image icon4 = saveImage("/restaurant/icon/icon4", ".png", imageRepository);

            Image background1 = saveImage("/restaurant/background/background1", ".jpg", imageRepository);
            Image background2 = saveImage("/restaurant/background/background2", ".jpg", imageRepository);
            Image background3 = saveImage("/restaurant/background/background3", ".jpg", imageRepository);
            Image background4 = saveImage("/restaurant/background/background4", ".jpg", imageRepository);

            Image gallery1a = saveImage("/restaurant/gallery/gallery1a", ".jpg", imageRepository);
            Image gallery1b = saveImage("/restaurant/gallery/gallery1b", ".jpg", imageRepository);
            Image gallery1c = saveImage("/restaurant/gallery/gallery1c", ".jpg", imageRepository);
            Image gallery1d = saveImage("/restaurant/gallery/gallery1d", ".jpg", imageRepository);
            imageGallerySet1.add(gallery1a);
            imageGallerySet1.add(gallery1b);
            imageGallerySet1.add(gallery1c);
            imageGallerySet1.add(gallery1d);

            Image gallery2a = saveImage("/restaurant/gallery/gallery2a", ".jpg", imageRepository);
            Image gallery2b = saveImage("/restaurant/gallery/gallery2b", ".jpg", imageRepository);
            Image gallery2c = saveImage("/restaurant/gallery/gallery2c", ".jpg", imageRepository);
            Image gallery2d = saveImage("/restaurant/gallery/gallery2d", ".jpg", imageRepository);
            imageGallerySet2.add(gallery2a);
            imageGallerySet2.add(gallery2b);
            imageGallerySet2.add(gallery2c);
            imageGallerySet2.add(gallery2d);

            Image gallery3a = saveImage("/restaurant/gallery/gallery3a", ".jpg", imageRepository);
            Image gallery3b = saveImage("/restaurant/gallery/gallery3b", ".jpg", imageRepository);
            Image gallery3c = saveImage("/restaurant/gallery/gallery3c", ".jpg", imageRepository);
            Image gallery3d = saveImage("/restaurant/gallery/gallery3d", ".jpg", imageRepository);
            imageGallerySet3.add(gallery3a);
            imageGallerySet3.add(gallery3b);
            imageGallerySet3.add(gallery3c);
            imageGallerySet3.add(gallery3d);

            Image gallery4a = saveImage("/restaurant/gallery/gallery4a", ".jpg", imageRepository);
            Image gallery4b = saveImage("/restaurant/gallery/gallery4b", ".jpg", imageRepository);
            Image gallery4c = saveImage("/restaurant/gallery/gallery4c", ".jpg", imageRepository);
            Image gallery4d = saveImage("/restaurant/gallery/gallery4d", ".jpg", imageRepository);
            imageGallerySet4.add(gallery4a);
            imageGallerySet4.add(gallery4b);
            imageGallerySet4.add(gallery4c);
            imageGallerySet4.add(gallery4d);

            Restaurant restaurant1 = new Restaurant(
                    "Pasta Paradise",
                    "Authentic Italian cuisine with a modern twist.",
                    "123 Main St, Cityville",
                    "+1-123-456-7890",
                    "info@pastaparadise.com",
                    path + background1.getId(),
                    "10:00",
                    "22:00",
                    path + icon1.getId(),
                    categorySet,
                    user1.get(),
//                    staffSet,
                    imageGallerySet1
            );

            Restaurant restaurant2 = new Restaurant(
                    "Dragon Wok",
                    "Traditional Chinese dishes and dim sum.",
                    "456 Elm St, Townsville",
                    "+1-234-567-8901",
                    "info@dragonwok.com",
                    path + background2.getId(),
                    "11:00",
                    "23:00",
                    path + icon2.getId(),
                    categorySet,
                    user2.get(),
//                    staffSet,
                    imageGallerySet2
            );

            Restaurant restaurant3 = new Restaurant(
                    "Spice Garden",
                    "Aromatic Indian flavors and spicy curries.",
                    "789 Oak St, Villageburg",
                    "+1-345-678-9012",
                    "info@spicegarden.com",
                    path + background3.getId(),
                    "12:00",
                    "22:30",
                    path + icon3.getId(),
                    categorySet,
                    user3.get(),
//                    staffSet,
                    imageGallerySet3
            );

            Restaurant restaurant4 = new Restaurant(
                    "Taco Fiesta",
                    "Mexican street food and tacos.",
                    "321 Pine St, Metrocity",
                    "+1-456-789-0123",
                    "info@tacofiesta.com",
                    path + background4.getId(),
                    "09:30",
                    "21:30",
                    path + icon4.getId(),
                    categorySet,
                    user4.get(),
//                    staffSet,
                    imageGallerySet4
            );

            Restaurant restaurant5 = new Restaurant(
                    "Sushi Sensations",
                    "Fresh sushi and sashimi prepared by expert chefs.",
                    "456 Ocean Ave, Seaside City",
                    "+1-987-654-3210",
                    "contact@sushisensations.com",
                    path + background2.getId(),
                    "11:00",
                    "23:00",
                    path + icon2.getId(),
                    categorySet,
                    user5.get(),
                    imageGallerySet2
            );

            Restaurant restaurant6 = new Restaurant(
                    "Taco Haven",
                    "Authentic Mexican street tacos with bold flavors.",
                    "789 Elm St, Tacosville",
                    "+1-234-567-8901",
                    "hello@tacohaven.com",
                    path + background3.getId(),
                    "09:00",
                    "21:00",
                    path + icon3.getId(),
                    categorySet,
                    user6.get(),
                    imageGallerySet3
            );

            Restaurant restaurant7 = new Restaurant(
                    "Burger Kingpin",
                    "Gourmet burgers made with locally sourced ingredients.",
                    "321 King St, Burgertown",
                    "+1-345-678-9012",
                    "info@burgerkingpin.com",
                    path + background4.getId(),
                    "12:00",
                    "00:00",
                    path + icon4.getId(),
                    categorySet,
                    user7.get(),
                    imageGallerySet4
            );

            Restaurant restaurant8 = new Restaurant(
                    "Vegan Delight",
                    "A plant-based paradise with delicious vegan dishes.",
                    "654 Green St, Vegtopia",
                    "+1-456-789-0123",
                    "support@vegandelight.com",
                    path + background1.getId(),
                    "08:00",
                    "20:00",
                    path + icon1.getId(),
                    categorySet,
                    user8.get(),
                    imageGallerySet1
            );

            Restaurant restaurant9 = new Restaurant(
                    "Pizza Perfection",
                    "Serving the finest pizzas with a variety of toppings.",
                    "123 Pizza Blvd, Cheesy Town",
                    "+1-567-890-1234",
                    "contact@pizzaperfection.com",
                    path + background1.getId(),
                    "10:30",
                    "23:30",
                    path + icon1.getId(),
                    categorySet,
                    user9.get(),
                    imageGallerySet1
            );


            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);
            restaurantRepository.save(restaurant3);
            restaurantRepository.save(restaurant4);
            restaurantRepository.save(restaurant5);
            restaurantRepository.save(restaurant6);
            restaurantRepository.save(restaurant7);
            restaurantRepository.save(restaurant8);
            restaurantRepository.save(restaurant9);

            System.out.println("Restaurant Repository has been populated with four(4) initial restaurants with images.");
        }
    }

    public void populateMenu(UserRepository userRepository, RestaurantRepository restaurantRepository, MenuCategoryRepository menuCategoryRepository, MenuItemRepository menuItemRepository) {

        if (menuCategoryRepository.count() == 0) {

            Optional<User> user1 = userRepository.findByEmail("owner1@gmail.com");
            Optional<Restaurant> restaurant1 = restaurantRepository.findByOwnerId(user1.get());

            MenuCategory menuCategory1a = menuCategoryRepository.save(new MenuCategory(0, "Momo", restaurant1.get()));
            MenuCategory menuCategory1b = menuCategoryRepository.save(new MenuCategory(0, "Pizza", restaurant1.get()));
            MenuCategory menuCategory1c = menuCategoryRepository.save(new MenuCategory(0, "Burger", restaurant1.get()));

            menuItemRepository.save(new MenuItem("Veg Momo", 120, menuCategory1a));
            menuItemRepository.save(new MenuItem("Buff Momo", 140, menuCategory1a));
            menuItemRepository.save(new MenuItem("Chicken Momo", 150, menuCategory1a));
            menuItemRepository.save(new MenuItem("Pork Momo", 180, menuCategory1a));

            menuItemRepository.save(new MenuItem("Veg Pizza", 220, menuCategory1b));
            menuItemRepository.save(new MenuItem("Buff Pizza", 240, menuCategory1b));
            menuItemRepository.save(new MenuItem("Chicken Pizza", 250, menuCategory1b));
            menuItemRepository.save(new MenuItem("Pork Pizza", 280, menuCategory1b));

            menuItemRepository.save(new MenuItem("Veg Burger", 220, menuCategory1c));
            menuItemRepository.save(new MenuItem("Buff Burger", 240, menuCategory1c));
            menuItemRepository.save(new MenuItem("Chicken Burger", 250, menuCategory1c));
            menuItemRepository.save(new MenuItem("Pork Burger", 280, menuCategory1c));


            Optional<User> user2 = userRepository.findByEmail("owner2@gmail.com");
            Optional<Restaurant> restaurant2 = restaurantRepository.findByOwnerId(user2.get());

            MenuCategory menuCategory2a = menuCategoryRepository.save(new MenuCategory(0, "Chow min", restaurant2.get()));
            MenuCategory menuCategory2b = menuCategoryRepository.save(new MenuCategory(0, "Naan", restaurant2.get()));
            MenuCategory menuCategory2c = menuCategoryRepository.save(new MenuCategory(0, "Burger", restaurant2.get()));

            menuItemRepository.save(new MenuItem("Veg Chow min", 120, menuCategory2a));
            menuItemRepository.save(new MenuItem("Buff Chow min", 140, menuCategory2a));
            menuItemRepository.save(new MenuItem("Chicken Chow min", 150, menuCategory2a));
            menuItemRepository.save(new MenuItem("Pork Chow min", 180, menuCategory2a));

            menuItemRepository.save(new MenuItem("Butte Naan", 220, menuCategory2b));
            menuItemRepository.save(new MenuItem("Buff Keema Naan", 240, menuCategory2b));
            menuItemRepository.save(new MenuItem("Chicken Keema Naan", 250, menuCategory2b));
            menuItemRepository.save(new MenuItem("Pork Keema Naan", 280, menuCategory2b));

            menuItemRepository.save(new MenuItem("Veg Burger", 220, menuCategory2c));
            menuItemRepository.save(new MenuItem("Buff Burger", 240, menuCategory2c));
            menuItemRepository.save(new MenuItem("Chicken Burger", 250, menuCategory2c));
            menuItemRepository.save(new MenuItem("Pork Burger", 280, menuCategory2c));


            Optional<User> user3 = userRepository.findByEmail("owner3@gmail.com");
            Optional<Restaurant> restaurant3 = restaurantRepository.findByOwnerId(user3.get());

            MenuCategory menuCategory3a = menuCategoryRepository.save(new MenuCategory(0, "Momo", restaurant3.get()));
            MenuCategory menuCategory3b = menuCategoryRepository.save(new MenuCategory(0, "Pizza", restaurant3.get()));
            MenuCategory menuCategory3c = menuCategoryRepository.save(new MenuCategory(0, "Burger", restaurant3.get()));

            menuItemRepository.save(new MenuItem("Veg Momo", 120, menuCategory3a));
            menuItemRepository.save(new MenuItem("Buff Momo", 140, menuCategory3a));
            menuItemRepository.save(new MenuItem("Chicken Momo", 150, menuCategory3a));
            menuItemRepository.save(new MenuItem("Pork Momo", 180, menuCategory3a));

            menuItemRepository.save(new MenuItem("Veg Pizza", 220, menuCategory3b));
            menuItemRepository.save(new MenuItem("Buff Pizza", 240, menuCategory3b));
            menuItemRepository.save(new MenuItem("Chicken Pizza", 250, menuCategory3b));
            menuItemRepository.save(new MenuItem("Pork Pizza", 280, menuCategory3b));

            menuItemRepository.save(new MenuItem("Veg Burger", 220, menuCategory3c));
            menuItemRepository.save(new MenuItem("Buff Burger", 240, menuCategory3c));
            menuItemRepository.save(new MenuItem("Chicken Burger", 250, menuCategory3c));
            menuItemRepository.save(new MenuItem("Pork Burger", 280, menuCategory3c));


            Optional<User> user4 = userRepository.findByEmail("owner4@gmail.com");
            Optional<Restaurant> restaurant4 = restaurantRepository.findByOwnerId(user4.get());

            MenuCategory menuCategory4a = menuCategoryRepository.save(new MenuCategory(0, "Chow min", restaurant4.get()));
            MenuCategory menuCategory4b = menuCategoryRepository.save(new MenuCategory(0, "Naan", restaurant4.get()));
            MenuCategory menuCategory4c = menuCategoryRepository.save(new MenuCategory(0, "Burger", restaurant4.get()));

            menuItemRepository.save(new MenuItem("Veg Chow min", 120, menuCategory4a));
            menuItemRepository.save(new MenuItem("Buff Chow min", 140, menuCategory4a));
            menuItemRepository.save(new MenuItem("Chicken Chow min", 150, menuCategory4a));
            menuItemRepository.save(new MenuItem("Pork Chow min", 180, menuCategory4a));

            menuItemRepository.save(new MenuItem("Butte Naan", 220, menuCategory4b));
            menuItemRepository.save(new MenuItem("Buff Keema Naan", 240, menuCategory4b));
            menuItemRepository.save(new MenuItem("Chicken Keema Naan", 250, menuCategory4b));
            menuItemRepository.save(new MenuItem("Pork Keema Naan", 280, menuCategory4b));

            menuItemRepository.save(new MenuItem("Veg Burger", 220, menuCategory4c));
            menuItemRepository.save(new MenuItem("Buff Burger", 240, menuCategory4c));
            menuItemRepository.save(new MenuItem("Chicken Burger", 250, menuCategory4c));
            menuItemRepository.save(new MenuItem("Pork Burger", 280, menuCategory4c));


            System.out.println("Menu Category and Item Repository has been populated with 4(users) x 3(category) x 4(items) = 48(items) initial menu items");
        }
    }

    public void populateBasket(MenuItemRepository menuItemRepository, UserRepository userRepository, BasketRepository basketRepository) {
        if (basketRepository.count() == 0) {

            Optional<MenuItem> menuItem1 = menuItemRepository.findById(Long.valueOf("1"));
            Optional<MenuItem> menuItem2 = menuItemRepository.findById(Long.valueOf("2"));
            Optional<MenuItem> menuItem3 = menuItemRepository.findById(Long.valueOf("3"));
            Optional<MenuItem> menuItem4 = menuItemRepository.findById(Long.valueOf("4"));
            Optional<MenuItem> menuItem5 = menuItemRepository.findById(Long.valueOf("5"));
            Optional<MenuItem> menuItem6 = menuItemRepository.findById(Long.valueOf("6"));
            Optional<MenuItem> menuItem7 = menuItemRepository.findById(Long.valueOf("7"));
            Optional<MenuItem> menuItem8 = menuItemRepository.findById(Long.valueOf("8"));
            Optional<MenuItem> menuItem9 = menuItemRepository.findById(Long.valueOf("9"));
            Optional<MenuItem> menuItem10 = menuItemRepository.findById(Long.valueOf("10"));

            Set<MenuItem> menuItemSet1 = new HashSet<>();
            menuItemSet1.add(menuItem1.get());
            menuItemSet1.add(menuItem3.get());
            menuItemSet1.add(menuItem5.get());

            Set<MenuItem> menuItemSet2 = new HashSet<>();
            menuItemSet2.add(menuItem1.get());
            menuItemSet2.add(menuItem4.get());
            menuItemSet2.add(menuItem8.get());

            Set<MenuItem> menuItemSet3 = new HashSet<>();
            menuItemSet3.add(menuItem2.get());
            menuItemSet3.add(menuItem6.get());
            menuItemSet3.add(menuItem8.get());

            Set<MenuItem> menuItemSet4 = new HashSet<>();
            menuItemSet4.add(menuItem3.get());
            menuItemSet4.add(menuItem6.get());
            menuItemSet4.add(menuItem9.get());


            Optional<User> user1 = userRepository.findByEmail("user1@gmail.com");
            Optional<User> user2 = userRepository.findByEmail("user2@gmail.com");
            Optional<User> user3 = userRepository.findByEmail("user3@gmail.com");
            Optional<User> user4 = userRepository.findByEmail("user4@gmail.com");

            System.out.println("user1: " + user1.get().toString());
            System.out.println("user2: " + user2.get().toString());

            Optional<Basket> basket1 = basketRepository.findByOwnerId(user1.get());
            Optional<Basket> basket2 = basketRepository.findByOwnerId(user2.get());
            Optional<Basket> basket3 = basketRepository.findByOwnerId(user3.get());
            Optional<Basket> basket4 = basketRepository.findByOwnerId(user4.get());

            System.out.println("basket1: " + basket1.get().toString());
            System.out.println("basket2: " + basket2.get().toString());

//            basket1.get().setMenuItemSet(menuItemSet1);
//            basket2.get().setMenuItemSet(menuItemSet2);
//            basket3.get().setMenuItemSet(menuItemSet3);
//            basket4.get().setMenuItemSet(menuItemSet4);

            basketRepository.save(basket1.get());
            basketRepository.save(basket2.get());
            basketRepository.save(basket3.get());
            basketRepository.save(basket4.get());


            System.out.println("Basket Repository has been populated with four(4) data.");

        }
    }

    private void populateBrowseContent(BrowseContentRepository browseContentRepository, RestaurantRepository restaurantRepository, IdHolderRepository idHolderRepository) {
        if (browseContentRepository.count() == 0) {
            BrowseContent browseContentR1 = browseContentRepository.save(new BrowseContent("Restaurant List One", "restaurant"));
            BrowseContent browseContentR2 = browseContentRepository.save(new BrowseContent("Restaurant List Two", "restaurant"));
            BrowseContent browseContentR3 = browseContentRepository.save(new BrowseContent("Restaurant List Three", "restaurant"));
            BrowseContent browseContentR4 = browseContentRepository.save(new BrowseContent("Restaurant List Four", "restaurant"));
            BrowseContent browseContentR5 = browseContentRepository.save(new BrowseContent("Restaurant List Five", "restaurant"));
            BrowseContent browseContentR6 = browseContentRepository.save(new BrowseContent("Restaurant List Six", "restaurant"));
            BrowseContent browseContentR7 = browseContentRepository.save(new BrowseContent("Restaurant List Seven", "restaurant"));
            BrowseContent browseContentR8 = browseContentRepository.save(new BrowseContent("Restaurant List Eight", "restaurant"));


            List<Restaurant> restaurantList = restaurantRepository.findAll();

            idHolderRepository.save(new IdHolder(restaurantList.get(0).getId().toString(), "restaurant", browseContentR1));
            idHolderRepository.save(new IdHolder(restaurantList.get(1).getId().toString(), "restaurant", browseContentR1));
            idHolderRepository.save(new IdHolder(restaurantList.get(2).getId().toString(), "restaurant", browseContentR1));
            idHolderRepository.save(new IdHolder(restaurantList.get(3).getId().toString(), "restaurant", browseContentR1));
            idHolderRepository.save(new IdHolder(restaurantList.get(4).getId().toString(), "restaurant", browseContentR1));
            idHolderRepository.save(new IdHolder(restaurantList.get(5).getId().toString(), "restaurant", browseContentR1));
            idHolderRepository.save(new IdHolder(restaurantList.get(6).getId().toString(), "restaurant", browseContentR1));

            idHolderRepository.save(new IdHolder(restaurantList.get(2).getId().toString(), "restaurant", browseContentR2));
            idHolderRepository.save(new IdHolder(restaurantList.get(3).getId().toString(), "restaurant", browseContentR2));
            idHolderRepository.save(new IdHolder(restaurantList.get(4).getId().toString(), "restaurant", browseContentR2));
            idHolderRepository.save(new IdHolder(restaurantList.get(5).getId().toString(), "restaurant", browseContentR2));
            idHolderRepository.save(new IdHolder(restaurantList.get(6).getId().toString(), "restaurant", browseContentR2));

            idHolderRepository.save(new IdHolder(restaurantList.get(3).getId().toString(), "restaurant", browseContentR3));
            idHolderRepository.save(new IdHolder(restaurantList.get(1).getId().toString(), "restaurant", browseContentR3));
            idHolderRepository.save(new IdHolder(restaurantList.get(7).getId().toString(), "restaurant", browseContentR3));
            idHolderRepository.save(new IdHolder(restaurantList.get(8).getId().toString(), "restaurant", browseContentR3));
            idHolderRepository.save(new IdHolder(restaurantList.get(0).getId().toString(), "restaurant", browseContentR3));

            idHolderRepository.save(new IdHolder(restaurantList.get(0).getId().toString(), "restaurant", browseContentR4));
            idHolderRepository.save(new IdHolder(restaurantList.get(2).getId().toString(), "restaurant", browseContentR4));
            idHolderRepository.save(new IdHolder(restaurantList.get(4).getId().toString(), "restaurant", browseContentR4));
            idHolderRepository.save(new IdHolder(restaurantList.get(6).getId().toString(), "restaurant", browseContentR4));
            idHolderRepository.save(new IdHolder(restaurantList.get(8).getId().toString(), "restaurant", browseContentR4));

            System.out.println("restaurant browse content (3) done");
        }
    }

    public Image saveImage(String imageName, String ext, ImageRepository imageRepository) throws IOException {
        String deviceFilePath = "/home/kritesh-thapa/allfile/coding/backend/SpringApi/FoodDeliveryWebApp/src/main/java/cm/ex/delivery/configuration/images";
        byte[] imageData = Files.readAllBytes(Paths.get(deviceFilePath + imageName + ext));
        String imgFile = Base64.getEncoder().encodeToString(imageData);
        Image image = new Image(imageName, imgFile, ext);
        return imageRepository.save(image);
    }

}

