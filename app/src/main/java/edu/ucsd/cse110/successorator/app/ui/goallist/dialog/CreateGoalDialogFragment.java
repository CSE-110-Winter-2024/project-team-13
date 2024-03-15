package edu.ucsd.cse110.successorator.app.ui.goallist.dialog;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.app.MainActivity;
import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.R;
import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.app.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.app.databinding.FragmentDialogCreateGoalPendingBinding;
import edu.ucsd.cse110.successorator.app.databinding.FragmentDialogCreateGoalRecurringBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateGoalDialogFragment extends DialogFragment {
    private MainViewModel activityModel;
    private FragmentDialogCreateGoalBinding defaultView;
    private FragmentDialogCreateGoalPendingBinding pendingView;
    private FragmentDialogCreateGoalRecurringBinding recurringView;
    private String viewSetting;

    CreateGoalDialogFragment(){}

    public static CreateGoalDialogFragment newInstance(String viewSetting) {
        var fragment = new CreateGoalDialogFragment();
        Bundle args = new Bundle();
        args.putString("viewSetting", viewSetting);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        this.viewSetting = getArguments() != null ? getArguments().getString("viewSetting", "") : "";
        // Dynamically decide which layout to use
        if ("Pending".equals(viewSetting)) {
            pendingView = FragmentDialogCreateGoalPendingBinding.inflate(inflater);
            configurePendingView(pendingView);
            builder.setView(pendingView.getRoot());
        } else if ("Recurring".equals(viewSetting)) {
            recurringView = FragmentDialogCreateGoalRecurringBinding.inflate(inflater);
            configureRecurringView(recurringView);
            builder.setView(recurringView.getRoot());
        } else {
            defaultView = FragmentDialogCreateGoalBinding.inflate(inflater);
            configureDefaultView(defaultView);
            builder.setView(defaultView.getRoot());
        }
        builder.setTitle("New Goal")
                .setMessage("Please enter your goal.")
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick);
        return builder.create();
    }


    private void configureRecurringView(FragmentDialogCreateGoalRecurringBinding view) {
        Calendar cal = MainViewModel.getCal();
        if(cal == null){
            cal = Calendar.getInstance();
        }
        //Creates text with day of the week
        String weeklyMsg = "Weekly...";
        view.weekly.setText(weeklyMsg);
        //Creates text with day and which number day this is eg: 3rd Tuesday
        String monthlyMsg = "Monthly...";
        view.monthly.setText(monthlyMsg);
        //Creates text with month and year
        String yearlyMsg = "Yearly...";
        view.yearly.setText(yearlyMsg);

    }

    private void configurePendingView(FragmentDialogCreateGoalPendingBinding view) {
        //Do Nothing.
    }

    private void configureDefaultView(FragmentDialogCreateGoalBinding view) {
        Calendar cal = MainViewModel.getCal();
        if(cal == null){
            cal = Calendar.getInstance();
        }
        //Creates text with day of the week
        String weeklyMsg = "Weekly on " +
                String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime()));
        view.weekly.setText(weeklyMsg);
        //Creates text with day and which number day this is eg: 3rd Tuesday
        int week = cal.get(Calendar.DAY_OF_MONTH);
        //number of times the specific day has repeated this month
        int numRepeated = ((week-1)/7)+1;
        String monthlyMsg;
        //sets monthlyMsg according to number suffix eg: 3rd
        switch(numRepeated) {
            case 1:
                monthlyMsg = "Monthly on " + numRepeated + "st "
                        + String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime()));
                break;
            case 2:
                monthlyMsg = "Monthly on " + numRepeated + "nd "
                        + String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime()));
                break;
            case 3:
                monthlyMsg = "Monthly on " + numRepeated + "rd "
                        + String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime()));
                break;
            default:
                monthlyMsg = "Monthly on " + numRepeated + "th "
                        + String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime()));
                break;
        }
        view.monthly.setText(monthlyMsg);
        //Creates text with month and year
        String yearlyMsg = "Yearly on " + String.valueOf(new SimpleDateFormat("MM/dd").format(cal.getTime()));
        view.yearly.setText(yearlyMsg);
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {

        String title;
        Calendar cal = MainViewModel.getCal();
        Calendar date = Calendar.getInstance();
        String month = recurringView.monthField.getText().toString();
        String day = recurringView.dayField.getText().toString();
        String year = recurringView.yearField.getText().toString();

        date.set(Calendar.MONTH, parseInt(month));
        date.set(Calendar.DAY_OF_MONTH, parseInt(day));
        date.set(Calendar.YEAR, parseInt(year));
        Goal goal;
        switch (viewSetting) {
            case "Pending":
                title = pendingView.goalTitleText.getText().toString();
                goal = new Goal(null, title, -1);
                goal.setPending(true);
                break;
            case "Recurring":
                title = recurringView.goalTitleText.getText().toString();
                if(recurringView.daily.isChecked()){
                    //goal daily recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("daily");
                }
                else if(recurringView.weekly.isChecked()){
                    //goal weekly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("weekly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("EEEE").format(date.getTime())));
                }
                else if(recurringView.monthly.isChecked()){
                    //goal monthly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("monthly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddEEEE").format(date.getTime())));
                }
                else{
                    //goal yearly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("yearly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddMM").format(date.getTime())));
                }
                goal.setRecurring(true);
                break;
            default:
                title = defaultView.goalTitleText.getText().toString();
                if(defaultView.oneTime.isChecked()){
                    //goal no recursion
                    goal = new Goal(null, title, -1);
                }
                else if(defaultView.daily.isChecked()){
                    //goal daily recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("daily");
                    goal.setRecurring(true);
                }
                else if(defaultView.weekly.isChecked()){
                    //goal weekly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("weekly");
                    goal.setRecurring(true);
                    goal.setDate(String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime())));
                }
                else if(defaultView.monthly.isChecked()){
                    //goal monthly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("monthly");
                    goal.setRecurring(true);
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddEEEE").format(cal.getTime())));
                }
                else{
                    //goal yearly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("yearly");
                    goal.setRecurring(true);
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddMM").format(cal.getTime())));
                }
                break;
        }
        goal.setContext(context());
        activityModel.endOfIncompleted(goal);
        dialog.dismiss();
    }
    public int context(){
        switch (viewSetting) {
            case "Pending":
                if (pendingView.home.isChecked()) {
                    //pending goal home context
                    return 0;
                } else if (pendingView.work.isChecked()) {
                    //pending goal work context
                    return 1;
                } else if (pendingView.school.isChecked()) {
                    //pending goal school context
                    return 2;
                } else {
                    //pending goal errands context
                    return 3;
                }
            case "Recurring":
                if (recurringView.home.isChecked()) {
                    //recurring goal home context
                    return 0;
                } else if (recurringView.work.isChecked()) {
                    //recurring goal work context
                    return 1;
                } else if (recurringView.school.isChecked()) {
                    //recurring goal school context
                    return 2;
                } else {
                    //recurring goal errands context
                    return 3;
                }
            default:
                if (defaultView.home.isChecked()) {
                    //goal home context
                    return 0;
                } else if (defaultView.work.isChecked()) {
                    //goal work context
                    return 1;
                } else if (defaultView.school.isChecked()) {
                    //goal school context
                    return 2;
                } else {
                    //goal errands context
                    return 3;
                }
        }
    }
    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}