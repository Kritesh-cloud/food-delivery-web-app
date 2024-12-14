package cm.ex.delivery.service;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.entity.Image;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private BasketServiceImpl basketService;

    @Override
    public BasicResponse signUp(User user, MultipartFile profileImage) throws IOException {
        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isEmpty())
            return BasicResponse.builder().status(false).code(409).message("This email is already in use").build();
        Set<Authority> authorityList = authorityRepository.findByAuthority("user")
                .map(Set::of)
                .orElse(Set.of());
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthoritySet(authorityList);

        String path = "http://localhost:8080/image/";
        user.setProfileUrl(path + "dummy");
        if (!Objects.requireNonNull(profileImage.getOriginalFilename()).isEmpty()) {
            Image iconImage = imageService.addImage(profileImage);
            user.setProfileUrl(path + iconImage.getId());
        }
        User newUser = userRepository.save(user);
        basketService.createBasket(newUser);
        return BasicResponse.builder().status(true).code(200).message("Account created successfully").build();
    }

    @Override
    public BasicResponse logIn(User user) {
        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isEmpty() || !passwordEncoder.matches(user.getPassword(), userEmail.get().getPassword()))
            return BasicResponse.builder().status(false).code(401).message("Email or password doesn't match").build();

        UserAuth userAuth = new UserAuth(user.getId().toString(), true, user.getEmail(), user.getPassword(), null, user.getPassword(), convertToGrantedAuthorities(user.getAuthoritySet()), null);
        String jwtToken = jwtService.generateToken(userAuth);

        return BasicResponse.builder().status(true).code(200).message("Successfully logged in").token(jwtToken).build();
    }

    @Override
    public User userInfo(String userId) {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        User user = userAuth.getUser();
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileUrl(user.getProfileUrl())
                .authoritySet(user.getAuthoritySet())
                .build();
    }

    @Override
    public List<User> userList(String userId) {
        List<User> userList = userRepository.findAll();
        return userList.isEmpty() ? List.of() : userList;
    }

    @Override
    public BasicResponse updateUser(User user, MultipartFile profileImage) throws IOException {
        Optional<User> updateUser = userRepository.findById(user.getId());
        if (updateUser.isEmpty())
            return BasicResponse.builder().status(false).code(409).message("Account not found").build();

        updateUser.get().setName(user.getName());
        updateUser.get().setEmail(user.getEmail());
        updateUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.get().setProfileUrl(user.getProfileUrl());

        if (!Objects.requireNonNull(profileImage.getOriginalFilename()).isEmpty()) {
            imageService.removeImage(extractImageId(user.getProfileUrl()));
            Image iconImage = imageService.addImage(profileImage);
            user.setProfileUrl("http://localhost:8080/image/" + iconImage.getId());
        }

        userRepository.save(updateUser.get());
        return BasicResponse.builder().status(true).code(200).message("User account updated successfully").build();
    }

    @Override
    public BasicResponse deleteUser(String userId) {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        basketService.removeBasket(userAuth.getUser());
        userRepository.delete(userAuth.getUser());
        return BasicResponse.builder().status(true).code(200).message("User account deleted successfully").build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .username(user.get().getEmail())
                .authorities(convertToGrantedAuthorities(user.get().getAuthoritySet()))
                .build();
    }

    private List<GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    public static List<String> convertToStringListAuthorities(List<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority) // Extract the authority name
                .collect(Collectors.toList());
    }

    private static String extractImageId(String imageUrl) {
        String uuidRegex = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


}