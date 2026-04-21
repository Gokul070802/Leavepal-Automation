package com.leavepal.automation.stepDefinitions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.LeaveRequestsPage;
import com.leavepal.automation.pages.LoginPage;
import com.leavepal.automation.utils.PageLocators;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ManagerLeaveApprovalSteps extends BaseClass {
    private final LoginPage loginPage = new LoginPage();
    private final LeaveRequestsPage leaveRequestsPage = new LeaveRequestsPage();
    private String approvedEmployeeIdentifier;
    private String rejectedEmployeeIdentifier;
    private int windowCountBeforeAttachmentView;
    private long downloadStartTimestampMillis;

    @Given("The user is logged in as a manager with username {string} and password {string}")
    public void theUserIsLoggedInAsAManager(String username, String password) {
        loginPage.selectManageroremployee();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.ManagerDashboard.LEAVE_REQUESTS_LINK));
    }

    @When("Navigate to the Leave Requests section")
    public void navigateToTheLeaveRequestsSection() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.ManagerDashboard.LEAVE_REQUESTS_LINK))
                .click();
        leaveRequestsPage.waitForLeaveRequestsTable();
    }

    @And("Click on the Pending Approvals button")
    public void clickOnThePendingApprovalsButton() {
        leaveRequestsPage.clickPendingApprovalsButton();
    }

    @And("Approve the leave application for employee {string}")
    public void approveTheLeaveApplicationForEmployee(String employeeIdentifier) {
        approvedEmployeeIdentifier = employeeIdentifier;
        leaveRequestsPage.clickApproveForEmployee(employeeIdentifier);
        leaveRequestsPage.confirmApprove();
    }

    @And("Approve the leave application for employee {string} with reason {string}")
    public void approveTheLeaveApplicationForEmployeeWithReason(String employeeIdentifier, String reason) {
        approvedEmployeeIdentifier = employeeIdentifier;
        leaveRequestsPage.clickApproveForEmployee(employeeIdentifier);
        leaveRequestsPage.confirmApproveWithComment(reason);
    }

    @Then("The leave application should be approved successfully")
    public void theLeaveApplicationShouldBeApprovedSuccessfully() {
        // ML4: "Leave request approved successfully."
        String toast = leaveRequestsPage.getToastMessage();
        Assert.assertTrue(toast.toLowerCase().contains("leave request approved successfully"),
                "Expected ML4 toast 'Leave request approved successfully.', but got: " + toast);

        Assert.assertNotNull(approvedEmployeeIdentifier,
                "No employee identifier captured from approval step.");

        leaveRequestsPage.selectStatusFilter("APPROVED");
        String status = getWait().until(driver -> {
            if (!leaveRequestsPage.isLeaveRequestShownForEmployee(approvedEmployeeIdentifier)) {
                return null;
            }
            String currentStatus = leaveRequestsPage.getStatusForEmployee(approvedEmployeeIdentifier);
            return "Approved".equalsIgnoreCase(currentStatus) ? currentStatus : null;
        });
        Assert.assertEquals(status.toLowerCase(), "approved",
                "Expected employee request status to become Approved in the table.");
    }

    @And("Reject the leave application for employee {string} with reason {string}")
    public void rejectTheLeaveApplicationForEmployee(String employeeIdentifier, String reason) {
        rejectedEmployeeIdentifier = employeeIdentifier;
        leaveRequestsPage.clickRejectForEmployee(employeeIdentifier);
        leaveRequestsPage.confirmReject(reason);
    }

    @Then("The leave application should be rejected successfully")
    public void theLeaveApplicationShouldBeRejectedSuccessfully() {
        // ML5: "Leave request rejected successfully."
        String toast = leaveRequestsPage.getToastMessage();
        Assert.assertTrue(toast.toLowerCase().contains("leave request rejected successfully"),
                "Expected ML5 toast 'Leave request rejected successfully.', but got: " + toast);

        Assert.assertNotNull(rejectedEmployeeIdentifier,
                "No employee identifier captured from rejection step.");

        leaveRequestsPage.selectStatusFilter("REJECTED");
        String status = getWait().until(driver -> {
            if (!leaveRequestsPage.isLeaveRequestShownForEmployee(rejectedEmployeeIdentifier)) {
                return null;
            }
            String currentStatus = leaveRequestsPage.getStatusForEmployee(rejectedEmployeeIdentifier);
            return "Rejected".equalsIgnoreCase(currentStatus) ? currentStatus : null;
        });
        Assert.assertEquals(status.toLowerCase(), "rejected",
                "Expected employee request status to become Rejected in the table.");
    }

    @And("View the sick leave attachment for employee {string}")
    public void viewTheSickLeaveAttachmentForEmployee(String employeeIdentifier) {
        windowCountBeforeAttachmentView = leaveRequestsPage.getWindowCount();
        leaveRequestsPage.clickViewAttachmentForEmployee(employeeIdentifier);
    }

    @Then("The sick leave attachment should open in a new tab")
    public void theSickLeaveAttachmentShouldOpenInANewTab() {
        // Guard: if ML7 popup-blocked toast appears, fail with a clear message
        // ML7: "Popup blocked. Please allow popups for preview."
        boolean newTabOpened = leaveRequestsPage.waitForNewTabToOpen(windowCountBeforeAttachmentView);
        if (!newTabOpened) {
            String toast = leaveRequestsPage.getToastMessageIfPresent(3);
            if (toast != null && toast.toLowerCase().contains("popup blocked")) {
                Assert.fail("ML7: Browser blocked the PDF preview popup. " +
                        "Ensure '--disable-popup-blocking' is set in Chrome options. Toast: " + toast);
            }
        }
        Assert.assertTrue(newTabOpened, "Attachment preview tab did not open.");
        leaveRequestsPage.closeNewTabAndSwitchBack();
    }

    @And("Download the sick leave attachment for employee {string}")
    public void downloadTheSickLeaveAttachmentForEmployee(String employeeIdentifier) {
        downloadStartTimestampMillis = System.currentTimeMillis();
        leaveRequestsPage.clickDownloadAttachmentForEmployee(employeeIdentifier);
    }

    @Then("The sick leave attachment PDF should be downloaded successfully")
    public void theSickLeaveAttachmentPDFShouldBeDownloadedSuccessfully() {
        Assert.assertTrue(leaveRequestsPage.isPdfDownloadedSince(downloadStartTimestampMillis),
                "PDF attachment was not downloaded to the Downloads folder.");
    }
}
