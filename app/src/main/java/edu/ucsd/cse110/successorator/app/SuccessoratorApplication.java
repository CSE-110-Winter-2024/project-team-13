package edu.ucsd.cse110.successorator.app;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.successorator.app.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.app.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

public class SuccessoratorApplication extends Application {
    private InMemoryDataSource dataSource;
    private GoalRepository goalRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        var database = Room.databaseBuilder(getApplicationContext(), SuccessoratorDatabase.class, "successorator-database")
                .allowMainThreadQueries()
                .build();

        this.goalRepository = new RoomGoalRepository(database.goalsDao());

        // Populate the database with some initial data on the first run.
        var sharedPreferences = getSharedPreferences("successorator", MODE_PRIVATE);
        var isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if(isFirstRun && database.goalsDao().count() == 0) {
            goalRepository.save(InMemoryDataSource.DEFAULT_GOALS);

            sharedPreferences.edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }

    public GoalRepository getGoalRepository() {
        return goalRepository;
    }
}
