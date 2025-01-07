package cm.ex.delivery.service.interfaces;


import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public BasicResponse signUp(User user, MultipartFile profileImage) throws IOException;

    public BasicResponse logIn(User user);

    public User findUserById(String userId);

    public User userInfo();

    public List<User> userList();

    public List<User> listUserByAuthority(String authority);

    public BasicResponse assignAuthority(String authority, String userId);

    public BasicResponse removeAuthority(String authority, String userId);

    public BasicResponse updateUser(User user);

    public BasicResponse deleteUser();
}

/*
public BasicResponse assignAuthority(String authority, String userId);
public BasicResponse removeAuthority(String authority, String userId);
public List<User> listUserByAuthority(String authority);
*/