package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

import java.util.Objects;

/**
 * Goal class that contains the description of the goal.
 */
public class Goal implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String title;
    private final @NonNull boolean isCompleted;
    private final @NonNull Integer sortOrder;

    public Goal(
        @Nullable Integer id,
        @NonNull String title,
        @NonNull Integer sortOrder
    ) {
        this.id = id;
        this.title = title;
        this.isCompleted = false;
        this.sortOrder = sortOrder;
    }

    @Nullable
    public Integer id() {
        return id;
    }

    @NonNull
    public String title() {
        return title;
    }

    public int sortOrder() { return sortOrder; }

    public Goal withId(int id) {
        return new Goal(id, this.title, this.sortOrder);
    }

    public Goal withSortOrder(int sortOrder) {
        return new Goal(this.id, this.title, sortOrder);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isCompleted == goal.isCompleted && Objects.equals(id, goal.id)
                && Objects.equals(title, goal.title) && Objects.equals(sortOrder, goal.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isCompleted, sortOrder);
    }
}
