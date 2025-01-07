package cm.ex.delivery.security.filter;

import cm.ex.delivery.entity.Authority;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.JwtService;
import cm.ex.delivery.service.UserServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            System.out.println("Basic filter");
            String username = extractUsernameAndPassword(authHeader)[0];
            String password = extractUsernameAndPassword(authHeader)[1];

            UserAuth auth = userCredentialAuth(username, password);
            if (!auth.isAuthenticated()) {
                throw new AccessDeniedException("Bad Credentials");
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Bearer filter : "+authHeader.toString());
            final String token = authHeader.substring(7);
            UserAuth auth = userTokenAuth(token);
            if (!auth.isAuthenticated()) {
                throw new AccessDeniedException("Bad Credentials");
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private UserAuth userCredentialAuth(String username, String password) throws AccessDeniedException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AccessDeniedException("Bad Credentials");
        }

        return new UserAuth(user.get().getId().toString(), true, username, null, null, user.get().getName(), convertToGrantedAuthorities(user.get().getAuthoritySet()),user.get());
    }

    private UserAuth userTokenAuth(String token) throws AccessDeniedException {
        final String userEmail = jwtService.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        UserAuth userAuth = new UserAuth(user.get().getId().toString(), true, user.get().getEmail(), null, null, user.get().getName(), convertToGrantedAuthorities(user.get().getAuthoritySet()),user.get());
        if (!jwtService.isTokenValid(token, userAuth)) {
            throw new JwtException("Invalid token");
        }
        return userAuth;
    }

    private String[] extractUsernameAndPassword(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2); // values = [username, password]
    }

    private List<GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }


}

