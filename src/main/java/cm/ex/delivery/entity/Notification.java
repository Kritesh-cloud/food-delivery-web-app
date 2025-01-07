package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "notification")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User senderId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "notification__recipient",
            joinColumns = @JoinColumn(name = "notification_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "user_id", updatable = true))
    private Set<User> userSet;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
