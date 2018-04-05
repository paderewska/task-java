package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    public void getTasks() {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");
        List<TaskDto> taskListDto = new ArrayList<>();
        taskListDto.add(taskDto);

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);

        //When
        List<TaskDto> test = taskController.getTasks();

        //Then
        assertEquals(1, test.size());
        assertEquals(2L, test.get(0).getId(), 0);
        assertEquals("Kałuża", test.get(0).getTitle());
        assertEquals("Mokra", test.get(0).getContent());
    }

    @Test
    public void getTask() throws TaskNotFoundException {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");
        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When
        TaskDto test = taskController.getTask(1L);

        //Then
        assertEquals(2L, test.getId(), 0);
        assertEquals("Kałuża", test.getTitle());
        assertEquals("Mokra", test.getContent());
    }

    @Test
    public void deleteTask() {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");

        //When
        taskController.deleteTask(1L);

        //Then
        verify(dbService, times(1)).deleteTask(1L);

    }

    @Test
    public void updateTask() {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");
        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When
        TaskDto test = taskController.updateTask(taskDto);

        //Then
        assertEquals(2L, test.getId(), 0);
        assertEquals("Kałuża", test.getTitle());
        assertEquals("Mokra", test.getContent());
    }

    @Test
    public void createTask() {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");
        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        //When
        taskController.createTask(taskDto);

        //Then
        verify(dbService, times(1)).saveTask(task);
    }
}