package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetAllTasks() throws Exception {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");
        List<TaskDto> taskListDto = new ArrayList<>();
        taskListDto.add(taskDto);

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);

       //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("Kałuża")))
                .andExpect(jsonPath("$[0].content", is("Mokra")));
    }

    @Test
    public void shouldGetTask() throws Exception {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");
        TaskDto taskDto = new TaskDto(1L, "Kałuża", "Mokra");

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Kałuża")))
                .andExpect(jsonPath("$.content", is("Mokra")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {

        //Given
        Task task = new Task(1L, "Obiad", "Kotlet");

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteTask(1L);
    }

    @Test
    public void shouldUpdateTask() throws Exception{

        //Given
        Task task = new Task(2L, "Kałuża", "Mokra");
        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");
        TaskDto updatedTaskDto = new TaskDto(2L, "Kałuża sucha", "Mokra plama");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);

        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(updatedTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Kałuża sucha")))
                .andExpect(jsonPath("$.content", is("Mokra plama")));
    }

    @Test
    public void shouldCreateTask() throws Exception {

        //Given
        Task task = new Task(2L, "Kałuża", "Mokra");
        TaskDto taskDto = new TaskDto(2L, "Kałuża", "Mokra");
        TaskDto updatedTaskDto = new TaskDto(2L, "Kałuża sucha", "Mokra plama");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);

        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(updatedTaskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Kałuża sucha")))
                .andExpect(jsonPath("$.content", is("Mokra plama")));
    }
}