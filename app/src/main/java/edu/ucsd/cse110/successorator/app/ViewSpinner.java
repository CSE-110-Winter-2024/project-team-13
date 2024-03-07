package edu.ucsd.cse110.successorator.app;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

public class ViewSpinner extends AppCompatActivity {

    Spinner spinner;
    MainViewModel mainViewModel;

    Activity activity;

    TextView testSpinner;

    public ViewSpinner(Activity activity, Spinner spinner, MainViewModel mainViewModel,TextView testSpinner) {
        this.activity = activity;
        this.spinner = spinner;
        this.mainViewModel = mainViewModel;
        this.testSpinner = testSpinner;
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(activity, R.array.views, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public AdapterView.OnItemSelectedListener eventHandler() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item is selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos).
                Log.d("Spinner", "onItemSelected: " + parent.getItemAtPosition(pos).toString());
                String currValue = parent.getItemAtPosition(pos).toString();
                testSpinner.setText(currValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing
            }
        };

    }

}
