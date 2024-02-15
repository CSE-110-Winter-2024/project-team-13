package edu.ucsd.cse110.successorator.lib.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoalList {

    private ArrayList<Goal> list;

    public GoalList() {
        list = new ArrayList<Goal>();
    }

    public void append(Goal goal) {
        list.add(goal);
    }

    public void remove(Goal goal) {
        list.remove(goal);
    }

    public void prepend(Goal goal) {
        list.add(0, goal);
    }

    public ArrayList<Goal> getList() {
        return list;
    }


    // Retrieve all completed goals
    public List<Goal> getCompletedGoals() {
        return list.stream()
                .filter(Goal::isCompleted)
                .collect(Collectors.toList());
    }

    // Retrieve all unfinished goals
    public List<Goal> getUnfinishedGoals() {
        return list.stream()
                .filter(goal -> !goal.isCompleted())
                .collect(Collectors.toList());
    }

    // Find a goal by ID
    public Goal findGoalById(Integer id) {
        return list.stream()
                .filter(goal -> goal.id() != null && goal.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
