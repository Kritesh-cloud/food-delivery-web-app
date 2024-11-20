package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.dto.request.authentication.SignInUserDto;
import cm.ex.delivery.dto.request.authentication.SignUpUserDto;
import cm.ex.delivery.dto.respone.authentication.BasicUserResponse;
import cm.ex.delivery.dto.respone.authentication.LoginResponse;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.entity.user.Authority;

import java.util.List;
import java.util.UUID;

public interface UserService {

    //CREATE
    public BasicUserResponse addUser(SignUpUserDto signUpUserDto);

    //READ
    public LoginResponse getUserToken(SignInUserDto signInUserDto);
    public User getUserById(UUID userId);
    public List<User> listAllUsers();
    public List<User> listAllUserByAuthority(Authority authority);

    //UPDATE
    public BasicUserResponse UpdateUser(User user);

    //DELETE
    public BasicUserResponse deleteUserById(UUID userId);
}