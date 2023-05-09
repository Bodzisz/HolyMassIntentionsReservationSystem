package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<User>> getUser(@PathVariable final int id) {
        return ResponseEntity.ok(userRepository.findAllById(Collections.singleton(id)));
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody final User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setTokenExpired(updatedUser.isTokenExpired());
            existingUser.setEnabled(updatedUser.isEnabled());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setLogin(updatedUser.getLogin());
            userRepository.save(existingUser);
        }
        return existingUser;
    }
}
