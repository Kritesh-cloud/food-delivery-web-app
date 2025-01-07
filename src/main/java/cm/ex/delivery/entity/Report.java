package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User senderId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
