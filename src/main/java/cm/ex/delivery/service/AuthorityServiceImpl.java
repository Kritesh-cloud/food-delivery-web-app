package cm.ex.delivery.service;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority addAuthority(String authority) {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        Optional<Authority> newAuthority = authorityRepository.findByAuthority(authority);
        if (newAuthority.isEmpty()) throw new IllegalArgumentException("Authority cannot be blank.");

        Set<Authority> userAuthority = userAuth.getUser().getAuthoritySet();
        Optional<Authority> higherAuthority = authorityRepository.findByAuthority(newAuthority.get().getAuthorityAccept());
        if(!userAuthority.contains(higherAuthority.get())) throw new AccessDeniedException("access denied for authority assign");

        return authorityRepository.save(newAuthority.get());
    }

    @Override
    public List<Authority> listAuthority() {
        List<Authority> authorityList = authorityRepository.findAll();
        return authorityList.isEmpty() ? new ArrayList<>() : authorityList;
    }

    @Override
    public BasicResponse removeAuthority(String authority) {
        Optional<Authority> authorityCheck = authorityRepository.findByAuthority(authority);
        if (authorityCheck.isEmpty())
            return BasicResponse.builder().status(false).code(404).message("Authority not found").build();

        authorityRepository.delete(authorityCheck.get());
        return BasicResponse.builder().status(true).code(200).message("Authority deleted successfully").build();
    }
}
