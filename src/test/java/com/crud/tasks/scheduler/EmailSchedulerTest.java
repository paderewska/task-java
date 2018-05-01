package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void sendInformationEmail() {

        //Given
        when(adminConfig.getAdminMail()).thenReturn("pieczony.kalafior@gmail.com");

        //When
        Mail mail = emailScheduler.sendInformationEmail();
        simpleEmailService.send(mail);

        //Then
        Assert.assertEquals("Wiadomosc", mail.getMessage());
    }

    @Test
    public void sendInformationEmailThymeleaf() {

        //Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("pieczony.kalafior@gmail.com");

        //When
        Mail mail = emailScheduler.sendInformationEmailThymeleaf();
        simpleEmailService.send2(mail);

        //Then
        Assert.assertEquals("Currently in database you got: 1 task", mail.getMessage());
    }


}