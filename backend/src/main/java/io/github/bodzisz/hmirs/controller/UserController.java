package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> postUser(@RequestBody final User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        return ResponseEntity.status(201).body(userService.deleteUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return ResponseEntity.status(204).build();
    }
}
