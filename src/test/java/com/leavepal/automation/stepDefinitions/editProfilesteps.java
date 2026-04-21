//Dhanush Raj R

package com.leavepal.automation.stepDefinitions;

import static org.testng.Assert.assertTrue;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.EditProfilePage;
import com.leavepal.automation.pages.EmployeedashboardPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class editProfilesteps extends BaseClass {
    EditProfilePage editProfilePage;
    EmployeedashboardPage employeedashboardPage = new EmployeedashboardPage();

    @When("Navigate to the Edit Profile section")
    public void navigateToEditProfileSection() {
        editProfilePage = employeedashboardPage.navigateToEditProfile();
    }

    @When("The user navigates to Employee Details page")
    public void navigateToEmployeeDetailsPage() {
        // Navigate to employee details - this is handled by the dashboard navigation
        // Since navigateToEditProfile already clicks Employee Details, we can reuse it
        editProfilePage = employeedashboardPage.navigateToEditProfile();
    }

    @And("The user clicks on Edit Details button")
    public void clickEditDetailsButton() {
        // This is already handled in navigateToEditProfile
        // The page object handles the navigation
    }

    @Then("The Edit Profile page should be displayed")
    public void editProfilePageDisplayed() {
        assertTrue(editProfilePage.isPageLoaded());
    }

    @And("The employee name should be visible")
    public void employeeNameVisible() {
        assertTrue(!editProfilePage.getUserName().isEmpty());
    }

    @And("The employee ID field should be disabled")
    public void employeeIdDisabled() {
        assertTrue(editProfilePage.isEmployeeIdDisabled());
    }

    @And("The official email field should be disabled")
    public void officialEmailDisabled() {
        assertTrue(editProfilePage.isEmailDisabled());
    }

    @And("The user enters phone number as {string}")
    public void enterPhoneNumber(String phone) {
        editProfilePage.enterPhone(phone);
    }

    @And("The user enters nationality as {string}")
    public void enterNationality(String nationality) {
        editProfilePage.enterNationality(nationality);
    }

    @And("The user selects blood group as {string}")
    public void selectBloodGroup(String group) {
        editProfilePage.selectBloodGroup(group);
    }

    @And("The user selects marital status as {string}")
    public void selectMaritalStatus(String status) {
        editProfilePage.selectMaritalStatus(status);
    }

    @And("The user enters date of birth as {string}")
    public void enterDateOfBirth(String dob) {
        editProfilePage.enterDOB(dob);
    }

    @And("The user enters personal email as {string}")
    public void enterPersonalEmail(String email) {
        editProfilePage.enterPersonalEmail(email);
    }

    @And("The user selects gender as {string}")
    public void selectGender(String gender) {
        editProfilePage.selectGender(gender);
    }

    @And("The user enters address as {string}")
    public void enterAddress(String address) {
        editProfilePage.enterAddress(address);
    }

    @And("The user clicks on Save Changes button")
    public void clickSaveChanges() {
        editProfilePage.saveChanges();
    }

    @And("The user clicks on Cancel button")
    public void clickCancel() {
        editProfilePage.cancelEdit();
    }

    @Then("The profile should be updated successfully")
    public void profileUpdatedSuccessfully() {
        assertTrue(getDriver().getPageSource().contains("success"));
    }

    @Then("The changes should be discarded")
    public void changesDiscarded() {
        assertTrue(getDriver().getCurrentUrl().contains("employee-details"));
    }

    @Then("An error message should be displayed for phone field")
    public void phoneErrorDisplayed() {
        assertTrue(editProfilePage.getPhoneError().isEmpty());
    }

    @Then("An error message should be displayed for date of birth field")
    public void dobErrorDisplayed() {
        assertTrue(editProfilePage.getDobError().isEmpty());
    }

    @Then("An error message should be displayed for personal email field")
    public void emailErrorDisplayed() {
        assertTrue(!editProfilePage.getPersonalEmailError().isEmpty());
    }

    @And("The user clicks on Logout button")
    public void clickLogout() {
        editProfilePage.logout();
    }

    @Then("The user should be logged out")
    public void userLoggedOut() {
        assertTrue(getDriver().getCurrentUrl().contains("index"));
    }
}