package cm.ex.delivery.configuration;

import cm.ex.delivery.entity.*;

import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.repository.RestaurantRepository;
import cm.ex.delivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Bean
    CommandLineRunner initDatabase() {
        DatabasePopulate dbp = new DatabasePopulate();
        return args -> {

            dbp.populateAuthority(authorityRepository);
            dbp.populateUser(userRepository, authorityRepository, passwordEncoder);
            dbp.populateRestaurant(restaurantRepository, userRepository);

        };
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

    private void populateUser(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
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

            System.out.println("User Repository has been populated with Ten(10) initial users.");
        }
    }

    public void populateRestaurant(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        if (restaurantRepository.count() == 0) {

            Optional<User> user1 = userRepository.findByEmail("owner1@gmail.com");
            Optional<User> user2 = userRepository.findByEmail("owner2@gmail.com");
            Optional<User> user3 = userRepository.findByEmail("owner3@gmail.com");
            Optional<User> user4 = userRepository.findByEmail("owner4@gmail.com");


            Set<User> staffSet = new HashSet<>();
            Set<Category> categorySet = new HashSet<>();
            Set<Image> imageGallerySet = new HashSet<>();

            Restaurant restaurant1 = new Restaurant(
                    "Pasta Paradise",
                    "Authentic Italian cuisine with a modern twist.",
                    "123 Main St, Cityville",
                    "+1-123-456-7890",
                    "info@pastaparadise.com",
                    "https://example.com/background1.jpg",
                    "10:00",
                    "22:00",
                    "https://example.com/icons/pasta.png",
                    categorySet,
                    user1.get(),
                    staffSet,
                    imageGallerySet
            );

            Restaurant restaurant2 = new Restaurant(
                    "Dragon Wok",
                    "Traditional Chinese dishes and dim sum.",
                    "456 Elm St, Townsville",
                    "+1-234-567-8901",
                    "info@dragonwok.com",
                    "https://example.com/background2.jpg",
                    "11:00",
                    "23:00",
                    "https://example.com/icons/wok.png",
                    categorySet,
                    user2.get(),
                    staffSet,
                    imageGallerySet
            );

            Restaurant restaurant3 = new Restaurant(
                    "Spice Garden",
                    "Aromatic Indian flavors and spicy curries.",
                    "789 Oak St, Villageburg",
                    "+1-345-678-9012",
                    "info@spicegarden.com",
                    "https://example.com/background3.jpg",
                    "12:00",
                    "22:30",
                    "https://example.com/icons/spice.png",
                    categorySet,
                    user3.get(),
                    staffSet,
                    imageGallerySet
            );

            Restaurant restaurant4 = new Restaurant(
                    "Taco Fiesta",
                    "Mexican street food and tacos.",
                    "321 Pine St, Metrocity",
                    "+1-456-789-0123",
                    "info@tacofiesta.com",
                    "https://example.com/background4.jpg",
                    "09:30",
                    "21:30",
                    "https://example.com/icons/taco.png",
                    categorySet,
                    user4.get(),
                    staffSet,
                    imageGallerySet
            );

            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);
            restaurantRepository.save(restaurant3);
            restaurantRepository.save(restaurant4);

            System.out.println("Restaurant Repository has been populated with four(4) initial restaurants.");
        }
    }

}

