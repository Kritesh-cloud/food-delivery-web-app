package cm.ex.delivery.security.filter;

import cm.ex.delivery.security.authentication.UserAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        UserAuth userAuth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();

        if (userAuth != null && userAuth.isAuthenticated()) {

            String requestURI = request.getRequestURI();
            List<String> ADMIN_ACCESS = List.of("admin", "moderator", "user");
            List<String> MODERATOR_ACCESS = List.of("moderator", "user");
            List<String> OWNER_ACCESS = List.of("owner", "staff", "user");
            List<String> STAFF_ACCESS = List.of("staff", "user");
            List<String> DELIVERY_ACCESS = List.of("delivery", "user");
            List<String> USER_ACCESS = List.of("user");

            if (requestURI.startsWith("/admin") && !hasAuthority(userAuth, ADMIN_ACCESS)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                response.getWriter().write("Access denied: You do not have the required role");
                return;
            } else if (requestURI.startsWith("/moderator") && !hasAuthority(userAuth, MODERATOR_ACCESS)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                response.getWriter().write("Access denied: You do not have the required role");
                return;
                } else if (requestURI.startsWith("/owner") && !hasAuthority(userAuth, OWNER_ACCESS)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                response.getWriter().write("Access denied: You do not have the required role");
                return;
            } else if (requestURI.startsWith("/staff") && !hasAuthority(userAuth, STAFF_ACCESS)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                response.getWriter().write("Access denied: You do not have the required role");
                return;
            } else if (requestURI.startsWith("/delivery") && !hasAuthority(userAuth, DELIVERY_ACCESS)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                response.getWriter().write("Access denied: You do not have the required role");
                return;
            } else if (requestURI.startsWith("/user") && !hasAuthority(userAuth, USER_ACCESS)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                response.getWriter().write("Access denied: You do not have the required role");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private boolean hasAuthority(UserAuth userAuth, List<String> requiredAuthorities) {

        List<String> userAuthorities = userAuth.getAuthorities().stream()
                .map(String::valueOf)
                .toList();


//        System.out.println("------------------------------------------------------------------------");
//        System.out.println("user Auth: "+userAuth.getAuthorities());
//        for(String au: requiredAuthorities){
//            System.out.println("requiredAuthorities: "+au);
//        }
//        System.out.println("------------------------------------");

        if(userAuthorities.containsAll(requiredAuthorities)){
//            System.out.println("yes");
            return true;
        }
        else{
//            System.out.println("no");
        }
        return false;
    }
}

