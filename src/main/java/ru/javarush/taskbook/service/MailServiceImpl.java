package ru.javarush.taskbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Layo on 22.04.2015.
 */
@Service
public class MailServiceImpl implements MailService{

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final String PASS_REC_SUBJECT = "Восстановление пароля";
    private static final String REG_CONFIRM_SUBJECT = "Подтверждение регистрации";
    private static final String NEW_TASK_ADDED_SUBJECT = "Добавлена новая задача";
    private static final String FROM = "no-reply.taskbook@yandex.ru";

    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private SimpleMailMessage mail = new SimpleMailMessage();

    private void send (SimpleMailMessage m){
        mailSender.send(m);
    }

    @Async
    @Override
    public void sendEmailNewTaskAdded(String[] to, String text) {
        mail.setTo(to);
        mail.setFrom(FROM);
        mail.setSubject(NEW_TASK_ADDED_SUBJECT);
        mail.setText(text);
        send(mail);
    }

    @Async
    @Override
    public void sendEmailPasswordRecovery(String to, String text) {
        mail.setTo(to);
        mail.setFrom(FROM);
        mail.setSubject(PASS_REC_SUBJECT);
        mail.setText(text);
        send(mail);
    }

    @Async
    @Override
    public void sendEmailRegConfirm(String to, String text) {
        mail.setTo(to);
        mail.setFrom(FROM);
        mail.setSubject(REG_CONFIRM_SUBJECT);
        mail.setText(text);
        send(mail);
    }
}
