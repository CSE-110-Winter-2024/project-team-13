package edu.ucsd.cse110.successorator.app.ui.goallist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

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
    private FragmentDialogCreateGoalBinding view;
    private String viewSetting;
    private String title;

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
            FragmentDialogCreateGoalPendingBinding pendingView = FragmentDialogCreateGoalPendingBinding.inflate(inflater);
            configurePendingView(pendingView);
            builder.setView(pendingView.getRoot());
            builder.setTitle("New Goal")
                    .setMessage("Please enter your goal.")
                    .setPositiveButton("Create", (dialog, which) -> {
                        title = pendingView.goalTitleText.getText().toString();
                        onPositiveButtonClick();
                    })
                    .setNegativeButton("Cancel", this::onNegativeButtonClick);
            return builder.create();
        } else if ("Recurring".equals(viewSetting)) {
            FragmentDialogCreateGoalRecurringBinding recurringView = FragmentDialogCreateGoalRecurringBinding.inflate(inflater);
            configureRecurringView(recurringView);
            builder.setView(recurringView.getRoot());
            builder.setTitle("New Goal")
                    .setMessage("Please enter your goal.")
                    .setPositiveButton("Create", (dialog, which) -> {
                        title = recurringView.goalTitleText.getText().toString();
                        onPositiveButtonClick();
                    })
                    .setNegativeButton("Cancel", this::onNegativeButtonClick);
            return builder.create();
        } else {
            FragmentDialogCreateGoalBinding defaultView = FragmentDialogCreateGoalBinding.inflate(inflater);
            configureDefaultView(defaultView);
            builder.setView(defaultView.getRoot());
            builder.setTitle("New Goal")
                    .setMessage("Please enter your goal.")
                    .setPositiveButton("Create", (dialog, which) -> {
                        title = defaultView.goalTitleText.getText().toString();
                        onPositiveButtonClick();
                    })
                    .setNegativeButton("Cancel", this::onNegativeButtonClick);
            return builder.create();
        }
//        builder.setTitle("New Goal")
//                .setMessage("Please enter your goal.")
//                .setPositiveButton("Create", this::onPositiveButtonClick)
//                .setNegativeButton("Cancel", this::onNegativeButtonClick);
//        return builder.create();
    }


    private void configureRecurringView(FragmentDialogCreateGoalRecurringBinding view) {
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

    private void configurePendingView(FragmentDialogCreateGoalPendingBinding view) {
        //Do Nothing.
        //Something to think about is to add goal with a status "pending".
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

    private void onPositiveButtonClick() {
        //ISSUE: view is null and cannot call different view types
        //title = view.goalTitleText.getText().toString();
        Calendar cal = MainViewModel.getCal();
        Goal goal;

        switch (viewSetting) {
            case "Pending":
                // Assuming title is retrieved the same way from all views
                goal = new Goal(null, title, -1);
                //Make this goal pending
                break;
            case "Recurring":
                if(view.daily.isChecked()){
                    //goal daily recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("daily");
                }
                else if(view.weekly.isChecked()){
                    //goal weekly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("weekly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime())));
                }
                else if(view.monthly.isChecked()){
                    //goal monthly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("monthly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddEEEE").format(cal.getTime())));
                }
                else{
                    //goal yearly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("yearly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddMM").format(cal.getTime())));
                }
                break;
            default:
                if(view.oneTime.isChecked()){
                    //goal no recursion
                    goal = new Goal(null, title, -1);
                }
                else if(view.daily.isChecked()){
                    //goal daily recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("daily");
                }
                else if(view.weekly.isChecked()){
                    //goal weekly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("weekly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("EEEE").format(cal.getTime())));
                }
                else if(view.monthly.isChecked()){
                    //goal monthly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("monthly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddEEEE").format(cal.getTime())));
                }
                else{
                    //goal yearly recursion
                    goal = new Goal(null, title, -1);
                    goal.setRecursionType("yearly");
                    goal.setDate(String.valueOf(new SimpleDateFormat("ddMM").format(cal.getTime())));
                }
                break;
        }

        activityModel.endOfIncompleted(goal);
        //dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
