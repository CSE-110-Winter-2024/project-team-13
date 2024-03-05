package edu.ucsd.cse110.successorator.lib.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class InMemoryDataSource {
    private int nextId = 0;

    private int minSortOrder = Integer.MAX_VALUE;
    private int maxSortOrder = Integer.MIN_VALUE;

    private final Map<Integer, Goal> goals
            = new HashMap<>();
    private final Map<Integer, SimpleSubject<Goal>> goalSubjects
            = new HashMap<>();
    private final SimpleSubject<List<Goal>> allGoalsSubject
            = new SimpleSubject<>();

    public InMemoryDataSource() {
    }

    public final static List<Goal> DEFAULT_GOALS = List.of(
            new Goal(0, "Wake Up", 0),
            new Goal(1, "Brush Teeth", 1),
            new Goal(2, "Eat Breakfast", 2),
            new Goal(3, "Go to Class", 3),
            new Goal(4, "Go to Work", 4),
            new Goal(5, "Do Homework", 5)
    );

    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();
        data.putGoals(DEFAULT_GOALS);
        return data;
    }

    public List<Goal> getGoals() {
        return List.copyOf(goals.values());
    }

    public Goal getGoal(int id) {
        return goals.get(id);
    }

    public SimpleSubject<Goal> getGoalSubject(int id) {
        if (!goalSubjects.containsKey(id)) {
            var subject = new SimpleSubject<Goal>();
            subject.setValue(getGoal(id));
            goalSubjects.put(id, subject);
        }
        return goalSubjects.get(id);
    }

    public SimpleSubject<List<Goal>> getAllGoalsSubject() {
        return allGoalsSubject;
    }

    public int getMinSortOrder() {
        return minSortOrder;
    }

    public int getMaxSortOrder() {
        return maxSortOrder;
    }

    public void putGoal(Goal goal) {
        var fixedGoal = preInsert(goal);

        goals.put(fixedGoal.id(), fixedGoal);
        postInsert();
        assertSortOrderConstraints();

        if (goalSubjects.containsKey(fixedGoal.id())) {
            goalSubjects.get(fixedGoal.id()).setValue(fixedGoal);
        }
        allGoalsSubject.setValue(getGoals());
    }

    public void putGoals(List<Goal> cards) {
        var fixedGoals = cards.stream()
            .map(this::preInsert)
            .collect(Collectors.toList());

        fixedGoals.forEach(goal -> goals.put(goal.id(), goal));
        postInsert();
        assertSortOrderConstraints();

        fixedGoals.forEach(goal -> {
            if (goalSubjects.containsKey(goal.id())) {
                goalSubjects.get(goal.id()).setValue(goal);
            }
        });
        allGoalsSubject.setValue(getGoals());
    }

    public void removeGoal(int id) {
        var goal = goals.get(id);
        var sortOrder = goal.sortOrder();

        goals.remove(id);
        shiftSortOrders(sortOrder, maxSortOrder, -1);

        if (goalSubjects.containsKey(id)) {
            goalSubjects.get(id).setValue(null);
        }
        allGoalsSubject.setValue(getGoals());
    }

    public void shiftSortOrders(int from, int to, int by) {
        var cards = goals.values().stream()
                .filter(goal -> goal.sortOrder() >= from && goal.sortOrder() <= to)
                .map(goal -> goal.withSortOrder(goal.sortOrder() + by))
                .collect(Collectors.toList());

        putGoals(cards);
    }

    private Goal preInsert(Goal goal) {
        var id = goal.id();
        if (id == null) {
            goal = goal.withId(nextId++);
        }
        else if (id > nextId) {
            nextId = id + 1;
        }

        return goal;
    }

    private void postInsert() {
        minSortOrder = goals.values().stream()
                .map(Goal::sortOrder)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);

        maxSortOrder = goals.values().stream()
                .map(Goal::sortOrder)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
    }

    private void assertSortOrderConstraints() {
        var sortOrders = goals.values().stream()
                .map(Goal::sortOrder)
                .collect(Collectors.toList());

        assert sortOrders.stream().allMatch(i -> i >= 0);

        assert sortOrders.size() == sortOrders.stream().distinct().count();

        assert sortOrders.stream().allMatch(i -> i >= minSortOrder);
        assert sortOrders.stream().allMatch(i -> i <= maxSortOrder);
    }

    public void clearGoals() {
        goals.clear();
    }
}
