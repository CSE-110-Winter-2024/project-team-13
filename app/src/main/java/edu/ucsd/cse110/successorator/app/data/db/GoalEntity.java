package edu.ucsd.cse110.successorator.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Calendar;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

@Entity(tableName = "goals")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "is_completed")
    public boolean isCompleted;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    @ColumnInfo(name = "last_updated")
    public long lastUpdated;

    @ColumnInfo(name = "recursion_type")
    public String recursionType;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "visibility")
    public int visibility;

    @ColumnInfo(name = "pending")
    public boolean pending;

    @ColumnInfo(name = "recurring")
    public boolean recurring;

    @ColumnInfo(name = "context")
    public int context;

    GoalEntity(@NonNull String title, int sortOrder) {
        this.title = title;
        this.sortOrder = sortOrder;
        this.isCompleted = false;
        this.lastUpdated = Calendar.getInstance().getTimeInMillis();
        this.recursionType = "oneTime";
        this.date = "0";
        this.visibility = 0;
        this.pending = false;
        this.recurring = false;
        this.context = 0;
    }

    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var card = new GoalEntity(goal.title(), goal.sortOrder());
        card.id = goal.id();
        card.isCompleted = goal.isCompleted();
        card.lastUpdated = goal.getLastUpdated().getTimeInMillis();
        card.recursionType = goal.recursionType();
        card.date = goal.date();
        card.visibility = goal.visibility();
        card.pending = goal.isPending();
        card.recurring = goal.isRecurring();
        card.context = goal.context();
        return card;
    }

    public @NonNull Goal toGoal() {
        Goal goal = new Goal(id, title, sortOrder);
        goal.setIsCompleted(isCompleted);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastUpdated);
        goal.setLastUpdated(calendar);
        goal.setRecursionType(recursionType);
        goal.setDate(date);
        goal.setVisibility(visibility);
        goal.setPending(pending);
        goal.setRecurring(recurring);
        goal.setContext(context);
        return goal;
    }
}