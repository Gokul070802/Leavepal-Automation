package com.leavepal.automation.stepDefinitions;

import com.leavepal.automation.pages.Employeedetailspage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class EmployeedetailspageSteps {
    Employeedetailspage employeedetailspage = new Employeedetailspage();

    @And("Click on the Add Employee button")
    public void clickOnAddEmployeeButton() {
        employeedetailspage.clickAddEmployee();
    }

    @And("Fill in the manager details with role {string}, email {string}, password {string}, firstName {string}, lastName {string}, department {string}, designation {string}, location {string} and dateOfJoining {string}")
    public void fillInTheManagerDetailsWithValidInformation(String role, String email, String password,
            String firstName,
            String lastName, String department, String designation, String location,
            String dateOfJoining) {
        employeedetailspage.selectRole(role);
        employeedetailspage.enterEmail(email);
        employeedetailspage.enterPassword(password);
        employeedetailspage.enterFirstName(firstName);
        employeedetailspage.enterLastName(lastName);
        employeedetailspage.selectDepartment(department);
        employeedetailspage.enterDesignation(designation);
        employeedetailspage.enterLocation(location);
        employeedetailspage.selectDateOfJoining(dateOfJoining);
    }

    @And("Fill in the employee details with role {string}, email {string}, password {string}, firstName {string}, lastName {string}, department {string}, designation {string}, reportingManager {string}, location {string} and dateOfJoining {string}")
    public void fillInTheEmployeeDetailsWithValidInformation(String role, String email, String password,
            String firstName,
            String lastName, String department, String designation, String reportingManager, String location,
            String dateOfJoining) {
        employeedetailspage.selectRole(role);
        employeedetailspage.enterEmail(email);
        employeedetailspage.enterPassword(password);
        employeedetailspage.enterFirstName(firstName);
        employeedetailspage.enterLastName(lastName);
        employeedetailspage.selectDepartment(department);
        employeedetailspage.enterDesignation(designation);
        employeedetailspage.selectReportingManager(reportingManager);
        employeedetailspage.enterLocation(location);
        employeedetailspage.selectDateOfJoining(dateOfJoining);
    }

    @And("Click on the Save button")
    public void clickOnSaveButton() {
        employeedetailspage.clickSaveButton();
    }

    @Then("A new manager should be created successfully")
    public void verifyManagerCreated() {
        boolean completed = employeedetailspage.waitForCreationCompletion();
        String toast = employeedetailspage.getToastMessageIfPresent();
        Assert.assertTrue(completed,
                "Manager creation did not complete before scenario ended. Current toast: " + toast);
        Assert.assertTrue(toast.isEmpty()
                || toast.contains("Manager")
                        && (toast.contains("added successfully") || toast.contains("created successfully")),
                "Unexpected manager creation message: " + toast);
    }

    @Then("A new employee should be created successfully")
    public void verifyEmployeeCreated() {
        boolean completed = employeedetailspage.waitForCreationCompletion();
        String toast = employeedetailspage.getToastMessageIfPresent();
        Assert.assertTrue(completed,
                "Employee creation did not complete before scenario ended. Current toast: " + toast);
        Assert.assertTrue(toast.isEmpty()
                || toast.contains("Employee")
                        && (toast.contains("added successfully") || toast.contains("created successfully")),
                "Unexpected employee creation message: " + toast);
    }
}
