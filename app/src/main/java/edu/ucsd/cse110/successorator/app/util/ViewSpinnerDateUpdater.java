package edu.ucsd.cse110.successorator.app.util;

import edu.ucsd.cse110.successorator.app.MockDate;
import edu.ucsd.cse110.successorator.app.ViewSpinner;

public class ViewSpinnerDateUpdater {
    MockDate mockDate;


    public ViewSpinnerDateUpdater(MockDate mockDate) {
        this.mockDate = mockDate;
    }

    public void update(String selectedView) {
        mockDate.setViewSetting(selectedView);
    }
}
