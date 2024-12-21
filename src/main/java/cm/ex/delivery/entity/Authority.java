package cm.ex.delivery.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authority")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true, nullable = false)
    private String authority;

    private String requestAuthority;

    private int level;

    private String authorityAccept;

//    @ManyToMany(mappedBy = "authoritySet")
//    private Set<User> users = new HashSet<>();

    public Authority(String authority, int level, String authorityAccept) {
        this.authority = authority;
        this.level = level;
        this.authorityAccept = authorityAccept;
    }
}
