package cm.ex.delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "id_holder")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID dataId;

    private Long longDataId;

    private String dataType;
}
