package com.leavepal.automation.pages;

import org.openqa.selenium.By;

import com.leavepal.automation.base.BaseClass;

public class ManagerDashboardPage extends BaseClass {
    // Locators
    private final By leaveRequests = By.linkText("Leave Requests");

    // Methods
    public boolean isManagerDashboardDisplayed() {
        String title = getDriver().getTitle();
        return title.contains("Dashboard - LeavePal");
    }

    // Method to navigate to Leave Requests page
    public LeaveRequestsPage navigateToLeaveRequests() {
        getDriver().findElement(leaveRequests).click();
        return new LeaveRequestsPage();
    }

}
