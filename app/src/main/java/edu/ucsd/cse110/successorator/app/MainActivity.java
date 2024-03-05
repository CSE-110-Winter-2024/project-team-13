package edu.ucsd.cse110.successorator.app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    private Calendar fakeDate = Calendar.getInstance();
    private GoalList  repGoals = new GoalList();
    private TextView dateTextView;
    private MainViewModel mainViewModel;
    private Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(view.getRoot());

        dateTextView = findViewById(R.id.date);

        view.dayforward.setOnClickListener(v -> {
            fakeDate.add(Calendar.DAY_OF_MONTH, 1);
            fakeDate.set(Calendar.HOUR_OF_DAY, 2);
            fakeDate.set(Calendar.MINUTE, 0);
            fakeDate.set(Calendar.SECOND, 0);
            fakeDate.set(Calendar.MILLISECOND, 0);
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.removeOutdatedCompletedGoals(Calendar.getInstance());
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar realDate = Calendar.getInstance();
                                dateTextView.setText(String.valueOf(new SimpleDateFormat("EEEE, M/dd").format(fakeDate.getTime())));
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
                } catch (InterruptedException e) { }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        fakeDate = Calendar.getInstance();
        MockDate mockDate = new MockDate(fakeDate, dateTextView, mainViewModel, repGoals);
        thread = mockDate.getMockDate();
        thread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        thread.interrupt();

    }
    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.action_bar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        var itemId = item.getItemId();
//
//        if(itemId == R.id.action_bar_menu_swap_views) {
//            swapFragments();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private void swapFragments() {
////        if(isShowingGoal) {
////            getSupportFragmentManager()
////                .beginTransaction()
////                .replace(R.id.fragment_container, GoalListFragment.newInstance())
////                .commit();
////        } else {
////            getSupportFragmentManager()
////                .beginTransaction()
////                .replace(R.id.fragment_container, GoalFragment.newInstance())
////                .commit();
////        }
////        isShowingGoal = !isShowingGoal;
//    }
}
