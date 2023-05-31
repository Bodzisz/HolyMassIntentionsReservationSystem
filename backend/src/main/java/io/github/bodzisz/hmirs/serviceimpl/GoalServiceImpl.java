package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Goal;
import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.repository.GoalRepository;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import io.github.bodzisz.hmirs.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final ParishRepository parishRepository;

    public GoalServiceImpl(GoalRepository goalRepository,
                               ParishRepository parishRepository){
        this.goalRepository = goalRepository;
        this.parishRepository = parishRepository;
    }

    @Override
    public List<Goal> getGoals() {
        return goalRepository.findAll();
    }

    @Override
    public Goal getGoal(int id) {
        Optional<Goal> search = goalRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Goal of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Goal getGoalByParishId(int id){
        Optional<Parish> parish = parishRepository.findById(id);
        if (parish.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", id));
        return goalRepository.findGoalByParish(parish.get());
    }

    @Override
    public Goal getGoalByParish(Parish parish){
        return goalRepository.findGoalByParish(parish);
    }

    @Override
    public Goal addGoal(Goal goal) {
        int parishId = goal.getParish().getId();
        Optional<Parish> parish = parishRepository.findById(parishId);
        if (parish.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", parishId));
        goal.setParish(parish.get());
        return goalRepository.save(goal);
    }

    @Override
    public Goal deleteGoal(int id) {
        Optional<Goal> toDelete = goalRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Goal of id=%d was not found", id));
        goalRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateGoal(final int id, final Goal goal) {
        Goal existingGoal = goalRepository.findById(id).orElse(null);
        if (existingGoal != null && existingGoal.getId()==goal.getId()) {
            goalRepository.save(goal);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Goal of id=%d was not found", id));
        }
    }

    @Override
    public void addProgress(final Goal goal, final int amount) {
        goal.setGathered(goal.getGathered() + amount);
        goalRepository.save(goal);
    }
}
