package edu.ucsd.cse110.successorator.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.app.ui.goal.GoalFragment;
import edu.ucsd.cse110.successorator.app.ui.goallist.GoalListFragment;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        Calendar calendar = Calendar.getInstance();

        var dateFormat = new SimpleDateFormat("EEEE, M/dd").format(calendar.getTime());
        TextView dateTextView = findViewById(R.id.date);
        dateTextView.setText(dateFormat);

        // Get the MainViewModel instance
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Perform the cleanup of completed goals
        mainViewModel.removeOutdatedCompletedGoals();

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
