package cm.ex.delivery.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BrowseListDto {

    private String id;

    private String title;

    private String type;

    private List<String> idList;
}
