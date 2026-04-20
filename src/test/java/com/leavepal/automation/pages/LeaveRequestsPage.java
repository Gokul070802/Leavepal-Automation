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

public class LeaveRequestsPage extends BaseClass {
    // Locators
    private final By statusFilter = By.id("managerStatusFilter");
    private final By tableBody = By.id("managerRequestsTableBody");
    private final By tableRows = By.cssSelector("#managerRequestsTableBody tr");
    private final By toastMessage = By.id("leavepal-toast-stack");

    private final By pendingRequestsCount = By.id("pendingRequestsCount");
    private final By approvedRequestsCount = By.id("approvedRequestsCount");
    private final By rejectedRequestsCount = By.id("rejectedRequestsCount");

    // Methods
    // Method to wait for the leave requests table to be fully loaded
    public void waitForLeaveRequestsTable() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(tableBody));
        getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
    }

    // Method to select a status filter (e.g., PENDING, APPROVED, REJECTED)
    public void selectStatusFilter(String status) {
        Select filter = new Select(getWait().until(ExpectedConditions.visibilityOfElementLocated(statusFilter)));
        filter.selectByValue(status.toUpperCase());
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

    public void clickRejectForEmployee(String employeeIdentifier) {
        By rejectButton = By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]//button[contains(normalize-space(.), 'Reject')]");
        getWait().until(ExpectedConditions.elementToBeClickable(rejectButton)).click();
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

        long timeoutMillis = 15000;
        long deadline = System.currentTimeMillis() + timeoutMillis;

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
        List<WebElement> rows = getDriver().findElements(tableRows);
        if (rows.size() == 1 && rows.get(0).getText().contains("No subordinate leave requests found")) {
            return 0;
        }
        return rows.size();
    }

    public String getPendingRequestsCount() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(pendingRequestsCount)).getText().trim();
    }

    public String getApprovedRequestsCount() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(approvedRequestsCount)).getText().trim();
    }

    public String getRejectedRequestsCount() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(rejectedRequestsCount)).getText().trim();
    }

    public String getToastMessage() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).getText().trim();
    }

    private By rowByEmployee(String employeeIdentifier) {
        return By.xpath("//tbody[@id='managerRequestsTableBody']//tr[.//*[contains(normalize-space(.), '"
                + employeeIdentifier + "')]]");

    }

}
