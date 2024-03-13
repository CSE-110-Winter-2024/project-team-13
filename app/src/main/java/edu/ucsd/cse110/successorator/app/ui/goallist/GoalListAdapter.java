package edu.ucsd.cse110.successorator.app.ui.goallist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.app.R;
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
        var goalContext = binding.contextImg;
        goalTitle.setText(goal.title());

        if(goal.visibility() != 0){
            binding.getRoot().setVisibility(View.GONE);
        }
        else{
            binding.getRoot().setVisibility(View.VISIBLE);
        }
        switch(goal.context()){
            case 0:
                goalContext.setImageResource(R.drawable.home);
                break;
            case 1:
                goalContext.setImageResource(R.drawable.work);
                break;
            case 2:
                goalContext.setImageResource(R.drawable.school);
                break;
            default:
                goalContext.setImageResource(R.drawable.errands);
                break;
        }

        if (goal.isCompleted()) {
            goalTitle.setPaintFlags(goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            goalTitle.setPaintFlags(goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        goalTitle.setOnLongClickListener(v -> {
            String currentViewSetting = activityModel.getCurrentViewSetting();

            if ("Pending".equals(currentViewSetting)) {
                PopupMenu pendingMenu = new PopupMenu(getContext(), v);
                pendingMenu.getMenuInflater().inflate(R.menu.edit_pending, pendingMenu.getMenu());
                pendingMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
                    if (itemId == R.id.movetoday) {
                        goal.setDate(dateFormat.format(calendar.getTime()));
                        goal.setPending(false);
                        activityModel.remove(goal.id());
                        activityModel.startOfRecursive(goal);
                    } else if (itemId == R.id.movetomorrow) {
                        calendar.add(Calendar.DATE, 1);
                        goal.setDate(dateFormat.format(calendar.getTime()));
                        goal.setPending(false);
                        activityModel.remove(goal.id());
                        activityModel.startOfRecursive(goal);
                    } else if (itemId == R.id.finish) {
                        goal.setIsCompleted(true);
                        goal.setPending(false);
                        activityModel.remove(goal.id());
                        activityModel.startOfRecursive(goal);
                    } else if (itemId == R.id.delete) {
                        onDeleteClick.accept(goal.id());
                    }
                    return true;
                });
                pendingMenu.show();
            } else if ("Recurring".equals(currentViewSetting)) {
                PopupMenu recurringMenu = new PopupMenu(getContext(), v);
                recurringMenu.getMenuInflater().inflate(R.menu.delete_recurring, recurringMenu.getMenu());
                recurringMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.delete) {
                        onDeleteClick.accept(goal.id());
                        return true;
                    }
                    return false;
                });
                recurringMenu.show();
            }
            else {
                goalTitle.setOnLongClickListener(null);
            }
            return true; // Indicates the event is consumed
        });


        goalTitle.setOnClickListener(v -> {
            // https://www.codingdemos.com/android-strikethrough-text/
            if (!goal.isCompleted()) {
                goal.setIsCompleted(true);
                activityModel.remove(goal.id());
                activityModel.startOfRecursive(goal);

            } else {
                goal.setIsCompleted(false);
                goal.setLastUpdated(Calendar.getInstance());
                activityModel.remove(goal.id());
                activityModel.endOfIncompleted(goal);
            }
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
