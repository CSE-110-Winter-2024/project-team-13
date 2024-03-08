package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

// file is not needed because we have the room repository now
public class SimpleGoalRepository implements GoalRepository {
    private final InMemoryDataSource dataSource;

    public SimpleGoalRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject<Goal> find(int id) {
        return dataSource.getGoalSubject(id);
    }

    @Override
    public Subject<List<Goal>> findAll() {
        return dataSource.getAllGoalsSubject();
    }

    @Override
    public List<Goal> findAllList() {
        return dataSource.getGoals();
    }

    @Override
    public void save(Goal goal) {
        dataSource.putGoal(goal);
    }

    @Override
    public void save(List<Goal> goals) {
        dataSource.putGoals(goals);
    }

    @Override
    public void remove(int id) {
        dataSource.removeGoal(id);
    }

    @Override
    public void append(Goal goal) {
        dataSource.putGoal(
            goal.withSortOrder(dataSource.getMaxSortOrder() + 1)
        );
    }

    @Override
    public void prepend(Goal goal) {
        // Shift all the existing cards up by one.
        dataSource.shiftSortOrders(0, dataSource.getMaxSortOrder(), 1);
        // Then insert the new card before the first one.
        dataSource.putGoal(
                goal.withSortOrder(dataSource.getMinSortOrder() - 1)
        );
    }

    public void endOfIncompleted(Goal goal) {
        List<Goal> list = dataSource.getGoals();
        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isCompleted()) {
                counter++;
            } else {
                break;
            }
        }
        dataSource.shiftSortOrders(counter+1, dataSource.getMaxSortOrder(), 1);
        dataSource.putGoal(goal.withSortOrder(counter));
    }

    public void startOfRecursive(Goal goal) {
        List<Goal> list = dataSource.getGoals();
        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).visibility() != 8) {
                counter++;
            } else {
                break;
            }
        }
        dataSource.shiftSortOrders(counter+1, dataSource.getMaxSortOrder(), 1);
        dataSource.putGoal(goal.withSortOrder(counter));
    }

    @Override
    public void removeAll() {
        dataSource.clearGoals();
    }
}
