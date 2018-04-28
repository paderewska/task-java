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

    //@Scheduled(cron ="0 0 10 * * *")
    public void sendInformationEmailThymeleaf(){
        long size = taskRepository.count();
        String task = "tasks";
        if(size==1) task = "task";
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT, "Currently in database you got: " + size + " " + task));
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        simpleEmailService.send2(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT, ""));
    }
}
