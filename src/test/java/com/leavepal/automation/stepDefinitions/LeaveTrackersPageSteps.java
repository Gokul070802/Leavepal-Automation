package com.leavepal.automation.stepDefinitions;

import org.testng.Assert;

import com.leavepal.automation.pages.LeaveTrackerPage;
import com.leavepal.automation.utils.ConfigReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class LeaveTrackersPageSteps {
    LeaveTrackerPage leaveTrackerPage = new LeaveTrackerPage();

    @And("Click on the Apply Leave button")
    public void clickOnTheApplyLeaveButton() {
        leaveTrackerPage.clickApplyLeave();
    }

    @And("Fill in the leave application details with leaveType {string}, fromDate {string}, toDate {string} , reason {string} and add medical certificate as attachment")
    public void fillInTheLeaveApplicationDetails(String leaveType, String fromDate, String toDate, String reason) {
        String filePath = ConfigReader.getRequiredProperty("medical.certificate.path");
        leaveTrackerPage.selectLeaveType(leaveType);
        leaveTrackerPage.enterFromDate(fromDate);
        leaveTrackerPage.enterToDate(toDate);
        leaveTrackerPage.enterReason(reason);
        leaveTrackerPage.uploadMedicalCertificate(filePath);
    }

    @And("Click on the Apply button")
    public void clickOnTheApplyButton() {
        leaveTrackerPage.clickApplyButton();
    }

    @Then("The leave application should be submitted successfully")
    public void theLeaveApplicationShouldBeSubmittedSuccessfully() {
        Assert.assertTrue(leaveTrackerPage.getToastMessage().contains("Leave applied successfully"),
                "Toast message not found or unexpected");
    }
}
