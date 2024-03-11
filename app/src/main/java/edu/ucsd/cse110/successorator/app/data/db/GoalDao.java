package edu.ucsd.cse110.successorator.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

@Dao
public interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GoalEntity goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<GoalEntity> goals);

    @Query("SELECT * FROM goals WHERE id = :id")
    GoalEntity find(int id);

    @Query("SELECT * FROM goals ORDER BY sort_order")
    List<GoalEntity> findAll();

    @Query("SELECT * FROM goals WHERE recursion_type != 'oneTime'")
    List<GoalEntity> getRecursive();

    @Query("SELECT * FROM goals WHERE pending")
    List<GoalEntity> getPending();

//    @Query("SELECT * FROM goals WHERE date = :date")
//    List<GoalEntity> getGoalsOfDate(String date);

    @Query("SELECT * FROM goals WHERE id = :id")
    LiveData<GoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM goals ORDER BY sort_order")
    LiveData<List<GoalEntity>> findAllAsLiveData();

    @Query("SELECT COUNT(*) FROM goals")
    int count();

    @Query("SELECT MIN(sort_order) FROM goals")
    int getMinSortOrder();

    @Query("SELECT MAX(sort_order) FROM goals")
    int getMaxSortOrder();

    @Query("SELECT sort_order FROM goals WHERE id = :id")
    int getSortOrder(int id);

    @Query("UPDATE goals SET sort_order = sort_order + :by " + "WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    @Transaction
    default int append(GoalEntity goal) {
        var maxSortOrder = getMaxSortOrder();
        var newGoal = new GoalEntity(goal.title,maxSortOrder + 1);
        newGoal.isCompleted = goal.isCompleted;
        newGoal.lastUpdated = goal.lastUpdated;
        newGoal.recursionType = goal.recursionType;
        newGoal.date = goal.date;
        newGoal.visibility = goal.visibility;
        newGoal.pending = goal.pending;
        newGoal.context = goal.context;
        return Math.toIntExact(insert(newGoal));
    }

    @Transaction
    default int endOfIncompleted(GoalEntity goal, int sOrder) {
        var maxSortOrder = getMaxSortOrder();
        var newGoal = new GoalEntity(goal.title,sOrder);
        shiftSortOrders(sOrder, getMaxSortOrder(), 1);
        newGoal.isCompleted = goal.isCompleted;
        newGoal.lastUpdated = goal.lastUpdated;
        newGoal.recursionType = goal.recursionType;
        newGoal.date = goal.date;
        newGoal.visibility = goal.visibility;
        newGoal.pending = goal.pending;
        newGoal.context = goal.context;
        return Math.toIntExact(insert(newGoal));
    }

    @Transaction
    default int startOfRecursive(GoalEntity goal, int sOrder) {
        var maxSortOrder = getMaxSortOrder();
        var newGoal = new GoalEntity(goal.title,sOrder);
        shiftSortOrders(sOrder, getMaxSortOrder(), 1);
        newGoal.isCompleted = goal.isCompleted;
        newGoal.lastUpdated = goal.lastUpdated;
        newGoal.recursionType = goal.recursionType;
        newGoal.date = goal.date;
        newGoal.visibility = goal.visibility;
        newGoal.pending = goal.pending;
        newGoal.context = goal.context;
        return Math.toIntExact(insert(newGoal));
    }

    @Transaction
    default int prepend(GoalEntity goal) {
        shiftSortOrders(getMinSortOrder(), getMaxSortOrder(), 1);
        var newGoal = new GoalEntity(goal.title, getMinSortOrder() - 1);
        newGoal.isCompleted = goal.isCompleted;
        newGoal.lastUpdated = goal.lastUpdated;
        newGoal.recursionType = goal.recursionType;
        newGoal.date = goal.date;
        newGoal.visibility = goal.visibility;
        newGoal.pending = goal.pending;
        newGoal.context = goal.context;
        return Math.toIntExact(insert(newGoal));
    }

    @Transaction
    default void remove(int id){
        GoalEntity goal = find(id);
        int sOrder = goal.sortOrder;
        delete(id);
        shiftSortOrders(sOrder+1, getMaxSortOrder(), -1);
    }

    @Query("DELETE FROM goals WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM goals")
    void deleteAll();
}