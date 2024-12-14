package cm.ex.delivery.service;

import cm.ex.delivery.entity.Notification;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.NotificationRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BasicResponse createNotification(String notification, List<String> recipientIds) {
        return null;
    }

    @Override
    public List<Notification> listNotificationBySenderId(User senderId) {
        return List.of();
    }

    @Override
    public List<Notification> listAllNotification() {
        return List.of();
    }

    @Override
    public List<Notification> listNotificationBySenderId(String type) {
        return List.of();
    }
}
