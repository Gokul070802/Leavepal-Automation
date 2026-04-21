package com.leavepal.automation.pages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class LeaveRequestsPage extends BaseClass {

    public void waitForLeaveRequestsTable() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(PageLocators.LeaveRequests.TABLE_BODY));
        getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(PageLocators.LeaveRequests.TABLE_ROWS, 0));
    }

    public void selectStatusFilter(String status) {
        new Select(getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(PageLocators.LeaveRequests.STATUS_FILTER)))
                .selectByValue(status.toUpperCase());
    }

    public void clickPendingApprovalsButton() {
        selectStatusFilter("PENDING");
        waitForLeaveRequestsTable();
    }

    public boolean isLeaveRequestShownForEmployee(String employeeIdentifier) {
        waitForLeaveRequestsTable();
        return !getDriver().findElements(rowByEmployee(employeeIdentifier)).isEmpty();
    }

    public String getStatusForEmployee(String employeeIdentifier) {
        By statusCell = By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]//td[7]//span[contains(@class, 'status-chip')]");
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(statusCell)).getText().trim();
    }

    public void clickApproveForEmployee(String employeeIdentifier) {
        By approveButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]//button[contains(normalize-space(.), 'Approve')]");
        getWait().until(ExpectedConditions.elementToBeClickable(approveButton)).click();
    }

    public void clickApproveForEmployeeAndLeaveType(String employeeIdentifier, String leaveType) {
        By approveButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr"
                + "[.//*[contains(normalize-space(.), '" + employeeIdentifier + "')]"
                + " and .//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + leaveType.toLowerCase() + "')]]"
                + "//button[contains(normalize-space(.), 'Approve')]");
        getWait().until(ExpectedConditions.elementToBeClickable(approveButton)).click();
    }

    public void confirmApprove() {
        getWait()
                .until(ExpectedConditions.elementToBeClickable(PageLocators.LeaveRequests.APPROVE_MODAL_CONFIRM_BUTTON))
                .click();
    }

    public void confirmApproveWithComment(String comment) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(PageLocators.LeaveRequests.APPROVE_MODAL_COMMENT))
                .sendKeys(comment);
        getWait()
                .until(ExpectedConditions.elementToBeClickable(PageLocators.LeaveRequests.APPROVE_MODAL_CONFIRM_BUTTON))
                .click();
    }

    public void clickRejectForEmployee(String employeeIdentifier) {
        By rejectButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]//button[contains(normalize-space(.), 'Reject')]");
        getWait().until(ExpectedConditions.elementToBeClickable(rejectButton)).click();
    }

    public void clickRejectForEmployeeAndLeaveType(String employeeIdentifier, String leaveType) {
        By rejectButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr"
                + "[.//*[contains(normalize-space(.), '" + employeeIdentifier + "')]"
                + " and .//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + leaveType.toLowerCase() + "')]]"
                + "//button[contains(normalize-space(.), 'Reject')]");
        getWait().until(ExpectedConditions.elementToBeClickable(rejectButton)).click();
    }

    public void confirmReject(String reason) {
        getWait()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(PageLocators.LeaveRequests.REJECT_MODAL_REASON_FIELD))
                .sendKeys(reason);
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.LeaveRequests.REJECT_MODAL_CONFIRM_BUTTON))
                .click();
    }

    public void clickViewAttachmentForEmployee(String employeeIdentifier) {
        By viewButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]//button[contains(normalize-space(.), 'View')]");
        getWait().until(ExpectedConditions.elementToBeClickable(viewButton)).click();
    }

    public void clickDownloadAttachmentForEmployee(String employeeIdentifier) {
        By downloadButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]//button[contains(normalize-space(.), 'Download')]");
        getWait().until(ExpectedConditions.elementToBeClickable(downloadButton)).click();
    }

    public int getWindowCount() {
        return getDriver().getWindowHandles().size();
    }

    public boolean waitForNewTabToOpen(int previousWindowCount) {
        try {
            return getWait().until(driver -> driver.getWindowHandles().size() > previousWindowCount);
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void closeNewTabAndSwitchBack() {
        Set<String> handles = getDriver().getWindowHandles();
        if (handles.size() <= 1) {
            return;
        }
        String original = getDriver().getWindowHandle();
        for (String handle : handles) {
            if (!handle.equals(original)) {
                getDriver().switchTo().window(handle);
                getDriver().close();
            }
        }
        getDriver().switchTo().window(original);
    }

    public boolean isPdfDownloadedSince(long startTimestampMillis) {
        Path configuredDownloadPath = Paths.get(System.getProperty("user.dir"), "target", "downloads");
        Path userDownloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");

        long deadline = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < deadline) {
            for (Path pathToScan : new Path[] { configuredDownloadPath, userDownloadsPath }) {
                if (!Files.exists(pathToScan)) {
                    continue;
                }
                try (Stream<Path> files = Files.list(pathToScan)) {
                    boolean found = files
                            .filter(path -> Files.isRegularFile(path))
                            .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".pdf"))
                            .anyMatch(path -> {
                                try {
                                    return Files.getLastModifiedTime(path).toMillis() >= startTimestampMillis;
                                } catch (IOException e) {
                                    return false;
                                }
                            });
                    if (found) {
                        return true;
                    }
                } catch (IOException ignored) {
                    // Retry until timeout.
                }
            }
        }
        return false;
    }

    public int getVisibleRequestCount() {
        waitForLeaveRequestsTable();
        List<WebElement> rows = getDriver().findElements(PageLocators.LeaveRequests.TABLE_ROWS);
        if (rows.size() == 1 && rows.get(0).getText().contains("No subordinate leave requests found")) {
            return 0;
        }
        return rows.size();
    }

    public String getPendingRequestsCount() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(
                PageLocators.LeaveRequests.PENDING_REQUESTS_COUNT)).getText().trim();
    }

    public String getApprovedRequestsCount() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(
                PageLocators.LeaveRequests.APPROVED_REQUESTS_COUNT)).getText().trim();
    }

    public String getRejectedRequestsCount() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(
                PageLocators.LeaveRequests.REJECTED_REQUESTS_COUNT)).getText().trim();
    }

    // getToastMessage() and getToastMessageIfPresent(int) are inherited from
    // BaseClass.

    private By rowByEmployee(String employeeIdentifier) {
        return By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]");
    }

}
