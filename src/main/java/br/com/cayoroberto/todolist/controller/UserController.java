package br.com.cayoroberto.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cayoroberto.todolist.domain.user.UserDTO;
import br.com.cayoroberto.todolist.domain.user.User;
import br.com.cayoroberto.todolist.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository repository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserDTO user)  {

        var userExist = this.repository.findByUsername(user.username());
        if(userExist != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }
        var passwordHashred = BCrypt
                .withDefaults().hashToString(12, user.password().toCharArray());
        User newUser = new User(user);
        newUser.setPassword(passwordHashred);
        var userCreated = this.repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
