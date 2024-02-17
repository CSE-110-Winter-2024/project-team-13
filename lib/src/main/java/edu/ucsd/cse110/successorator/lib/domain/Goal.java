package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Goal {
    private final @Nullable Integer id;
    private final @NonNull String title;
    private boolean isCompleted;

    private final @NonNull Integer sortOrder;
    private long lastUpdated;


    public Goal(@Nullable Integer id, @NonNull String title, @NonNull Integer sortOrder) {
        this.id = id;
        this.title = title;
        this.isCompleted = false;
        this.sortOrder = sortOrder;
        this.lastUpdated = Calendar.getInstance().getTimeInMillis();
    }

    public @Nullable Integer id() {
        return id;
    }

    public @NonNull String title() {
        return title;
    }

    public int sortOrder() { return sortOrder; }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed){
        isCompleted = completed;
    }

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
    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
