package com.leavepal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class Employeedetailspage extends BaseClass {

    public void clickAddEmployee() {
        getDriver().findElement(PageLocators.EmployeeDetails.ADD_EMPLOYEE_BUTTON).click();
    }

    public void selectRole(String role) {
        new Select(getDriver().findElement(PageLocators.EmployeeDetails.ROLE_DROPDOWN)).selectByValue(role);
    }

    public void enterEmail(String email) {
        getDriver().findElement(PageLocators.EmployeeDetails.EMAIL_FIELD).sendKeys(email);
    }

    public void enterPassword(String password) {
        getDriver().findElement(PageLocators.EmployeeDetails.PASSWORD_FIELD).sendKeys(password);
    }

    public void enterFirstName(String firstName) {
        getDriver().findElement(PageLocators.EmployeeDetails.FIRST_NAME_FIELD).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        getDriver().findElement(PageLocators.EmployeeDetails.LAST_NAME_FIELD).sendKeys(lastName);
    }

    public void selectDepartment(String department) {
        new Select(getDriver().findElement(PageLocators.EmployeeDetails.DEPARTMENT_DROPDOWN))
                .selectByVisibleText(department);
    }

    public void enterDesignation(String designation) {
        getDriver().findElement(PageLocators.EmployeeDetails.DESIGNATION_FIELD).sendKeys(designation);
    }

    public void selectReportingManager(String reportingManager) {
        getWait().until(
                ExpectedConditions.elementToBeClickable(PageLocators.EmployeeDetails.REPORTING_MANAGER_DROPDOWN));
        getWait().until(driver -> {
            Select dropdown = new Select(driver.findElement(PageLocators.EmployeeDetails.REPORTING_MANAGER_DROPDOWN));
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
        new Select(getDriver().findElement(PageLocators.EmployeeDetails.REPORTING_MANAGER_DROPDOWN))
                .selectByVisibleText(reportingManager);
    }

    public void selectReportingManagerByNameContains(String managerNamePart) {
        getWait().until(
                ExpectedConditions.elementToBeClickable(PageLocators.EmployeeDetails.REPORTING_MANAGER_DROPDOWN));
        Select dropdown = new Select(getDriver().findElement(PageLocators.EmployeeDetails.REPORTING_MANAGER_DROPDOWN));
        String needle = managerNamePart == null ? "" : managerNamePart.trim().toLowerCase();

        for (WebElement option : dropdown.getOptions()) {
            String text = option.getText() == null ? "" : option.getText().trim();
            String value = option.getAttribute("value");
            if (text.isEmpty()) {
                continue;
            }
            if (value == null || value.trim().isEmpty()) {
                continue;
            }
            if (text.equalsIgnoreCase("Loading managers...")
                    || text.equalsIgnoreCase("No managers available")
                    || text.equalsIgnoreCase("Unable to load managers")) {
                continue;
            }
            if (text.toLowerCase().contains(needle)) {
                dropdown.selectByVisibleText(text);
                return;
            }
        }

        throw new TimeoutException("No reporting manager option contains: " + managerNamePart);
    }

    public void enterLocation(String location) {
        getDriver().findElement(PageLocators.EmployeeDetails.LOCATION_FIELD).sendKeys(location);
    }

    public void selectDateOfJoining(String date) {
        getDriver().findElement(PageLocators.EmployeeDetails.DATE_OF_JOINING_FIELD).sendKeys(date);
    }

    public void clickSaveButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.EmployeeDetails.SAVE_BUTTON)).click();
    }

    public void clickCancelButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.EmployeeDetails.CANCEL_BUTTON)).click();
    }

    public boolean waitForCreationCompletion() {
        try {
            getWait().until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElementLocated(PageLocators.TOAST_MESSAGE,
                            "added successfully"),
                    ExpectedConditions.textToBePresentInElementLocated(PageLocators.TOAST_MESSAGE,
                            "created successfully"),
                    ExpectedConditions.urlContains("employee-details.html")));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    // Returns empty string (not null) so callers can safely call .isEmpty().
    @Override
    public String getToastMessageIfPresent(int timeoutSeconds) {
        String msg = super.getToastMessageIfPresent(timeoutSeconds);
        return msg == null ? "" : msg;
    }

    // No-arg convenience that matches the call site in EmployeedetailspageSteps.
    public String getToastMessageIfPresent() {
        return getToastMessageIfPresent(10);
    }

    // ─── Workforce Records Table (admin view) ─────────────────────────────────

    public void waitForWorkforceTable() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.EmployeeDetails.EMPLOYEE_TABLE_BODY));
    }

    public boolean isEmployeeInTable(String username) {
        return findRowForEmployee(username) != null;
    }

    public void clickViewProfileForEmployee(String username) {
        clickActionButtonForEmployee(username, "VIEW PROFILE", "viewprofile(");
    }

    public void clickSetTempPasswordForEmployee(String username) {
        clickActionButtonForEmployee(username, "SET TEMP PASSWORD", "generatetemporarypassword(");
    }

    public void confirmTemporaryPasswordGeneration() {
        getWait().until(ExpectedConditions.elementToBeClickable(
                PageLocators.EmployeeDetails.TEMP_PASSWORD_CONFIRM_BUTTON)).click();
    }

    public void clickDeleteForEmployee(String username) {
        clickActionButtonForEmployee(username, "DELETE", "deleteemployee(");
    }

    private WebElement findRowForEmployee(String identifier) {
        waitForWorkforceTable();
        String needle = identifier == null ? "" : identifier.trim().toLowerCase();
        List<WebElement> rows = getDriver().findElements(By.cssSelector("#employeeTableBody tr"));

        for (WebElement row : rows) {
            String rowText = row.getText() == null ? "" : row.getText().toLowerCase();
            if (rowText.contains(needle)) {
                return row;
            }

            List<WebElement> buttons = row.findElements(By.tagName("button"));
            for (WebElement button : buttons) {
                String onclick = button.getAttribute("onclick");
                if (onclick != null && onclick.toLowerCase().contains(needle)) {
                    return row;
                }
            }
        }
        return null;
    }

    private void clickActionButtonForEmployee(String identifier, String buttonText, String onclickPrefix) {
        WebElement row = findRowForEmployee(identifier);
        if (row == null) {
            throw new TimeoutException("No employee row found for identifier: " + identifier);
        }

        List<WebElement> buttons = row.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            String text = button.getText() == null ? "" : button.getText().trim();
            String onclick = button.getAttribute("onclick");
            boolean textMatch = text.equalsIgnoreCase(buttonText);
            boolean onclickMatch = onclick != null && onclick.toLowerCase().contains(onclickPrefix);

            if (textMatch || onclickMatch) {
                getWait().until(ExpectedConditions.elementToBeClickable(button)).click();
                return;
            }
        }

        throw new TimeoutException("Action button '" + buttonText + "' not found for identifier: " + identifier);
    }

    // ─── Employee Profile Modal ───────────────────────────────────────────────

    public boolean isEmployeeProfileModalDisplayed() {
        String classes = getDriver()
                .findElement(PageLocators.EmployeeDetails.EMPLOYEE_PROFILE_MODAL)
                .getAttribute("class");
        return classes != null && !classes.contains("hidden");
    }

    public String getModalFieldValue(String fieldLabel) {
        By valueLocator = By.xpath(
                "//div[@id='modalProfileGrid']"
                        + "//span[contains(@class,'modal-profile-label') and normalize-space()='" + fieldLabel + "']"
                        + "/following-sibling::span[contains(@class,'modal-profile-value')]");
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(valueLocator)).getText().trim();
    }

    public void closeEmployeeProfileModal() {
        getWait().until(ExpectedConditions
                .elementToBeClickable(PageLocators.EmployeeDetails.EMPLOYEE_PROFILE_MODAL_CLOSE)).click();
        getWait().until(driver -> {
            String classes = driver
                    .findElement(PageLocators.EmployeeDetails.EMPLOYEE_PROFILE_MODAL)
                    .getAttribute("class");
            return classes != null && classes.contains("hidden");
        });
    }

    public void waitForAdminLeaveDataTable() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("leaveTrackerTableBody")));
    }

    public int getAdminLeaveDataMetric(String employeeIdentifier, int columnIndex) {
        waitForAdminLeaveDataTable();
        By cell = By.xpath("//tbody[@id='leaveTrackerTableBody']//tr[.//td[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]/td[" + columnIndex + "]");
        String value = getWait().until(ExpectedConditions.visibilityOfElementLocated(cell)).getText().trim();
        String digitsOnly = value.replaceAll("[^0-9]", "");
        return digitsOnly.isEmpty() ? 0 : Integer.parseInt(digitsOnly);
    }
}
