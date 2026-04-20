package com.leavepal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.Select;

import com.leavepal.automation.base.BaseClass;

public class Employeedetailspage extends BaseClass {

    // locators
    private final By addEmployeeButton = By.id("employee-details-button-add-employee-3");
    private final By selectRoledropdown = By.id("role");
    private final By emailField = By.id("emailId");
    private final By passwordField = By.id("password");
    private final By firstNameField = By.id("firstName");
    private final By lastNameField = By.id("lastName");
    private final By selectDepartmentDropdown = By.id("department");
    private final By designationField = By.id("designation");
    private final By selectReportingManagerDropdown = By.id("reportingEmployeeId");
    private final By locationField = By.id("location");
    private final By dojfield = By.id("dateJoining");
    private final By saveButton = By.id("saveEmployeeBtn");
    private final By cancelButton = By.id("cancel-btn");
    private final By toastMessage = By.id("leavepal-toast-stack");

    // method to click on add employee button
    public void clickAddEmployee() {
        getDriver().findElement(addEmployeeButton).click();
    }

    // method to fill employee details form and save
    public void selectRole(String role) {
        Select roleDropdown = new Select(getDriver().findElement(selectRoledropdown));
        roleDropdown.selectByValue(role);
    }

    // method to fill employee details form and save
    public void enterEmail(String email) {
        getDriver().findElement(emailField).sendKeys(email);
    }

    // method to fill employee details form and save
    public void enterPassword(String password) {
        getDriver().findElement(passwordField).sendKeys(password);
    }

    // method to fill employee details form and save
    public void enterFirstName(String firstName) {
        getDriver().findElement(firstNameField).sendKeys(firstName);
    }

    // method to fill employee details form and save
    public void enterLastName(String lastName) {
        getDriver().findElement(lastNameField).sendKeys(lastName);
    }

    // method to fill employee details form and save
    public void selectDepartment(String department) {
        Select departmentDropdown = new Select(getDriver().findElement(selectDepartmentDropdown));
        departmentDropdown.selectByVisibleText(department);
    }

    // method to fill employee details form and save
    public void enterDesignation(String designation) {
        getDriver().findElement(designationField).sendKeys(designation);
    }

    // method to fill employee details form and save
    public void selectReportingManager(String reportingManager) {
        getWait().until(ExpectedConditions.elementToBeClickable(selectReportingManagerDropdown));

        // Wait until backend-loaded manager options are available (not
        // placeholder-only)
        getWait().until(driver -> {
            Select dropdown = new Select(driver.findElement(selectReportingManagerDropdown));
            return dropdown.getOptions().stream().anyMatch(option -> {
                String value = option.getAttribute("value");
                String text = option.getText().trim();
                return value != null
                        && !value.trim().isEmpty()
                        && !text.equalsIgnoreCase("Loading managers...")
                        && !text.equalsIgnoreCase("No managers available")
                        && !text.equalsIgnoreCase("Unable to load managers");
            });
        });

        Select reportingManagerDropdown = new Select(getDriver().findElement(selectReportingManagerDropdown));
        reportingManagerDropdown.selectByVisibleText(reportingManager);
    }

    // method to fill employee details form and save
    public void enterLocation(String location) {
        getDriver().findElement(locationField).sendKeys(location);
    }

    // method to fill employee details form and save
    public void selectDateOfJoining(String date) {
        getDriver().findElement(dojfield).sendKeys(date);
    }

    // method to fill employee details form and save
    public void clickSaveButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    // method to click on cancel button
    public void clickCancelButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    // method to verify user creation
    public String verifyUserCreated() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).getText();
    }

    // wait until request completes either by success toast or redirect to employee
    // list
    public boolean waitForCreationCompletion() {
        try {
            getWait().until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElementLocated(toastMessage, "added successfully"),
                    ExpectedConditions.textToBePresentInElementLocated(toastMessage, "created successfully"),
                    ExpectedConditions.urlContains("employee-details.html")));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public String getToastMessageIfPresent() {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).getText();
        } catch (TimeoutException ex) {
            return "";
        }
    }
}
