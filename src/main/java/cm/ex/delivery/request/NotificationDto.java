package cm.ex.delivery.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationDto {

    private String notification;

    private boolean restaurant;

    private boolean staff;

    private boolean delivery;

    private boolean customer;

    private List<String> accountList;
}
