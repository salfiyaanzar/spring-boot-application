package com.example.spring_boot_task_application.repositories;

import com.example.spring_boot_task_application.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepositories extends CrudRepository<Task, Long> {


}
