package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import sun.util.resources.cldr.gv.LocaleNames_gv;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();

    @Override
    Task save(Task task);

    void deleteById(Long id);

    Optional<Task> findById(String id);

}
