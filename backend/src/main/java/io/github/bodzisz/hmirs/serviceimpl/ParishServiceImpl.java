package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Goal;
import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.repository.GoalRepository;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import io.github.bodzisz.hmirs.repository.UserRepository;
import io.github.bodzisz.hmirs.service.ParishService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ParishServiceImpl implements ParishService {

    private final ParishRepository parishRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public ParishServiceImpl(ParishRepository parishRepository,
                                GoalRepository goalRepository,
                                UserRepository userRepository){
        this.parishRepository = parishRepository;
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Parish> getParishes() {
        return parishRepository.findAll();
    }

    @Override
    public Parish getParish(int id) {
        Optional<Parish> search = parishRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Parish addParish(Parish parish) {
        int userId = parish.getMainPriest().getId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User of id=%d was not found", userId));
        parish.setMainPriest(user.get());
        Goal goal = new Goal();
        Parish new_parish = parishRepository.save(parish);
        goal.setParish(new_parish);
        goal.setGoal_title("Dobrobyt parafii");
        goal.setGathered(0);
        goal.setAmount(1000);
        goalRepository.save(goal);
        return new_parish;
    }

    @Override
    public Parish deleteParish(int id) {
        Optional<Parish> toDelete = parishRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", id));
        Parish parish = toDelete.get();
        Goal goalToDelete = goalRepository.findGoalByParish(parish);
        goalRepository.delete(goalToDelete);
        parishRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateParish(final int id, Parish parish) {
        Parish existingParish = parishRepository.findById(id).orElse(null);
        if (existingParish != null) {
            int userId = parish.getMainPriest().getId();
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User of id=%d was not found", userId));
            existingParish.setMainPriest(user.get());
            existingParish.setName(parish.getName());
            parishRepository.save(existingParish);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Parish of id=%d was not found", id));
        }
    }
}
