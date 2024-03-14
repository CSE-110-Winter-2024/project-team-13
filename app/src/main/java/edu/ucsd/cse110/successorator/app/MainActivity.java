package edu.ucsd.cse110.successorator.app;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.app.util.ViewSpinnerDateUpdater;
import edu.ucsd.cse110.successorator.lib.domain.GoalList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    private Calendar fakeDate = Calendar.getInstance();
    private GoalList  repGoals = new GoalList();
    private TextView dateTextView;

    private Spinner viewSpinnerView;
    private MainViewModel mainViewModel;
    private Thread thread;
    private MockDate mockDate;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(view.getRoot());

        dateTextView = findViewById(R.id.date);
        viewSpinnerView = findViewById(R.id.view_spinner);

        view.dayforward.setOnClickListener(v -> {
            fakeDate.add(Calendar.DAY_OF_MONTH, 1);
            fakeDate.set(Calendar.HOUR_OF_DAY, 2);
            fakeDate.set(Calendar.MINUTE, 0);
            fakeDate.set(Calendar.SECOND, 0);
            fakeDate.set(Calendar.MILLISECOND, 0);
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.removeOutdatedCompletedGoals(Calendar.getInstance());


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.burger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);

        TextView tester = findViewById(R.id.testerText);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                tester.setText(item.getTitle());
                return true;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        fakeDate = Calendar.getInstance();
        mockDate = new MockDate(fakeDate, dateTextView, mainViewModel, repGoals);
        ViewSpinnerDateUpdater updater = new ViewSpinnerDateUpdater(mockDate, mainViewModel);
        ViewSpinner viewSpinner = new ViewSpinner(this, viewSpinnerView, mainViewModel, updater);
        viewSpinnerView.setOnItemSelectedListener(viewSpinner.eventHandler());
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
