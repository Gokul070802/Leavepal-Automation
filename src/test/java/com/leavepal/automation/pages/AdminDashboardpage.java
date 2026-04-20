package com.leavepal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;

public class AdminDashboardpage extends BaseClass {

    // locators
    private final By employeeDetailsLink = By.partialLinkText("Details");

    // method to verify if admin dashboard is displayed
    public boolean isAdminDashboardDisplayed() {
        String title = getDriver().getTitle();
        return title.contains("HR Dashboard - LeavePal");
    }

    // method to navigate to employee details page
    public Employeedetailspage selectEmployeeDetails() {
        getWait().until(ExpectedConditions.elementToBeClickable(employeeDetailsLink)).click();
        return new Employeedetailspage();
    }

}
