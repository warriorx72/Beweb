package com.bemedica.springboot.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bemedica.springboot.app.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailApplicationTest {
	@Autowired
    private EmailService emailService;

    @Test
    public void testEmail() {
       // emailService.sendMail("frank23@example.com", "Test subject", "Test mail");
    }


}
