package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Notification;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface NotificationService {

    public BasicResponse createNotification(String notification, List<String> recipientIds);

    public List<Notification> listNotificationBySenderId(User senderId);

    public List<Notification> listAllNotification();

    public List<Notification> listNotificationBySenderId(String type);
}
