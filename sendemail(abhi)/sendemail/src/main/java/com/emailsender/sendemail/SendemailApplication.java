package com.emailsender.sendemail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class SendemailApplication {
        @Autowired
        
        private sendEmail senderservice;
         
        
		public static void main(String[] args) {
			SpringApplication.run(SendemailApplication.class, args);
		}
        @EventListener(ApplicationReadyEvent.class)
        public void sendEmail(){
        	senderservice.send(User.getemail(),"OTP",("Hello "+User.getname()+"!\n"+"Otp for Bank Transaction is : "+ATMFrame.t1()));
        }
}

