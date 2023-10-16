package br.com.cayoroberto.todolist.controller;

import br.com.cayoroberto.todolist.domain.task.Task;
import br.com.cayoroberto.todolist.domain.task.TaskDTO;
import br.com.cayoroberto.todolist.repository.ITaskRepository;
import br.com.cayoroberto.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository repository;
    @PostMapping("/")
    public ResponseEntity create (@RequestBody TaskDTO task, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        Task newTask = new Task(task);
        newTask.setIdUser((UUID) idUser);
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(newTask.getStartAt()) || currentDate.isAfter(newTask.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data início/data de término deve ser maior que a data atual");
        }
        if( newTask.getStartAt().isAfter(newTask.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data início deve ser maior que a data de término");
        }
        var taskcreated = repository.save(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskcreated);
    }

    @GetMapping("/")
    public List<Task> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.repository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskDTO task, @PathVariable UUID id, HttpServletRequest request ){

        var existTask = this.repository.findById(id).orElse(null);

        if(existTask == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada");
        }
        var idUser = request.getAttribute("idUser");
        if(!existTask.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alterar essa tarefa");
        }
        Task updateTask = new Task(task);

        Utils.copyNonNullProperties(updateTask, existTask);

        var taskUpdated = this.repository.save(existTask);
        return ResponseEntity.ok().body(taskUpdated );

    }
}
