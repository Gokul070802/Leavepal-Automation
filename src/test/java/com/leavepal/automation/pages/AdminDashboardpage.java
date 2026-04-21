package com.leavepal.automation.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class AdminDashboardpage extends BaseClass {

    public boolean isAdminDashboardDisplayed() {
        return getDriver().getTitle().contains("HR Dashboard - LeavePal");
    }

    public Employeedetailspage selectEmployeeDetails() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.AdminDashboard.EMPLOYEE_DETAILS_LINK))
                .click();
        return new Employeedetailspage();
    }

    public void clickAdminResetNotification() {
        try {
            getWait()
                    .until(ExpectedConditions.elementToBeClickable(PageLocators.AdminDashboard.RESET_NOTIFICATION_ROOT))
                    .click();
        } catch (Exception ex) {
            // Some dashboard variants expose the temporary password generator directly.
        }
    }

    public void waitForAdminResetWorkflow() {
        getWait().until(ExpectedConditions.or(
                ExpectedConditions.elementToBeClickable(PageLocators.AdminDashboard.RESET_NOTIFICATION_ROOT),
                ExpectedConditions.elementToBeClickable(PageLocators.AdminDashboard.GENERATE_TEMP_PASSWORD_BUTTON),
                ExpectedConditions.titleContains("Dashboard"),
                ExpectedConditions.urlContains("dashboard")));
    }

    public void clickGenerateTempPassword() {
        getWait().until(
                ExpectedConditions.elementToBeClickable(PageLocators.AdminDashboard.GENERATE_TEMP_PASSWORD_BUTTON))
                .click();
    }

    public String getGeneratedPassword() {
        String fullText = getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(PageLocators.AdminDashboard.CONFIRM_CODE_DIV))
                .getText();
        String[] parts = fullText.split(":");
        return parts[parts.length - 1].trim();
    }

    public void clickCloseButton() {
        getDriver().findElement(PageLocators.AdminDashboard.CLOSE_BUTTON).click();
    }

    public void clickLogout() {
        getDriver().findElement(PageLocators.AdminDashboard.LOGOUT_BUTTON).click();
    }
}
