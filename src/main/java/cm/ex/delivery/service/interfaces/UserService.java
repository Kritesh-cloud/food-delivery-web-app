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
}