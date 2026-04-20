package com.leavepal.automation.stepDefinitions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.AdminDashboardpage;
import com.leavepal.automation.pages.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class AdminDashBoardSteps extends BaseClass {
    LoginPage loginPage = new LoginPage();
    AdminDashboardpage adminDashboardpage = new AdminDashboardpage();

    @Given("The user is logged in as an admin with username {string} and password {string}")
    public void theUserIsLoggedInAsAnAdmin(String username, String password) {
        loginPage.selectHRLogin();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @When("Navigate to the employee details section")
    public EmployeedetailspageSteps navigateToTheEmployeeDetailsSection() {
        adminDashboardpage.selectEmployeeDetails();
        return new EmployeedetailspageSteps();
    }

}
