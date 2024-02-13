package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

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
}