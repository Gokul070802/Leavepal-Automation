package com.leavepal.automation.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class EmployeedashboardPage extends BaseClass {

    public boolean isEmployeeDashboardDisplayed() {
        return getDriver().getTitle().contains("Employee Dashboard - LeavePal");
    }

    public void waitForDashboard() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.EmployeeDashboard.LEAVE_TRACKER_LINK));
    }

    public LeaveTrackerPage navigateToLeaveTracker() {
        getDriver().findElement(PageLocators.EmployeeDashboard.LEAVE_TRACKER_LINK).click();
        return new LeaveTrackerPage();
    }

    public EditProfilePage navigateToEditProfile() {
        getDriver().findElement(PageLocators.EmployeeDashboard.EMPLOYEE_DETAILS_LINK).click();
        getDriver().findElement(PageLocators.EmployeeDashboard.EDIT_DETAILS_BUTTON).click();
        return new EditProfilePage();
    }
}
