package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface GoalRepository {
    Subject<Goal> find(int id);

    Subject<List<Goal>> findAll();

    List<Goal> findAllList();

    List<Goal> getRecursive();

    List<Goal> getPending();

//    List<Goal> getGoalsOfDate(String date);

    void save(Goal goal);

    void save(List<Goal> goals);

    void prepend(Goal goal);

    void remove(int id);

    void append(Goal goal);

    void endOfIncompleted(Goal goal);

    void startOfRecursive(Goal goal);

    void removeAll();
}
