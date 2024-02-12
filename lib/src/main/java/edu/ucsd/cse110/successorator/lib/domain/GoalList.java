package edu.ucsd.cse110.successorator.lib.domain;

import java.util.ArrayList;

public class GoalList {

    ArrayList<Goal> list;

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
}
