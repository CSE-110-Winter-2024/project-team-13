package edu.ucsd.cse110.successorator.app;

import edu.ucsd.cse110.successorator.lib.domain.GoalList;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import androidx.lifecycle.ViewModelProvider;
public class GoalCheck {
    public GoalCheck() {}
    public void goalCheck(GoalList goals, Calendar today, MainViewModel mainViewModel){
        for(Goal goal : goals.getList()) {
            if (goal.recursionType().equals("daily")) {
                goal.setIsCompleted(false);
                goal.setLastUpdated(Calendar.getInstance());
                mainViewModel.endOfIncompleted(goal);
                goals.remove(goal);
            } else if (goal.recursionType().equals("weekly")) {
                if (goal.date().equals(
                        String.valueOf(new SimpleDateFormat("EEEE").format(today.getTime())))) {
                    goal.setIsCompleted(false);
                    goal.setLastUpdated(Calendar.getInstance());
                    mainViewModel.endOfIncompleted(goal);
                    goals.remove(goal);
                }
            } else if (goal.recursionType().equals("monthly")) {
                String numRepeated = String.valueOf(((today.get(Calendar.DAY_OF_MONTH)) - 1 / 7) + 1);
                String day = String.valueOf(new SimpleDateFormat("EEEE").format(today.getTime()));
                if (numRepeated.equals(goal.date().substring(0, 2))
                        && day.equals(goal.date().substring(2))) {
                    goal.setIsCompleted(false);
                    goal.setLastUpdated(Calendar.getInstance());
                    mainViewModel.endOfIncompleted(goal);
                    goals.remove(goal);
                }
            } else if (goal.recursionType().equals("yearly")
                    && goal.date().equals(
                    String.valueOf(new SimpleDateFormat("ddMM").format(today.getTime())))) {
                    goal.setIsCompleted(false);
                    goal.setLastUpdated(Calendar.getInstance());
                    mainViewModel.endOfIncompleted(goal);
                    goals.remove(goal);
            }
        }
    }
}
