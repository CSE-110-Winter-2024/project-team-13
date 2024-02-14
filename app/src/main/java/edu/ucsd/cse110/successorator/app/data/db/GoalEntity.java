package edu.ucsd.cse110.successorator.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

@Entity(tableName = "goals")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    GoalEntity(@NonNull String title, int sortOrder) {
        this.title = title;
        this.sortOrder = sortOrder;
    }

    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var card = new GoalEntity(goal.title(), goal.sortOrder());
        card.id = goal.id();
        return card;
    }

    public @NonNull Goal toGoal() {
        return new Goal(id, title, sortOrder);
    }
}