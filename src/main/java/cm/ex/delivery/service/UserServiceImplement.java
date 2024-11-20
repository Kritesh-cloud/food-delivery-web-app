package cm.ex.delivery.service;

import cm.ex.delivery.dto.request.authentication.SignInUserDto;
import cm.ex.delivery.dto.request.authentication.SignUpUserDto;
import cm.ex.delivery.dto.respone.authentication.BasicUserResponse;
import cm.ex.delivery.dto.respone.authentication.LoginResponse;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.entity.user.Authority;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements UserService, UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    Logger logger = LoggerFactory.getLogger(UserServiceImplement.class);

    @Override
    public BasicUserResponse addUser(SignUpUserDto signUpUserDto) {

        BasicUserResponse userResponse = new BasicUserResponse();
        userResponse.setType("Create");
        userResponse.setStatus(true);
        userResponse.setMessage("Account created successfully");
        User userEmail = userRepository.findByEmail(signUpUserDto.getEmail());
        if(userEmail != null){
            userResponse.setStatus(false);
            userResponse.setMessage("User with this email already exists");
            return userResponse;
        }

        Set<Authority> authorityList = new HashSet<>();
        Authority userAuthority = authorityRepository.findByAuthority("user");
        authorityList.add(userAuthority);

        User user = modelMapper.map(signUpUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityList);

        logger.info("[Info] UserServiceImplement, SignUpUserDto :{}",signUpUserDto.toString());
        logger.info("[Info] UserServiceImplement, User :{}",user.toString());

        userRepository.save(user);
        return userResponse;
    }

    @Override
    public LoginResponse getUserToken(SignInUserDto signInUserDto){
        LoginResponse loginResponse = new LoginResponse(false,null,null);
        User user = userRepository.findByEmail(signInUserDto.getEmail());
        if(user == null) {
            loginResponse.setMessage("user not found, cannot make token");
            return loginResponse;
        }
        if(!passwordEncoder.matches(signInUserDto.getPassword(),user.getPassword())){
            logger.info("User password doesn't matches "+signInUserDto.getPassword()+", "+user.getPassword());
            loginResponse.setMessage("Email or password doesn't match");
            return loginResponse;
        }

        logger.info("User password matches "+signInUserDto.getPassword()+", "+user.getPassword());
        UserAuth userAuth = new UserAuth(true,user.getEmail(),user.getPassword(),null,user.getPassword(),null);
        userAuth.setAuthority(convertToGrantedAuthorities(user.getAuthority()));
        String jwtToken = jwtService.generateToken(userAuth);

        loginResponse.setStatus(true);
        loginResponse.setMessage("user token");
        loginResponse.setToken(jwtToken);


        return loginResponse;
    }

    @Override
    public User getUserById(UUID userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> listAllUserByAuthority(Authority authority){
        return userRepository.findUserByAuthority(authority.getAuthority());
    }

    @Override
    public BasicUserResponse UpdateUser(User user) {
        BasicUserResponse userResponse = new BasicUserResponse();
        userResponse.setType("Update");
        userResponse.setStatus(true);
        userResponse.setMessage("User updated successfully");
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail == null){
            userResponse.setStatus(false);
            userResponse.setMessage("User not found");
            return userResponse;
        }
        userRepository.save(user);
        return userResponse;
    }

    @Override
    public BasicUserResponse deleteUserById(UUID userId) {
        BasicUserResponse userResponse = new BasicUserResponse();
        userResponse.setType("Update");
        userResponse.setStatus(true);
        userResponse.setMessage("User deleted successfully");
        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            userResponse.setStatus(false);
            userResponse.setMessage("User not found");
            return userResponse;
        }

        userRepository.delete(user.get());
        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .username(user.getEmail())
                .authorities(convertToGrantedAuthorities(user.getAuthority()))
                .build();
    }

    private List<GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}

