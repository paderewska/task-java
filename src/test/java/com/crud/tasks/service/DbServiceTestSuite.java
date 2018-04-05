package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldGetAllTasks() {

        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Zadanie jeden", "Zrób coś"));
        taskList.add(new Task(2L, "Zadanie dwa", "Zrób cośtam"));

        when(taskRepository.findAll()).thenReturn(taskList);

        //When
        List<Task> testList = dbService.getAllTasks();

        //Then
        assertNotNull(testList);
        assertEquals(2, testList.size());
        assertEquals(2L, testList.get(1).getId(), 0);
        assertEquals("Zadanie jeden", testList.get(0).getTitle());
    }

    @Test
    public void shouldGetTask() {

        //Given

        Task task1 = new Task(1L, "Zadanie jeden", "Zrób coś");
        Task task2 = new Task(2L, "Zadanie dwa", "Zrób cośtam");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);

        when(taskRepository.findById(2L)).thenReturn(Optional.ofNullable(task2));

        //When
        Optional<Task> test = dbService.getTask(2L);

        //Then
        assertEquals("Zadanie dwa", test.get().getTitle());
    }

    @Test
    public void saveTask() {

        //Given
        Task task1 = new Task(1L, "Zadanie jeden", "Zrób coś");
        Task task2 = new Task(2L, "Zadanie dwa", "Zrób cośtam");

        when(taskRepository.save(task1)).thenReturn(task1);

        //When
        Task testTask = dbService.saveTask(task1);

        //Then
        assertEquals(1L, testTask.getId(), 0);
        assertEquals("Zadanie jeden", testTask.getTitle());
        assertEquals("Zrób coś", testTask.getContent());
    }

    @Test
    public void deleteTask() {

        //Given
        Task task1 = new Task(1L, "Zadanie jeden", "Zrób coś");

        //When
        dbService.deleteTask(1L);

        //Then
        verify(taskRepository, times(1)).deleteById(1L);
    }
}