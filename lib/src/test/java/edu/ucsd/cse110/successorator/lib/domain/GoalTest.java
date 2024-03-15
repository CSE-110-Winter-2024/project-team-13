package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.jetbrains.annotations.TestOnly;
import org.junit.Test;

import java.util.Calendar;

public class GoalTest {

    @Test
    public void id() {
        Goal goal = new Goal(1, "title", 1);
        assertEquals((Integer) 1, goal.id());
    }

    @Test
    public void title() {
        Goal goal = new Goal(1, "title", 1);
        assertEquals("title", goal.title());
    }

    @Test
    public void SetIsComplete() {
        Goal goal = new Goal(1, "title", 1);
        goal.setIsCompleted(true);
        assertTrue(goal.isCompleted());
    }

    @Test
    public void sortOrder() {
        Goal goal = new Goal(1, "title", 1);
        assertEquals(1, goal.sortOrder());
    }

    @Test
    public void withId() {
        Goal goal = new Goal(1, "title", 1);
        assertEquals(new Goal(2, "title", 1), goal.withId(2));
    }

    @Test
    public void withSortOrder() {
        Goal goal = new Goal(1, "title", 1);
        assertEquals(new Goal(1, "title", 2), goal.withSortOrder(2));
    }

    @Test
    public void goalCreationSetsLastUpdatedToCurrentTime() {
        Calendar beforeCreation = Calendar.getInstance();

        // Simulate a short delay
        try {
            Thread.sleep(100); // 100 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Goal goal = new Goal(1, "New Goal", 1);

        Calendar afterCreation = Calendar.getInstance();

        // Check that the goal's lastUpdated time is after beforeCreation and before afterCreation
        assertTrue(goal.getLastUpdated().after(beforeCreation) || goal.getLastUpdated().equals(beforeCreation));
        assertTrue(goal.getLastUpdated().before(afterCreation) || goal.getLastUpdated().equals(afterCreation));
    }

    @Test
    public void goalRecursionTypeAndDate() {
        Goal goal = new Goal(1, "Recurring Goal", 1);
        goal.setRecursionType("daily");
        goal.setDate("2024-03-01");
        assertEquals("daily", goal.recursionType());
        assertEquals("2024-03-01", goal.date());
    }

    @Test
    public void goalPendingStatus() {
        Goal goal = new Goal(1, "Pending Goal", 1);
        goal.setPending(true);
        assertTrue(goal.isPending());
    }

    @Test
    public void recurringAndPendingGoalIntegration() {
        Goal goal = new Goal(null, "Future Goal", 1);
        goal.setRecursionType("weekly");
        goal.setDate("2024-03-07");
        goal.setPending(true);
        assertEquals("weekly", goal.recursionType());
        assertEquals("2024-03-07", goal.date());
        assertTrue(goal.isPending());
    }

    @Test
    public void recurringGoalAttributes() {
        Goal goal = new Goal(1, "Weekly Meeting", 1);
        goal.setRecursionType("weekly");
        goal.setDate("2024-03-01"); // Assuming this format for simplicity
        goal.setRecurring(true);

        assertTrue(goal.isRecurring());
        assertEquals("weekly", goal.recursionType());
        assertEquals("2024-03-01", goal.date());
    }

    @Test
    public void goalVisibility() {
        Goal goal = new Goal(1, "Visible Goal", 1);
        goal.setVisibility(1);
        assertEquals(1, goal.visibility());
    }

    @Test
    public void goalContext() {
        Goal goal = new Goal(1, "Context Goal", 1);
        goal.setContext(1);
        assertEquals(1, goal.context());
    }

}