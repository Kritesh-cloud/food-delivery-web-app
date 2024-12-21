package cm.ex.delivery.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrowseContentResponse {

    private Long id;

    private Integer contentOrder;

    private String title;

    private String type;

    private List<String> ids;


}
