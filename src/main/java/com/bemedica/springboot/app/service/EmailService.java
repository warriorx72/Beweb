package com.bemedica.springboot.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bemedica.springboot.app.component.EmailStatus;

import javax.mail.internet.MimeMessage;
 
@Component
public class EmailService {
	
	    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	    
	    @Autowired
	    private JavaMailSender javaMailSender;
	 
	    public EmailStatus sendPlainText(String to, String subject, String text, String ruta) {
	        return sendM(to, subject, text, false, ruta);
	    }
	 
	    public EmailStatus sendHtml(String to, String subject, String htmlBody, String ruta) {
	        return sendM(to, subject, htmlBody, true, ruta);
	    }
	 
	    private EmailStatus sendM(String to, String subject, String text, Boolean isHtml, String ruta) {
	        try {
	        	
	            MimeMessage mail = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	            helper.setTo(to);
	            helper.setFrom("Bemedica");
	            helper.setSubject(subject);
	            helper.setText(text, isHtml);
	            
	            //Attacht
	            FileSystemResource file = new FileSystemResource(ruta);
	    		helper.addAttachment("CotizacionDeEstudios.pdf", file);
	            
	            
	            javaMailSender.send(mail);
	            LOGGER.info("Send email '{}' to: {}", subject, to);
	            return new EmailStatus(to, subject, text).success();
	        } catch (Exception e) {
	            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", to, e.getMessage()));
	            return new EmailStatus(to, subject, text).error(e.getMessage());
	        }
	    }


}
