package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.dto.NewUserDTO;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable final int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody final NewUserDTO user) {
        userCheck(user);
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        return ResponseEntity.status(201).body(userService.deleteUser(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return ResponseEntity.status(204).build();
    }

    private void userCheck(NewUserDTO user){
        if (user.lastName() == null || user.lastName().length() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid last name");
        if (user.firstName() == null || user.firstName().length() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid first name");
        if (user.login() == null || user.login().length() < 5 || userService.userExists(user.login()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid login");
    }
}
