package cm.ex.delivery.entity.content;

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
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter content data")
    @Column(name = "title")
    private String title;

    @NotBlank(message="please enter content data")
    @Column(name = "content")
    private String content;

    @NotBlank(message="please enter content data")
    @Column(name = "type")
    private String type; // guide, article, blog, promotional, deals

    @NotBlank(message="please enter content data")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotBlank(message="please enter content data")
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @NotBlank(message="please enter content data")
    @Column(name = "status")
    private String status; //draft, published, archived

    @NotBlank(message="please enter content data")
    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank(message="please enter user data")
    @Column(name = "view_count")
    private String viewCount;

    @NotBlank(message="please enter content data")
    @Column(name = "like_count")
    private String likeCount;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotBlank(message="please enter message")
    private User author;
}
