package com.leavepal.automation.stepDefinitions;

import com.leavepal.automation.pages.Employeedetailspage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.leavepal.automation.base.BaseClass;

public class EmployeedetailspageSteps extends BaseClass {
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

    // ─── Workforce Records – View Profile ─────────────────────────────────────

    @When("Admin clicks View Profile for employee {string}")
    public void adminClicksViewProfileForEmployee(String username) {
        employeedetailspage.waitForWorkforceTable();
        employeedetailspage.clickViewProfileForEmployee(username);
    }

    @Then("The employee profile modal is displayed")
    public void theEmployeeProfileModalIsDisplayed() {
        Assert.assertTrue(employeedetailspage.isEmployeeProfileModalDisplayed(),
                "Employee profile modal was not displayed.");
    }

    @And("The modal profile shows full name {string}")
    public void theModalProfileShowsFullName(String expectedName) {
        String actual = employeedetailspage.getModalFieldValue("Full Name");
        Assert.assertEquals(actual, expectedName,
                "Employee full name in modal did not match expected value.");
    }

    @And("Admin closes the employee profile modal")
    public void adminClosesTheEmployeeProfileModal() {
        employeedetailspage.closeEmployeeProfileModal();
    }

    // ─── Workforce Records – Set Temp Password ────────────────────────────────

    @When("Admin clicks Set Temp Password for employee {string}")
    public void adminClicksSetTempPasswordForEmployee(String username) {
        employeedetailspage.waitForWorkforceTable();
        employeedetailspage.clickSetTempPasswordForEmployee(username);
    }

    @And("Admin confirms temporary password generation")
    public void adminConfirmsTemporaryPasswordGeneration() {
        employeedetailspage.confirmTemporaryPasswordGeneration();
    }

    @Then("A temporary password confirmation message is shown")
    public void aTemporaryPasswordConfirmationMessageIsShown() {
        String toast = employeedetailspage.getToastMessage();
        Assert.assertTrue(toast.toLowerCase().contains("temporary password"),
                "Expected temporary password toast, but got: " + toast);
    }

    // ─── Workforce Records – Delete Employee ──────────────────────────────────

    @When("Admin clicks Delete for employee {string}")
    public void adminClicksDeleteForEmployee(String username) {
        employeedetailspage.waitForWorkforceTable();
        employeedetailspage.clickDeleteForEmployee(username);
        getWait().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[text()='Delete']")))
                .click();

    }

    @Then("Employee {string} is no longer in the workforce table")
    public void employeeIsNoLongerInTheWorkforceTable(String username) {
        Assert.assertFalse(employeedetailspage.isEmployeeInTable(username),
                "Employee '" + username + "' was still found in the workforce table after deletion.");
    }
}
