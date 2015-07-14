package ru.javarush.taskbook.service;

/**
 * Created by Layo on 22.04.2015.
 */
public interface MailService {
    public void sendEmailNewTaskAdded(String[] to, String text);
    public void sendEmailPasswordRecovery(String to, String text);
    public void sendEmailRegConfirm(String to, String text);
}
