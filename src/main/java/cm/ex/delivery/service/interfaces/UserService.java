package cm.ex.delivery.service.interfaces;


import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public BasicResponse signUp(User user, MultipartFile profileImage) throws IOException;

    public BasicResponse logIn(User user);

    public User userInfo(String userId);

    public List<User> userList(String userId);

    public BasicResponse updateUser(User user, MultipartFile profileImage) throws IOException;

    public BasicResponse deleteUser(String userId);
}