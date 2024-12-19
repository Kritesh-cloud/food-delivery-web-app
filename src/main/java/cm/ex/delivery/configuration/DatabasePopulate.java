package cm.ex.delivery.configuration;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.entity.User;

import cm.ex.delivery.repository.AuthorityRepository;
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

    @Bean
    CommandLineRunner initDatabase() {
        DatabasePopulate dbp = new DatabasePopulate();
        return args -> {

            dbp.populateAuthority(authorityRepository);
            dbp.populateUser(userRepository, authorityRepository, passwordEncoder);

        };
    }

    private void populateAuthority(AuthorityRepository authorityRepository) {
        if (authorityRepository.count() == 0) {
            authorityRepository.save(new Authority("admin",1,"admin"));
            authorityRepository.save(new Authority("moderator", 2,"admin"));
            authorityRepository.save(new Authority("delivery", 3,"moderator"));
            authorityRepository.save(new Authority("owner", 3,"moderator"));
            authorityRepository.save(new Authority("staff", 4,"owner"));
            authorityRepository.save(new Authority("user", 5,"auto"));

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
            userRepository.save(new User("Aakrity Simkhada", "aakriti@gmail.com", passwordEncoder.encode("password"),"", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityModerator.get());
            userRepository.save(new User("Moderator One", "moderator1@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Moderator Two", "moderator2@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Moderator Three", "moderator3@gmail.com", passwordEncoder.encode("password"),"", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityDelivery.get());
            userRepository.save(new User("Delivery Guy One", "delivery1@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Delivery Guy Two", "delivery2@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Delivery Guy Three", "delivery3@gmail.com", passwordEncoder.encode("password"),"", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityOwner.get());
            authoritySet.add(authorityStaff.get());
            userRepository.save(new User("Owner One", "owner1@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Owner Two", "owner2@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Owner Three", "owner3@gmail.com", passwordEncoder.encode("password"),"", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityStaff.get());
            userRepository.save(new User("Staff One", "staff1@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Staff Two", "staff2@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("Staff Three", "staff3@gmail.com", passwordEncoder.encode("password"),"", authoritySet));

            authoritySet.clear();
            authoritySet.add(authorityUser.get());
            userRepository.save(new User("User One", "user1@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("User Two", "user2@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("User Three", "user3@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("User Four", "user4@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("User Five", "user5@gmail.com", passwordEncoder.encode("password"),"", authoritySet));
            userRepository.save(new User("User Six", "user6@gmail.com", passwordEncoder.encode("password"),"", authoritySet));

            System.out.println("User Repository has been populated with five(5) initial users.");
        }
    }

}

