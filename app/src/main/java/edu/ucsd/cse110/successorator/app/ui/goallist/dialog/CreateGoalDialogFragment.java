package edu.ucsd.cse110.successorator.app.ui.goallist.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.R;
import edu.ucsd.cse110.successorator.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.app.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateGoalDialogFragment extends DialogFragment {
    private MainViewModel activityModel;
    private FragmentDialogCreateGoalBinding view;

    CreateGoalDialogFragment(){}

    public static CreateGoalDialogFragment newInstance() {
        var fragment = new CreateGoalDialogFragment();
        Bundle args = new Bundle();
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
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());
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
        String yearlyMsg = "Yearly on " + String.valueOf(new SimpleDateFormat("dd/MM/yy").format(cal.getTime()));
        view.yearly.setText(yearlyMsg);
        return new AlertDialog.Builder(getActivity())
            .setTitle("New Goal")
            .setMessage("Please enter your goal.")
            .setView(view.getRoot())
            .setPositiveButton("Create", this::onPositiveButtonClick)
            .setNegativeButton("Cancel", this::onNegativeButtonClick)
            .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var title = view.goalTitleText.getText().toString();
        Calendar cal = Calendar.getInstance();
        Goal goal;
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
        activityModel.endOfIncompleted(goal);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
