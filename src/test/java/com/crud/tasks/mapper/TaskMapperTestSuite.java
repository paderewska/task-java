package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskMapperTestSuite {

    @Test
    public void mapToTask() {

        //Given
        TaskMapper taskMapper = new TaskMapper();

        TaskDto taskDto = new TaskDto(1L, "Zrób pranie", "Zrób to dobrze");

        //When
        Task testTask = taskMapper.mapToTask(taskDto);

        //Then
        Assert.assertEquals(1L, testTask.getId(), 0);
        Assert.assertEquals("Zrób pranie", testTask.getTitle());
        Assert.assertEquals("Zrób to dobrze", testTask.getContent());
    }

    @Test
    public void mapToTaskDto() {

        //Given
        TaskMapper taskMapper = new TaskMapper();

        Task task = new Task(1L, "Zrób pranie", "Zrób to dobrze");

        //When
        TaskDto testTaskDto = taskMapper.mapToTaskDto(task);

        //Then
        Assert.assertEquals(1L, testTaskDto.getId(), 0);
        Assert.assertEquals("Zrób pranie", testTaskDto.getTitle());
        Assert.assertEquals("Zrób to dobrze", testTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {

        //Given
        TaskMapper taskMapper = new TaskMapper();

        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Zrób pranie", "Zrób to dobrze"));

        //When
        List<TaskDto> testTaskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        Assert.assertEquals(1, testTaskDtoList.size());
        Assert.assertEquals(1L, testTaskDtoList.get(0).getId(), 0);
        Assert.assertEquals("Zrób pranie", testTaskDtoList.get(0).getTitle());
        Assert.assertEquals("Zrób to dobrze", testTaskDtoList.get(0).getContent());

    }
}