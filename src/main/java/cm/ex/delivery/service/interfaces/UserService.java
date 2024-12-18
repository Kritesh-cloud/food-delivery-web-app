package cm.ex.delivery.service.interfaces;


import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public BasicResponse signUp(User user, MultipartFile profileImage) throws IOException;

    public BasicResponse logIn(User user);

    public User userInfo();

    public List<User> userList();

    public BasicResponse updateUser(User user);

    public BasicResponse updateAddUserAuthority(String newAuthority);

    public BasicResponse updateRemoveUserAuthority(String newAuthority);

    public BasicResponse deleteUser();
}