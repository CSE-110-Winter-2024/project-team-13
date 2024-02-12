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

        var goal = new Goal(null, title, -1);

        activityModel.append(goal);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
