package cm.ex.delivery.entity;

import cm.ex.delivery.entity.user.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter user data")
    @Column(name = "full_name")
    private String fullName;

    @NotBlank(message="please enter user data")
    @Column(name = "email")
    private String email;

    @NotBlank(message="please enter user data")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authority;

    public User(String fullName, String email, String password, Set<Authority> authority) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}