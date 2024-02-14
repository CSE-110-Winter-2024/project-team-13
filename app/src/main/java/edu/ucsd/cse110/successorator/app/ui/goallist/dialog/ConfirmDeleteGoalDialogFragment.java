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

public class ConfirmDeleteGoalDialogFragment extends DialogFragment {
    private static final String ARG_GOAL_ID = "goal_id";
    private int goalId;

    private MainViewModel activityModel;

    ConfirmDeleteGoalDialogFragment() {
        // Required empty public constructor
    }

    public static ConfirmDeleteGoalDialogFragment newInstance(int goalId) {
        var fragment = new ConfirmDeleteGoalDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GOAL_ID, goalId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.goalId = requireArguments().getInt(ARG_GOAL_ID);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Delete Goal")
                .setMessage("Are you sure you want to delete this goal?")
                .setPositiveButton("Delete", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }
    private void onPositiveButtonClick(DialogInterface dialog, int which){
        activityModel.remove(goalId);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }
}
