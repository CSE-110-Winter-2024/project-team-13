package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoalListTest {

    @Test
    public void append() {
        GoalList goalList = new GoalList();
        Goal goal = new Goal(1, "title", 1);

        goalList.append(goal);

        assertEquals(goalList.getList().get(0), goal);

        Goal goal1 = new Goal(2, "title1", 2);
        goalList.append(goal1);

        assertEquals(goalList.getList().get(1), goal1);
    }

    @Test
    public void remove() {
        GoalList goalList = new GoalList();
        Goal goal = new Goal(1, "title", 1);
        Goal goal1 = new Goal(2, "title1", 2);

        goalList.append(goal);
        goalList.append(goal1);

        goalList.remove(goal);

        assertEquals(goalList.getList().get(0), goal1);
    }

    @Test
    public void prepend() {
        GoalList goalList = new GoalList();
        Goal goal = new Goal(1, "title", 1);

        goalList.prepend(goal);

        assertEquals(goalList.getList().get(0), goal);

        Goal goal1 = new Goal(2, "title1", 2);
        goalList.prepend(goal1);

        assertEquals(goalList.getList().get(0), goal1);
    }

    @Test
    public void getCompletedGoals() {
        GoalList goalList = new GoalList();
        Goal completedGoal = new Goal(1, "Finished task", 1);
        Goal unfinishedGoal = new Goal(2, "Unfinished task", 2);
        completedGoal.setIsCompleted(true);
        goalList.append(completedGoal);
        goalList.append(unfinishedGoal);

        assertEquals(1, goalList.getCompletedGoals().size());
        assertTrue(goalList.getCompletedGoals().contains(completedGoal));
        assertFalse(goalList.getCompletedGoals().contains(unfinishedGoal));
    }

    @Test
    public void getUnfinishedGoals() {
        GoalList goalList = new GoalList();
        Goal completedGoal = new Goal(1, "Finished task", 1);
        Goal unfinishedGoal = new Goal(2, "Unfinished task", 2);
        completedGoal.setIsCompleted(true);
        goalList.append(completedGoal);
        goalList.append(unfinishedGoal);

        assertEquals(1, goalList.getUnfinishedGoals().size());
        assertTrue(goalList.getUnfinishedGoals().contains(unfinishedGoal));
        assertFalse(goalList.getUnfinishedGoals().contains(completedGoal));
    }

    @Test
    public void findGoalById() {
        GoalList goalList = new GoalList();
        Goal goal = new Goal(1, "Find this goal", 1);
        goalList.append(goal);

        assertNotNull(goalList.findGoalById(1));
        assertEquals(goal, goalList.findGoalById(1));
        assertNull(goalList.findGoalById(99)); // Test for a non-existent ID
    }
}