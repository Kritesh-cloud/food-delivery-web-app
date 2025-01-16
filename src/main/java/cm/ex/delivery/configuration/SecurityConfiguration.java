package cm.ex.delivery.configuration;

import cm.ex.delivery.security.filter.AuthenticationFilter;
import cm.ex.delivery.security.filter.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private AuthorizationFilter authorizationFilter;

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("SecurityConfiguration");
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/signIn").permitAll()
                        .requestMatchers("/signUp").permitAll()

                                .requestMatchers("/user/browse/*").permitAll()
                                .requestMatchers("/list-restaurant-details").permitAll()
                                .requestMatchers("/list-restaurant-details/*").permitAll()
                                .requestMatchers("/list-restaurant-details-by-id/{id}").permitAll()
                                .requestMatchers("/reverse-list-restaurant-details/{id}").permitAll()
                                .requestMatchers("/list-short-restaurant-details").permitAll()
                                .requestMatchers("/list-short-restaurant-details/{id}").permitAll()
//                                .requestMatchers("/list-short-restaurant-details").permitAll()
//                                .requestMatchers("/list-short-restaurant-details").permitAll()

                                .requestMatchers("/user-list").permitAll()
                                .requestMatchers("/get-user/{userId}").permitAll()

//                        .requestMatchers("/product/list").permitAll()
                        .requestMatchers("/image/{imageId}").permitAll()

//                        // Role-based access control
//                        .requestMatchers("/admin/**").hasAnyAuthority("admin", "moderator", "user")
//                        .requestMatchers("/moderator/**").hasAnyAuthority("moderator", "user")
//                        .requestMatchers("/owner/**").hasAnyAuthority("owner", "staff", "user")
//                        .requestMatchers("/staff/**").hasAnyAuthority("staff", "user")
//                        .requestMatchers("/delivery/**").hasAnyAuthority("delivery", "user")
//                        .requestMatchers("/user/**").hasAuthority("user")

                        .anyRequest().authenticated()
                )
                .build();
    }
}

