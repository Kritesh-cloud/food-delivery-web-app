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
@Table(name = "user_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter message")
    @Column(name = "message")
    private String message;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @JoinColumn(name = "user_sent")
    private boolean userSent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank(message="please enter message")
    private User user;

}
