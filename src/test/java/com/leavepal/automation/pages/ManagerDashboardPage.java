package com.leavepal.automation.pages;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class ManagerDashboardPage extends BaseClass {

    public boolean isManagerDashboardDisplayed() {
        return getDriver().getTitle().contains("Dashboard - LeavePal");
    }

    public LeaveRequestsPage navigateToLeaveRequests() {
        getDriver().findElement(PageLocators.ManagerDashboard.LEAVE_REQUESTS_LINK).click();
        return new LeaveRequestsPage();
    }
}
