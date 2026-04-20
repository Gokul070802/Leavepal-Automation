package com.leavepal.automation.pages;

import org.openqa.selenium.By;

import com.leavepal.automation.base.BaseClass;

public class EmployeedashboardPage extends BaseClass {
    // Locators
    private final By leaveTrackerLink = By.linkText("Leave Tracker");

    // Methods
    public boolean isEmployeeDashboardDisplayed() {
        String title = getDriver().getTitle();
        return title.contains("Employee Dashboard - LeavePal");
    }

    // Method to navigate to Leave Tracker page
    public LeaveTrackerPage navigateToLeaveTracker() {
        getDriver().findElement(leaveTrackerLink).click();
        return new LeaveTrackerPage();
    }
}
