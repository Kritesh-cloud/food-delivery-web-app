package cm.ex.delivery.service;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public BasicResponse addAuthority(String authority) {
        if (authority.isBlank())
            throw new IllegalArgumentException("Input cannot be blank.");

        Optional<Authority> authorityCheck = authorityRepository.findByAuthority(authority);
        if (authorityCheck.isPresent())
            return BasicResponse.builder().status(true).code(200).message("This authority is already added").build();

        Authority newAuthority = new Authority(authority);
        return BasicResponse.builder().status(true).code(200).message("New authority added successfully").build();
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
