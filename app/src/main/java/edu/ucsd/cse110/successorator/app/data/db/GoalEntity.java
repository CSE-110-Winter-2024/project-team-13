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

//    @ColumnInfo(name = "last_updated")
//    public long lastUpdated;

    GoalEntity(@NonNull String title, int sortOrder) {
        this.title = title;
        this.sortOrder = sortOrder;
        this.isCompleted = false;
//        this.lastUpdated = Calendar.getInstance().getTimeInMillis();
    }

    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var card = new GoalEntity(goal.title(), goal.sortOrder());
        card.id = goal.id();
        card.isCompleted = goal.isCompleted();
//        card.lastUpdated = goal.getLastUpdated().getTimeInMillis();
        return card;
    }

    public @NonNull Goal toGoal() {
        Goal goal = new Goal(id, title, sortOrder);
        goal.setIsCompleted(isCompleted);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(lastUpdated);
//        goal.setLastUpdated(calendar);
        return goal;
    }
}