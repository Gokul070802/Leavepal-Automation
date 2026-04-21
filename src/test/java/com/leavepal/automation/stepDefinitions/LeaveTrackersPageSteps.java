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

    @And("Fill in the leave application details with leaveType {string}, fromDate {string}, toDate {string}, reason {string}")
    public void fillInTheLeaveApplicationDetailsWithoutAttachment(String leaveType, String fromDate, String toDate,
            String reason) {
        leaveTrackerPage.fillLeaveApplication(leaveType, fromDate, toDate, reason);
    }

    @And("Fill in the leave application details with leaveType {string}, fromDate {string}, toDate {string}, Day type {string}, reason {string}")
    public void fillInTheLeaveApplicationDetailsWithDayType(String leaveType, String fromDate, String toDate,
            String dayType, String reason) {
        leaveTrackerPage.fillLeaveApplication(leaveType, fromDate, toDate, dayType, reason);
    }

    @And("Fill in the leave application details with leaveType {string}, fromDate {string}, toDate {string} , reason {string} and no attachment")
    public void fillInSickLeaveDetailsWithoutAttachment(String leaveType, String fromDate, String toDate,
            String reason) {
        leaveTrackerPage.fillLeaveApplication(leaveType, fromDate, toDate, reason);
    }

    @And("Click on the Apply button")
    public void clickOnTheApplyButton() {
        leaveTrackerPage.clickApplyButton();
    }

    @Then("The leave application should be submitted successfully")
    public void theLeaveApplicationShouldBeSubmittedSuccessfully() {
        // AL13: "Leave applied successfully! Sent to your manager for review."
        // AL12: "Leave submitted as {N} day(s) {Sick/Casual} + {M} day(s) Loss of Pay.
        // Both sent to your manager for approval."
        String toastMessage = leaveTrackerPage.getToastMessage();
        String lower = toastMessage.toLowerCase();
        boolean isSingleLeave = lower.contains("leave applied successfully");
        boolean isSplitLOP = lower.contains("loss of pay") && lower.contains("both sent to your manager");
        Assert.assertTrue(isSingleLeave || isSplitLOP,
                "Expected AL13 ('Leave applied successfully') or AL12 ('Loss of Pay...Both sent') toast, but got: "
                        + toastMessage);
    }

    @Then("The loss of pay generator should be displayed")
    public void theLossOfPayGeneratorShouldBeDisplayed() {
        Assert.assertTrue(leaveTrackerPage.isLeaveSplitPromptDisplayed(),
                "Loss of pay generator prompt was not displayed.");
    }

    @And("Confirm the loss of pay generator")
    public void confirmTheLossOfPayGenerator() {
        leaveTrackerPage.clickLeaveSplitOkButton();
    }

}
