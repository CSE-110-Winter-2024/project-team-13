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
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.app.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalListAdapter extends ArrayAdapter<Goal> {
    Consumer<Integer> onDeleteClick;

    public GoalListAdapter(Context context, List<Goal> flashcards, Consumer<Integer> onDeleteClick) {
        super(context, 0, new ArrayList<>(flashcards));
        this.onDeleteClick = onDeleteClick;
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


        goalTitle.setOnClickListener(v -> {
            // https://www.codingdemos.com/android-strikethrough-text/
            if(!goalTitle.getPaint().isStrikeThruText()) {
                goalTitle.setPaintFlags(goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                goalTitle.setPaintFlags(goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
//            var id = goal.id();
//            assert id != null;
//            onDeleteClick.accept(id);
        });

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
