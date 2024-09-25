package cm.ex.delivery.entity.user;

import cm.ex.delivery.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_authority_list")
public class AuthorityList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank(message="please enter authority list data")
    private User user;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @NotBlank(message="please enter authority list data")
    private Authority authority;

}
