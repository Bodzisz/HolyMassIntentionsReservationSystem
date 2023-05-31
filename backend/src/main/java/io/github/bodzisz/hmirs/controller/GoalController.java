package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.entity.Goal;
import io.github.bodzisz.hmirs.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/goals")
@CrossOrigin(origins = "http://localhost:3000")
public class GoalController {

    private final GoalService goalsService;

    public GoalController(GoalService goalsService) {
        this.goalsService = goalsService;
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getGoals() {
        return ResponseEntity.ok(goalsService.getGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoal(@PathVariable final int id) {
        return ResponseEntity.ok(goalsService.getGoalByParishId(id));
    }

    @PostMapping
    public ResponseEntity<Goal> postGoal(@RequestBody final Goal goal) {
        goalCheck(goal);
        return ResponseEntity.ok(goalsService.addGoal(goal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Goal> deleteGoal(@PathVariable int id) {
        return ResponseEntity.ok(goalsService.deleteGoal(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGoal(@PathVariable int id, @RequestBody Goal updatedGoal) {
        goalCheck(updatedGoal);
        goalsService.updateGoal(id, updatedGoal);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("reset/{id}")
    public ResponseEntity<Void> resetGoal(@PathVariable int id) {
        Goal updatedGoal = goalsService.getGoal(id);
        updatedGoal.setGoal_title("Dobrobyt parafii");
        updatedGoal.setAmount(1000);
        updatedGoal.setGathered(0);
        goalsService.updateGoal(id, updatedGoal);
        return ResponseEntity.status(204).build();
    }

    private void goalCheck(Goal goal){
        if (goal.getAmount() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid goal amount");
        if (goal.getGathered() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid gathered amount");
        if (goal.getGoal_title().length() <= 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid goal title");
        if (goal.getParish() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parish");
    }
}
