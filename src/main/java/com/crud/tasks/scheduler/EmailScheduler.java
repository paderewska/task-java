package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Task: Once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron ="0 0 10 * * *")
    public Mail sendInformationEmailThymeleaf(){
        long size = taskRepository.count();
        String task = "tasks";
        if(size==1) task = "task";
        Mail mail = new Mail(
                adminConfig.getAdminMail(),
                SUBJECT, "Currently in database you got: " + size + " " + task);
    simpleEmailService.send(mail);
    return mail;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public Mail sendInformationEmail() {
        Mail mail = new Mail(
                adminConfig.getAdminMail(),
                SUBJECT, "Wiadomosc");
        simpleEmailService.send2(mail);

        return mail;
    }
}
