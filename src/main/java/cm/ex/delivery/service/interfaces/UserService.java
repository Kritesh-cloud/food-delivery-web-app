package cm.ex.delivery.service.interfaces;


import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface UserService {

    public BasicResponse signUp(User user);

    public BasicResponse logIn(User user);

    public User userInfo(String userId);

    public List<User> userList(String userId);

    public BasicResponse updateUser(User user);

    public BasicResponse deleteUser(String userId);
//    //CREATE
//    public BasicUserResponse addUser(SignUpUserDto signUpUserDto);
//
//    //READ
//    public LoginResponse getUserToken(SignInUserDto signInUserDto);
//    public User getUserById(UUID userId);
//    public List<User> listAllUsers();
//    public List<User> listAllUserByAuthority(Authority authority);
//
//    //UPDATE
//    public BasicUserResponse UpdateUser(User user);
//
//    //DELETE
//    public BasicUserResponse deleteUserById(UUID userId);
}