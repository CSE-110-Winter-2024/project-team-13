package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Goal {
    private final @Nullable Integer id;
    private final @NonNull String title;
    private boolean isCompleted;

    public Goal(@Nullable Integer id, @NonNull String title) {
        this.id = id;
        this.title = title;
        this.isCompleted = false;
    }

    public @Nullable Integer id() {
        return id;
    }

    public @NonNull String title() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isCompleted == goal.isCompleted && Objects.equals(id, goal.id) && Objects.equals(title, goal.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isCompleted);
    }
}
