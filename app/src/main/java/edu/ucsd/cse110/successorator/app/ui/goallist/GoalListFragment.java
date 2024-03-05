package edu.ucsd.cse110.successorator.app.ui.goallist;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.R;
import edu.ucsd.cse110.successorator.app.databinding.FragmentGoalListBinding;
import edu.ucsd.cse110.successorator.app.ui.goallist.dialog.ConfirmDeleteGoalDialogFragment;
import edu.ucsd.cse110.successorator.app.ui.goallist.dialog.CreateGoalDialogFragment;

public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentGoalListBinding view;
    private GoalListAdapter adapter;

    Spinner viewSpinner;

    TextView testText;

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

        this.adapter = new GoalListAdapter(requireContext(), List.of(), id -> {
            var dialogFragment = ConfirmDeleteGoalDialogFragment.newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "ConfirmDeleteGoalDialogFragment");
        }, activityModel);
        activityModel.getOrderedGoals().observe(goals -> {
            if (goals == null) return;
            if (!goals.isEmpty()) {
                view.getRoot().setBackgroundColor(Color.WHITE);
            } else {
                view.getRoot().setBackgroundColor(Color.TRANSPARENT);
            }
            adapter.clear();
            adapter.addAll(new ArrayList<>(goals));
            adapter.notifyDataSetChanged();
        });




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentGoalListBinding.inflate(inflater, container, false);

        viewSpinner = view.getRoot().findViewById(R.id.view_spinner);

        view.goalList.setAdapter(adapter);
        initSpinner();

//        viewSpinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String viewState = viewSpinner.getSelectedItem().toString();
//            }
//        });

        view.createGoalButton.setOnClickListener(v -> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getParentFragmentManager(), "CreateGoalDialogFragment");
        });

        testText = (TextView) view.getRoot().findViewById(R.id.testSpinner);

        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item is selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos).
                Log.d("Spinner", "onItemSelected: " + parent.getItemAtPosition(pos).toString());
                testText.setText(parent.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing
            }
        });

        return view.getRoot();
    }

    private void initSpinner() {
        String[] items = new String[]{
                "Today", "Tomorrow", "Pending", "Recurring"
        };

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(getActivity(), R.array.views, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        viewSpinner.setAdapter(adapter);
//        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        });
    }

}
