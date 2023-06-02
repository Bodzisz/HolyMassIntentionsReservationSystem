package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.dto.NewUserDTO;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.repository.UserRepository;
import io.github.bodzisz.hmirs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        Optional<User> search = userRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User of id=%d was not found", id));
        return search.get();
    }

    @Override
    public User addUser(NewUserDTO user) {
        User userToAdd = new User(user.firstName(), user.lastName(), user.login(), user.password(), user.role());
        userToAdd.setId(0);
        if (userToAdd.getPassword().length() < 3 || Objects.equals(userToAdd.getFirstName(), "")
                || Objects.equals(userToAdd.getLastName(), "") || userToAdd.getLogin().length() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Passed data is invalid"));
        userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));

        return userRepository.save(userToAdd);
    }

    @Override
    public User deleteUser(int id) {
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User of id=%d was not found", id));
        userRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateUser(final int id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPassword(user.getPassword());
            existingUser.setLogin(user.getLogin());
            userRepository.save(existingUser);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User of id=%d was not found", id));
        }
    }

    @Override
    public boolean userExists(final String login){
        return userRepository.existsByLogin(login);
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
