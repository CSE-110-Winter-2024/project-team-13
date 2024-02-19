package edu.ucsd.cse110.successorator.app;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

import edu.ucsd.cse110.successorator.app.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;

public class MainViewModelTest {

    @Test
    public void append() {
        var dataSource = InMemoryDataSource.fromDefault();
        var repo = new SimpleGoalRepository(dataSource);
        var model = new MainViewModel(repo);

        Goal goal = new Goal(6, "New Goal", 6);

        model.append(goal);
        List<Goal> l = dataSource.getGoals();
        assertEquals(goal, l.get(6));
    }

    @Test
    public void prepend() {
        var dataSource = InMemoryDataSource.fromDefault();
        var repo = new SimpleGoalRepository(dataSource);
        var model = new MainViewModel(repo);

        Goal goal = new Goal(6, "New Goal", 0);

        model.prepend(goal);
        List<Goal> l = dataSource.getGoals();
        assertEquals(7, l.size());

//        assertEquals((Integer) 6, l.get(0).id());
    }

    @Test
    public void remove() {
        var dataSource = InMemoryDataSource.fromDefault();
        var repo = new SimpleGoalRepository(dataSource);
        var model = new MainViewModel(repo);

        model.remove(0);

        assertEquals(5, dataSource.getGoals().size());
    }

    @Test
    public void removeAllGoals() {
        var dataSource = InMemoryDataSource.fromDefault();
        var repo = new SimpleGoalRepository(dataSource);
        var model = new MainViewModel(repo);

        model.removeAllGoals();

        assertEquals(0, dataSource.getGoals().size());
    }
}