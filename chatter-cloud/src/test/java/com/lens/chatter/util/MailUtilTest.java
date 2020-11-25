package com.lens.chatter.util;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MailUtilTest.ContextConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailUtilTest {

    @Configuration
    @TestPropertySource(locations = "classpath:application.properties")
    @ComponentScan(basePackages = {"com.lens.chatter"})
    public static class ContextConfiguration {
        @Bean
        public JavaMailSender mailSender() {
            return new JavaMailSenderImpl(); //create the instance here
        }
    }

    @Autowired
    private MailUtil mailUtil;

    @Test
    @Ignore("It is not working due to ConnectException, It should be solved")
    void sendTokenMailTest() {
        mailUtil.sendTokenMail("ajangs@hotmail.com",
                "test_token",
                "test mail",
                "Ignore this mail");
    }
}
