package edu.ucsd.cse110.successorator.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final GoalRepository goalRepository;

    // UI state
    private final SimpleSubject<List<Goal>> orderedGoals;
    private final SimpleSubject<Goal> topGoal;
    private final SimpleSubject<String> displayedText;

    public static final ViewModelInitializer<MainViewModel> initializer =
        new ViewModelInitializer<>(
            MainViewModel.class,
            creationExtras -> {
                var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return  new MainViewModel(app.getGoalRepository());
            });

    public MainViewModel(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;

        // Create the observable subjects.
        this.orderedGoals = new SimpleSubject<>();
        this.topGoal = new SimpleSubject<>();
        this.displayedText = new SimpleSubject<>();

        // When the list of cards changes (or is first loaded), reset the ordering.
        goalRepository.findAll().observe(goals -> {
            if (goals == null) return; // not ready yet, ignore

            var newOrderedGoals = goals.stream()
                .sorted(Comparator.comparingInt(Goal::sortOrder))
                .collect(Collectors.toList());

            orderedGoals.setValue(newOrderedGoals);
        });

        orderedGoals.observe(goals -> {
            if (goals == null || goals.size() == 0) return;
            var goal = goals.get(0);
            this.topGoal.setValue(goal);
        });

        topGoal.observe(goal -> {
            if (goal == null) return;

            displayedText.setValue(goal.title());
        });


    }

    public Subject<String> getDisplayedText() {
        return displayedText;
    }

    public Subject<List<Goal>> getOrderedGoals() {
        return orderedGoals;
    }

    public void append(Goal goal) {
        goalRepository.append(goal);
    }

    public void endOfIncompleted(Goal goal) {
        goalRepository.endOfIncompleted(goal);
    }

    public void prepend(Goal goal){ goalRepository.prepend(goal); }

    public void remove(int id) {
        goalRepository.remove(id);
    }

    public void removeOutdatedCompletedGoals(Calendar today) {

        // Get the list of all goals
        List<Goal> allGoals = goalRepository.findAllList();
        //  goal.getLastUpdated().getTime().before(today.getTime())
        // Iterate over the goals and remove completed ones that are outdated

        for (Goal goal : allGoals) {
            if (goal.isCompleted()) {
                Calendar goalDate = goal.getLastUpdated();
                if(!(goalDate.get(Calendar.HOUR_OF_DAY) < 2)) {
                    goalDate.add(Calendar.DAY_OF_MONTH, 1);
                }
                goalDate.set(Calendar.HOUR_OF_DAY, 1);
                goalDate.set(Calendar.MINUTE, 59);
                goalDate.set(Calendar.SECOND, 59);
                goalDate.set(Calendar.MILLISECOND, 0);

                Log.d("Expiration Date", goalDate.getTime().toString());
                Log.d("Today Date", today.getTime().toString());
                if(!goal.date().equals("0") && goal.visibility() == View.GONE){
                    if (goal.recursionType().equals("weekly")) {
                        if (goal.date().equals(
                                String.valueOf(new SimpleDateFormat("EEEE").format(today.getTime())))) {
                            goal.setIsCompleted(false);
                            goal.setLastUpdated(Calendar.getInstance());
                            goal.setVisibility(View.VISIBLE);
                            remove(goal.id());
                            endOfIncompleted(goal);
                        }
                    } else if (goal.recursionType().equals("monthly")) {
                        String numRepeated = String.valueOf(((today.get(Calendar.DAY_OF_MONTH)) - 1 / 7) + 1);
                        String day = String.valueOf(new SimpleDateFormat("EEEE").format(today.getTime()));
                        if (numRepeated.equals(goal.date().substring(0, 2))
                                && day.equals(goal.date().substring(2))) {
                            goal.setIsCompleted(false);
                            goal.setLastUpdated(Calendar.getInstance());
                            goal.setVisibility(View.VISIBLE);
                            remove(goal.id());
                            endOfIncompleted(goal);
                        }
                    } else if (goal.recursionType().equals("yearly")
                            && goal.date().equals(
                            String.valueOf(new SimpleDateFormat("ddMM").format(today.getTime())))) {
                        goal.setIsCompleted(false);
                        goal.setLastUpdated(Calendar.getInstance());
                        goal.setVisibility(View.VISIBLE);
                        remove(goal.id());
                        endOfIncompleted(goal);
                    }
                }
                else if(today.getTime().after(goalDate.getTime())) {
                    if(!goal.date().equals("0")) {
                        goal.setVisibility(View.GONE);
                        remove(goal.id());
                        append(goal);
                    }
                    else if(goal.recursionType().equals("daily")){
                        goal.setIsCompleted(false);
                        goal.setLastUpdated(Calendar.getInstance());
                        remove(goal.id());
                        endOfIncompleted(goal);
                    }
                    else {
                        remove(goal.id());
                    }
                }
            }
        }
    }

    public void removeAllGoals() {
        goalRepository.removeAll();
    }
}
