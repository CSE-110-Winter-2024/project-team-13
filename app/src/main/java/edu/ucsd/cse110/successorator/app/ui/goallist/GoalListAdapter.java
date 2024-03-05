package edu.ucsd.cse110.successorator.app.ui.goallist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.app.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.lib.domain.Goal;


public class GoalListAdapter extends ArrayAdapter<Goal> {
    Consumer<Integer> onDeleteClick;

    private MainViewModel activityModel;

    public GoalListAdapter(Context context, List<Goal> goals, Consumer<Integer> onDeleteClick, MainViewModel activityModel) {
        super(context, 0, new ArrayList<>(goals));
        this.onDeleteClick = onDeleteClick;
        this.activityModel = activityModel;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        var goal = getItem(position);
        assert goal != null;

        ListItemGoalBinding binding;
        if (convertView != null) {
            binding = ListItemGoalBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemGoalBinding.inflate(layoutInflater, parent, false);
        }
        var goalTitle = binding.goalTitle;
        goalTitle.setText(goal.title());
        if (goal.isCompleted()) {
            goalTitle.setPaintFlags(goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            goalTitle.setPaintFlags(goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        goalTitle.setOnClickListener(v -> {
            // https://www.codingdemos.com/android-strikethrough-text/
            if (!goal.isCompleted()) {
                goal.setIsCompleted(true);
                activityModel.remove(goal.id());
                activityModel.append(goal);

            } else {
                goal.setIsCompleted(false);
                goal.setLastUpdated(Calendar.getInstance());
                activityModel.remove(goal.id());
                activityModel.endOfIncompleted(goal);
            }
        });
        if(goal.visibility() != 0){
            binding.getRoot().setVisibility(View.GONE);
        }
        else{
            binding.getRoot().setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var goal = getItem(position);
        assert goal != null;

        var id = goal.id();
        assert id != null;

        return id;
    }
}
