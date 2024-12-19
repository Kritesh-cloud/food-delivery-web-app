package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface AuthorityService {

    public Authority addAuthority(String authority);

    public List<Authority> listAuthority();

    public BasicResponse removeAuthority(String authority);

}

