package com.example.spring_boot_task_application.controller;

import com.example.spring_boot_task_application.models.Task;
import com.example.spring_boot_task_application.services.TaskService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/tasks")

public class TaskController {
    private final Logger logger= LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskService taskService;


    @GetMapping("")
    public Iterable<Task> getTasks(){
        logger.debug("REST TaskController::getTasks");
        return taskService.getAll();

    }
    @GetMapping("/{id}")
    public Optional<Task> getTask(@PathVariable Long id){
        logger.debug("REST TaskController::getTaskById:{}", id);
        Optional<Task> optionalTask=taskService.getById(id);
        return optionalTask;
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        logger.debug("REST TaskController::deleteTasksById: {}",id );
        taskService.delete(id);
    }

    @PostMapping("api/v1/tasks")
    public Task createNewTask(@RequestBody Task newTask) {
        logger.debug("REST TaskController::createTask: {}", newTask);
        Task task = new Task();
        task.setDescription(newTask.getDescription());
        task.setStatus(newTask.getStatus());
        task = taskService.save(task);
        return task;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task newTask) {
        logger.debug("REST TaskController::updateTask[{}]: {}", id, newTask);

        Optional<Task> optionalTask = taskService.getById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setDescription(newTask.getDescription());
            task.setStatus(newTask.getStatus());
            task = taskService.save(task);

            return new ResponseEntity<Task>(task, HttpStatus.OK);  // Use 200 OK for updates
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
