package cm.ex.delivery.security.filter;

import cm.ex.delivery.entity.User;
import cm.ex.delivery.entity.user.Authority;
import cm.ex.delivery.repository.AuthorityRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.security.authentication.UserAuth;
import cm.ex.delivery.service.JwtService;
import cm.ex.delivery.service.UserServiceImplement;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserServiceImplement userServiceImplement;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            logger.info("#[INFO]Authentication Filter. Basic");
            String username = extractUsernameAndPassword(authHeader)[0];
            String password = extractUsernameAndPassword(authHeader)[1];

            UserAuth auth = userCredentialAuth(username, password);
            if (!auth.isAuthenticated()) {
                throw new AccessDeniedException("Bad Credentials");
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            logger.info("#[INFO]Authentication Filter. Bearer");
            final String token = authHeader.substring(7);

            UserAuth auth = userTokenAuth(token);

            System.out.println("## auth bearer : " + auth.toString());
            if (!auth.isAuthenticated()) {
                throw new AccessDeniedException("Bad Credentials");
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private UserAuth userCredentialAuth(String username, String password) throws AccessDeniedException {
        UserDetails userDetails = userServiceImplement.loadUserByUsername(username);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AccessDeniedException("Bad Credentials");
        }
        User user = userRepository.findByEmail(userDetails.getUsername());

        return new UserAuth(true, username, null, null, user.getFullName(), convertToGrantedAuthorities(user.getAuthority()));
    }

    private UserAuth userTokenAuth(String token) throws AccessDeniedException {
        final String userEmail = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        UserAuth userAuth = new UserAuth(true, user.getEmail(), null, null, user.getFullName(), convertToGrantedAuthorities(user.getAuthority()));
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

