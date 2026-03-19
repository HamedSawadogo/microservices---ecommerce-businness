package org.ecom.backend.shared;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendEmail(String message) throws Exception {
            Thread.sleep(10000);
            System.err.println("Email sent :  " + message);
    }
}
