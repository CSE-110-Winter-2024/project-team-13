package edu.ucsd.cse110.successorator.app.ui.goallist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.databinding.FragmentGoalListBinding;
import edu.ucsd.cse110.successorator.app.ui.goallist.dialog.CreateGoalDialogFragment;

public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentGoalListBinding view;
    private GoalListAdapter adapter;

    public GoalListFragment() {
    }

    public static GoalListFragment newInstance() {
        GoalListFragment fragment = new GoalListFragment();
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

        this.adapter = new GoalListAdapter(requireContext(), List.of(), activityModel::remove);
        activityModel.getOrderedGoals().observe(goals -> {
            if (goals == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(goals));
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentGoalListBinding.inflate(inflater, container, false);

        view.goalList.setAdapter(adapter);

        view.createGoalButton.setOnClickListener(v -> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getParentFragmentManager(), "CreateGoalDialogFragment");
        });

        return view.getRoot();
    }
}
