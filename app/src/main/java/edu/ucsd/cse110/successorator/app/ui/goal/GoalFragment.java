package edu.ucsd.cse110.successorator.app.ui.goal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.databinding.FragmentGoalBinding;

public class GoalFragment extends Fragment {
//    private MainViewModel activityModel;
//    private FragmentGoalBinding view;
//
//    public GoalFragment() {
//        // Required empty public constructor
//    }
//
//    public static GoalFragment newInstance() {
//        GoalFragment fragment = new GoalFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        var modelOwner = requireActivity();
//        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
//        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
//        this.activityModel = modelProvider.get(MainViewModel.class);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view = FragmentGoalBinding.inflate(inflater, container, false);
//
//        setupMvp();
//
//        return view.getRoot();
//    }
//
//    private void setupMvp() {
//        activityModel.getDisplayedText().observe(text -> view.goalText.setText(text));
//    }
}