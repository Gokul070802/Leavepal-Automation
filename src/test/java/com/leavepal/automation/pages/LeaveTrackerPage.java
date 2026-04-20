package com.leavepal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.leavepal.automation.base.BaseClass;

public class LeaveTrackerPage extends BaseClass {
    // Locators
    private final By applyLeaveButton = By.id("leave-tracker-button-apply-leave-2");
    private final By leaveTypeDropdown = By.id("leaveType");
    private final By fromDateInput = By.id("fromDate");
    private final By toDateInput = By.id("toDate");
    private final By reasonInput = By.id("reason");
    private final By medicalCertificateUpload = By.id("sickAttachment");
    private final By applyButton = By.id("apply-leave-button-apply-3");
    private final By cancelButton = By.id("apply-leave-button-cancel-2");
    private final By toastMessage = By.id("leavepal-toast-stack");
    private final By leaveSplitOkButton = By.id("leaveSplitOkBtn");
    private final By leaveSplitCancelButton = By.id("leaveSplitCancelBtn");

    // Methods

    public void clickApplyLeave() {
        getDriver().findElement(applyLeaveButton).click();
    }

    public void selectLeaveType(String leaveType) {
        Select leaveTypeMenu = new Select(getDriver().findElement(leaveTypeDropdown));
        leaveTypeMenu.selectByValue(leaveType);
    }

    public String getSelectedLeaveType() {
        Select leaveTypeMenu = new Select(
                getWait().until(ExpectedConditions.visibilityOfElementLocated(leaveTypeDropdown)));
        return leaveTypeMenu.getFirstSelectedOption().getAttribute("value");
    }

    public boolean isLeaveSplitPromptDisplayed() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(leaveSplitOkButton)).isDisplayed()
                && getWait().until(ExpectedConditions.visibilityOfElementLocated(leaveSplitCancelButton)).isDisplayed();
    }

    public void clickLeaveSplitOkButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(leaveSplitOkButton)).click();
        // Wait for the split prompt to close so it doesn't block Apply button click.
        try {
            getWait().until(ExpectedConditions.invisibilityOfElementLocated(leaveSplitOkButton));
        } catch (TimeoutException ignored) {
            // UI may keep the element in DOM briefly; continue with Apply wait/click
            // safeguards.
        }
        getWait().until(ExpectedConditions.elementToBeClickable(applyButton));
    }

    public void enterFromDate(String fromDate) {
        setDateValue(fromDateInput, fromDate);
    }

    public void enterToDate(String toDate) {
        setDateValue(toDateInput, toDate);
    }

    // Converts dd-MM-yyyy (feature format) to yyyy-MM-dd (required by
    // input[type=date]) and fires a change event so the form reacts
    private void setDateValue(By locator, String date) {
        String[] parts = date.split("-");
        String htmlDate = parts[2] + "-" + parts[1] + "-" + parts[0];
        WebElement element = getDriver().findElement(locator);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
                element, htmlDate);
    }

    public void enterReason(String reason) {
        getDriver().findElement(reasonInput).sendKeys(reason);
    }

    public void fillLeaveApplication(String leaveType, String fromDate, String toDate, String reason) {
        selectLeaveType(leaveType);
        enterFromDate(fromDate);
        enterToDate(toDate);
        enterReason(reason);
    }

    public void uploadMedicalCertificate(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath must be provided");
        }
        // Wait for the file input to be present in the DOM
        getWait().until(ExpectedConditions.presenceOfElementLocated(medicalCertificateUpload));
        WebElement fileInput = getDriver().findElement(medicalCertificateUpload);
        // Remove CSS hiding so WebDriver can interact with it via sendKeys
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1';",
                fileInput);
        // sendKeys on input[type=file] sets the file directly — no OS dialog needed
        fileInput.sendKeys(filePath);
    }

    public void clickApplyButton() {
        WebElement apply = getWait().until(ExpectedConditions.elementToBeClickable(applyButton));
        try {
            apply.click();
        } catch (ElementClickInterceptedException ex) {
            // Fallback for transient overlays/animations.
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", apply);
        }
    }

    public void clickCancelButton() {
        getDriver().findElement(cancelButton).click();
    }

    public String getToastMessage() {
        WebDriverWait toastWait = new WebDriverWait(getDriver(), java.time.Duration.ofSeconds(30));
        toastWait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
        return getDriver().findElement(toastMessage).getText();
    }

}
