package org.ecom.backend.notifications.domain;

import java.util.List;

public interface SendEmailNotificationService {
    void sendEmail(List<String> to, String title, Object content);
}
