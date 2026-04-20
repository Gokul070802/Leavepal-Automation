package com.leavepal.automation.stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.LeaveRequestsPage;
import com.leavepal.automation.pages.LoginPage;

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
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Leave Requests")));
    }

    @When("Navigate to the Leave Requests section")
    public void navigateToTheLeaveRequestsSection() {
        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("Leave Requests"))).click();
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
        By confirmApproveButton = By.cssSelector("#approveModal:not(.hidden) button.primary-btn");
        getWait().until(ExpectedConditions.elementToBeClickable(confirmApproveButton)).click();
    }

    @And("Approve the leave application for employee {string} with reason {string}")
    public void approveTheLeaveApplicationForEmployeeWithReason(String employeeIdentifier, String reason) {
        approvedEmployeeIdentifier = employeeIdentifier;
        leaveRequestsPage.clickApproveForEmployee(employeeIdentifier);
        By approvalCommentField = By.cssSelector("#approveModal:not(.hidden) #approvalComment");
        By confirmApproveButton = By.cssSelector("#approveModal:not(.hidden) button.primary-btn");
        getWait().until(ExpectedConditions.visibilityOfElementLocated(approvalCommentField)).sendKeys(reason);
        getWait().until(ExpectedConditions.elementToBeClickable(confirmApproveButton)).click();
    }

    @Then("The leave application should be approved successfully")
    public void theLeaveApplicationShouldBeApprovedSuccessfully() {
        String toast = leaveRequestsPage.getToastMessage();
        Assert.assertTrue(toast.toLowerCase().contains("approved") || toast.toLowerCase().contains("success"),
                "Expected approval success message, but got: " + toast);

        Assert.assertNotNull(approvedEmployeeIdentifier,
                "No employee identifier captured from approval step.");
        String status = getWait().until(driver -> {
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
        By rejectionReasonField = By.cssSelector("#rejectModal:not(.hidden) #rejectionReason");
        By confirmRejectButton = By.cssSelector("#rejectModal:not(.hidden) button.danger-btn");
        getWait().until(ExpectedConditions.visibilityOfElementLocated(rejectionReasonField)).sendKeys(reason);
        getWait().until(ExpectedConditions.elementToBeClickable(confirmRejectButton)).click();
    }

    @Then("The leave application should be rejected successfully")
    public void theLeaveApplicationShouldBeRejectedSuccessfully() {
        String toast = leaveRequestsPage.getToastMessage();
        Assert.assertTrue(toast.toLowerCase().contains("rejected") || toast.toLowerCase().contains("success"),
                "Expected rejection success message, but got: " + toast);

        Assert.assertNotNull(rejectedEmployeeIdentifier,
                "No employee identifier captured from rejection step.");
        String status = getWait().until(driver -> {
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
        Assert.assertTrue(leaveRequestsPage.waitForNewTabToOpen(windowCountBeforeAttachmentView),
                "Attachment preview tab did not open.");
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
