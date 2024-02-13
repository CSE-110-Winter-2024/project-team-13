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
}