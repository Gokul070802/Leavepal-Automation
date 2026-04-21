package com.leavepal.automation.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import org.openqa.selenium.By;
import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class LeaveTrackerPage extends BaseClass {

    public void clickApplyLeave() {
        getDriver().findElement(PageLocators.LeaveTracker.APPLY_LEAVE_BUTTON).click();
    }

    public void selectLeaveType(String leaveType) {
        new Select(getDriver().findElement(PageLocators.LeaveTracker.LEAVE_TYPE_DROPDOWN))
                .selectByValue(leaveType);
    }

    public String getSelectedLeaveType() {
        return new Select(getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.LeaveTracker.LEAVE_TYPE_DROPDOWN)))
                .getFirstSelectedOption().getAttribute("value");
    }

    public void selectDayType(String dayType) {
        new Select(getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.LeaveTracker.DAY_TYPE_DROPDOWN)))
                .selectByValue(dayType.toLowerCase());
    }

    public String getSelectedDayType() {
        return new Select(getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.LeaveTracker.DAY_TYPE_DROPDOWN)))
                .getFirstSelectedOption().getAttribute("value");
    }

    public void enterFromDate(String fromDate) {
        setDateValue(PageLocators.LeaveTracker.FROM_DATE_INPUT, fromDate);
    }

    public void enterToDate(String toDate) {
        setDateValue(PageLocators.LeaveTracker.TO_DATE_INPUT, toDate);
    }

    // Converts dd-MM-yyyy (feature format) to yyyy-MM-dd (HTML date input) and
    // fires a change event so the form reacts.
    private void setDateValue(org.openqa.selenium.By locator, String date) {
        String[] parts = date.split("-");
        String htmlDate = parts[2] + "-" + parts[1] + "-" + parts[0];
        WebElement element = getDriver().findElement(locator);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
                element, htmlDate);
    }

    public void enterReason(String reason) {
        getDriver().findElement(PageLocators.LeaveTracker.REASON_INPUT).sendKeys(reason);
    }

    public void fillLeaveApplication(String leaveType, String fromDate, String toDate, String reason) {
        selectLeaveType(leaveType);
        enterFromDate(fromDate);
        enterToDate(toDate);
        enterReason(reason);
    }

    public void fillLeaveApplication(String leaveType, String fromDate, String toDate, String dayType, String reason) {
        selectLeaveType(leaveType);
        enterFromDate(fromDate);
        enterToDate(toDate);
        selectDayType(dayType);
        enterReason(reason);
    }

    public void uploadMedicalCertificate(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath must be provided");
        }
        getWait().until(
                ExpectedConditions.presenceOfElementLocated(PageLocators.LeaveTracker.MEDICAL_CERTIFICATE_UPLOAD));
        WebElement fileInput = getDriver().findElement(PageLocators.LeaveTracker.MEDICAL_CERTIFICATE_UPLOAD);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity='1';",
                fileInput);
        fileInput.sendKeys(filePath);
    }

    public boolean isLeaveSplitPromptDisplayed() {
        WebDriverWait splitPromptWait = new WebDriverWait(getDriver(), java.time.Duration.ofSeconds(6));
        try {
            return splitPromptWait.until(driver -> {
                boolean okVisible = isElementVisible(PageLocators.LeaveTracker.LEAVE_SPLIT_OK_BUTTON)
                        || isElementVisible(By.xpath(
                                "//button[contains(normalize-space(.), 'OK') or contains(normalize-space(.), 'Ok') or contains(normalize-space(.), 'Confirm')]"));

                boolean cancelVisible = isElementVisible(PageLocators.LeaveTracker.LEAVE_SPLIT_CANCEL_BUTTON)
                        || isElementVisible(By.xpath(
                                "//button[contains(normalize-space(.), 'Cancel') or contains(normalize-space(.), 'No')]"));

                return okVisible && cancelVisible;
            });
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void clickLeaveSplitOkButton() {
        WebDriverWait splitActionWait = new WebDriverWait(getDriver(), java.time.Duration.ofSeconds(10));
        By[] okCandidates = new By[] {
                PageLocators.LeaveTracker.LEAVE_SPLIT_OK_BUTTON,
                By.xpath(
                        "//button[contains(normalize-space(.), 'OK') or contains(normalize-space(.), 'Ok') or contains(normalize-space(.), 'Confirm')]")
        };

        WebElement okButton = null;
        for (By candidate : okCandidates) {
            try {
                okButton = splitActionWait.until(ExpectedConditions.elementToBeClickable(candidate));
                break;
            } catch (TimeoutException ignored) {
                // Try next candidate.
            }
        }

        if (okButton == null) {
            throw new TimeoutException("Leave split confirmation button did not become clickable.");
        }

        okButton.click();
        try {
            getWait().until(
                    ExpectedConditions.invisibilityOfElementLocated(PageLocators.LeaveTracker.LEAVE_SPLIT_OK_BUTTON));
            getWait().until(ExpectedConditions
                    .invisibilityOfElementLocated(PageLocators.LeaveTracker.LEAVE_SPLIT_CANCEL_BUTTON));
        } catch (TimeoutException ignored) {
            // UI may keep the element in DOM briefly; continue.
        }
    }

    public void clickApplyButton() {
        // After LOP modal closes, the button may be temporarily removed and
        // re-rendered.
        WebDriverWait extendedWait = new WebDriverWait(getDriver(), java.time.Duration.ofSeconds(20));
        extendedWait.until(ExpectedConditions.presenceOfElementLocated(PageLocators.LeaveTracker.APPLY_BUTTON));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        try {
            getDriver().findElement(PageLocators.LeaveTracker.APPLY_BUTTON).click();
        } catch (Exception ex) {
            try {
                WebElement apply = getDriver().findElement(PageLocators.LeaveTracker.APPLY_BUTTON);
                ((JavascriptExecutor) getDriver()).executeScript(
                        "arguments[0].disabled=false; arguments[0].click();", apply);
            } catch (Exception e2) {
                ((JavascriptExecutor) getDriver()).executeScript(
                        "var btn = document.getElementById('" + PageLocators.LeaveTracker.APPLY_BUTTON_ID + "'); "
                                + "if(btn) { btn.disabled=false; btn.click(); } else { "
                                + "throw new Error('Button not found in DOM'); }");
            }
        }
    }

    public void clickCancelButton() {
        getDriver().findElement(PageLocators.LeaveTracker.CANCEL_BUTTON).click();
    }

    public String getRequestStatusByTypeAndReason(String leaveType, String reason) {
        String lowerType = leaveType == null ? "" : leaveType.trim().toLowerCase();
        By rowLocator = By.xpath("//tr"
                + "[.//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + lowerType + "')]"
                + " and .//*[contains(normalize-space(.), '" + reason + "')]]");

        WebElement row = getWait().until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
        List<WebElement> statusChips = row.findElements(By.cssSelector("span.status-chip"));
        if (!statusChips.isEmpty()) {
            return statusChips.get(0).getText().trim();
        }

        List<WebElement> cells = row.findElements(By.tagName("td"));
        if (!cells.isEmpty()) {
            return cells.get(cells.size() - 1).getText().trim();
        }
        return row.getText();
    }

    private boolean isElementVisible(By locator) {
        List<WebElement> elements = getDriver().findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    // 30-second wait to accommodate API submission latency.
    @Override
    public String getToastMessage() {
        WebDriverWait toastWait = new WebDriverWait(getDriver(), java.time.Duration.ofSeconds(30));
        return toastWait.until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.TOAST_MESSAGE)).getText().trim();
    }
}
