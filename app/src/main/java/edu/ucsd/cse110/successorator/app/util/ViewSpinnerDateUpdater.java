package edu.ucsd.cse110.successorator.app.util;

import edu.ucsd.cse110.successorator.app.MainViewModel;
import edu.ucsd.cse110.successorator.app.MockDate;
import edu.ucsd.cse110.successorator.app.ViewSpinner;

public class ViewSpinnerDateUpdater {
    MockDate mockDate;

    MainViewModel mainViewModel;



    public ViewSpinnerDateUpdater(MockDate mockDate, MainViewModel mainViewModel) {
        this.mockDate = mockDate;
        this.mainViewModel = mainViewModel;
    }

    public void update(String selectedView) {
        mockDate.setViewSetting(selectedView);
        mainViewModel.setViewSetting(selectedView);
    }
}
