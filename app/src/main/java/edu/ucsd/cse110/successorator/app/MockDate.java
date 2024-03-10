package edu.ucsd.cse110.successorator.app;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;



public class MockDate extends AppCompatActivity {
    private Calendar fakeDate;
    private TextView dateTextView;
    private MainViewModel mainViewModel;
    private GoalList repGoals;
    private GoalCheck goalCheck;

    private String viewSetting;

    public MockDate(Calendar fakeDate, TextView dateTextView, MainViewModel mainViewModel, GoalList repGoals) {
        this.fakeDate = fakeDate;
        this.dateTextView = dateTextView;
        this.mainViewModel = mainViewModel;
        this.repGoals = repGoals;
        this.goalCheck = new GoalCheck();
        this.viewSetting = "Today";
    }

    public void setViewSetting(String viewSetting) {
        this.viewSetting = viewSetting;
    }


    public Thread getMockDate() {
        Thread mockDate = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar realDate = Calendar.getInstance();
                                mainViewModel.setDateInstance(fakeDate);
                                displayText();
                                if (fakeDate.get(Calendar.HOUR_OF_DAY) == 2 && fakeDate.get(Calendar.MINUTE) == 0 && fakeDate.get(Calendar.SECOND) == 0) {
                                    fakeDate.add(Calendar.SECOND, 1);
                                    mainViewModel.removeOutdatedCompletedGoals(fakeDate);
                                }
                                if (realDate.get(Calendar.HOUR_OF_DAY) == 2 && realDate.get(Calendar.MINUTE) == 0 && realDate.get(Calendar.SECOND) == 0) {
                                    mainViewModel.removeOutdatedCompletedGoals(realDate);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };
        return mockDate;
    }

    public void displayText() {
        if (viewSetting.equals("Today")) {
            dateTextView.setText(viewSetting + String.valueOf(new SimpleDateFormat(", M/dd").format(fakeDate.getTime())));
        } else if (viewSetting.equals("Tomorrow")) {
            Calendar tomorrow = (Calendar) fakeDate.clone();
            tomorrow.add(Calendar.DATE, 1);
            dateTextView.setText(viewSetting + String.valueOf(new SimpleDateFormat(", M/dd").format(tomorrow.getTime())));
        } else {
            dateTextView.setText(viewSetting);
        }

    }
}
