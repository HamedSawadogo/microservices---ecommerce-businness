package org.ecom.backend.notifications.infrastructure;

import org.ecom.backend.notifications.domain.SendEmailNotificationService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SendEmailNotificationServiceImpl implements SendEmailNotificationService {
    @Override
    public void sendEmail(List<String> to, String title, Object content) {
        //TODO send email
        System.err.println("Email sent to :  " + to + " title :  " + title +  "content:  " + content);
    }
}
