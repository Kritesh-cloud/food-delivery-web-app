package cm.ex.delivery.entity.user;

import cm.ex.delivery.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_ban")
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @NotBlank(message="please enter ban data")
    @Column(name = "reason")
    private String reason;

    @OneToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
