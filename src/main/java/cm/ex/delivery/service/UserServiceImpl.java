package cm.ex.delivery.service;

import cm.ex.delivery.entity.Authority;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ModelMapper modelMapper;

    @Override
    public BasicResponse signUp(User user) {
        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isEmpty())
            return BasicResponse.builder().status(false).code(409).message("This email is already in use").build();
        Set<Authority> authorityList = authorityRepository.findByAuthority("user")
                .map(Set::of)
                .orElse(Set.of());
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthoritySet(authorityList);
        userRepository.save(user);
        return BasicResponse.builder().status(true).code(200).message("Account created successfully").build();
    }

    @Override
    public BasicResponse logIn(User user) {
        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isEmpty() || !passwordEncoder.matches(user.getPassword(),userEmail.get().getPassword()))
            return BasicResponse.builder().status(false).code(401).message("Email or password doesn't match").build();

        UserAuth userAuth = new UserAuth(user.getId().toString(),true,user.getEmail(),user.getPassword(),null,user.getPassword(),convertToGrantedAuthorities(user.getAuthoritySet()));
        String jwtToken = jwtService.generateToken(userAuth);

        return BasicResponse.builder().status(true).code(200).message("Successfully logged in").token(jwtToken).build();
    }

    @Override
    public User userInfo(String userId) {
        return null;
    }

    @Override
    public List<User> userList(String userId) {
        return List.of();
    }

    @Override
    public BasicResponse updateUser(User user) {
        return null;
    }

    @Override
    public BasicResponse deleteUser(String userId) {
        return null;
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
}