package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.Goal;

import java.util.List;

public interface GoalService {

    List<Goal> getGoals();

    Goal getGoal(final int id);

    Goal addGoal(final Goal goal);

    Goal deleteGoal(final int id);

    void updateGoal(final int id, final Goal goal);

    public Goal getGoalByParishId(int id);
}
