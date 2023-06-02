package io.github.bodzisz.hmirs;

import io.github.bodzisz.hmirs.dto.NewUserDTO;
import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.entity.Role;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.service.ChurchService;
import io.github.bodzisz.hmirs.service.ParishService;
import io.github.bodzisz.hmirs.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;
    private final ParishService parishService;
    private final ChurchService churchService;

    @PostConstruct
    void init() {
        if(userService.getUsersCount() == 0) {
            userService.addUser(new NewUserDTO("Kacper", "Wojcicki", "kacperwojcicki", "password", Role.USER));
            userService.addUser(new NewUserDTO("Mateusz", "Wozniak", "mateuszwozniak", "password", Role.PRIEST));
        }
        if(parishService.getParishes().isEmpty()) {
            parishService.addParish(new Parish("Parafia Mariusza Pudzianowskiego", userService.getByLogin("mateuszwozniak")));
        }
        if(churchService.getChurches().isEmpty()) {
            churchService.addChurch(new Church("Chrystusa Krola", "Gluszyca", 50, parishService.getParish(1)));
            churchService.addChurch(new Church("Roberta Maklowicza", "Wroclaw", 50, parishService.getParish(1)));
            churchService.addChurch(new Church("Roberta Kubicy", "Wroclaw", 100, parishService.getParish(1)));
        }

    }
}
