package com.leavepal.automation.stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.EmployeedashboardPage;
import com.leavepal.automation.pages.LeaveTrackerPage;
import com.leavepal.automation.pages.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class EmployeedashboardSteps {
    LoginPage loginPage = new LoginPage();
    EmployeedashboardPage employeedashboardPage = new EmployeedashboardPage();

    @Given("The user is logged in as an employee with username {string} and password {string}")
    public void theUserIsLoggedInAsAnEmployee(String username, String password) {
        loginPage.selectManageroremployee();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        BaseClass.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Leave Tracker")));
    }

    @When("Navigate to the Leave Tracker section")
    public LeaveTrackerPage navigateToTheLeaveTrackerSection() {
        employeedashboardPage.navigateToLeaveTracker();
        return new LeaveTrackerPage();
    }
}
