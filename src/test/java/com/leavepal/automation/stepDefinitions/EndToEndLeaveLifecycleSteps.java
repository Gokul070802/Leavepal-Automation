package com.leavepal.automation.stepDefinitions;

import java.time.LocalDate;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.AdminDashboardpage;
import com.leavepal.automation.pages.EmployeedashboardPage;
import com.leavepal.automation.pages.Employeedetailspage;
import com.leavepal.automation.pages.LeaveRequestsPage;
import com.leavepal.automation.pages.LeaveTrackerPage;
import com.leavepal.automation.pages.LoginPage;
import com.leavepal.automation.utils.PageLocators;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EndToEndLeaveLifecycleSteps extends BaseClass {

    private final LoginPage loginPage = new LoginPage();
    private final AdminDashboardpage adminDashboardPage = new AdminDashboardpage();
    private final EmployeedashboardPage employeeDashboardPage = new EmployeedashboardPage();
    private final Employeedetailspage employeeDetailsPage = new Employeedetailspage();
    private final LeaveTrackerPage leaveTrackerPage = new LeaveTrackerPage();
    private final LeaveRequestsPage leaveRequestsPage = new LeaveRequestsPage();

    private String createdManagerEmail;
    private String createdManagerPassword;
    private String createdManagerFullName;

    private String createdEmployeeEmail;
    private String createdEmployeePassword;
    private String createdEmployeeFullName;

    private String sickLeaveReason;
    private String casualLeaveReason;

    @Given("Admin logs in for end-to-end setup with username {string} and password {string}")
    public void adminLogsInForEndToEndSetup(String username, String password) {
        loginPage.selectHRLogin();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        getWait().until(ExpectedConditions.or(
                ExpectedConditions.titleContains("HR Dashboard"),
                ExpectedConditions.urlContains("dashboard")));
    }

    @When("Admin creates a manager and an employee assigned to that manager for end-to-end flow")
    public void adminCreatesManagerAndEmployeeForEndToEndFlow() {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis() % 1000000);

        String managerFirstName = "E2EManager" + uniqueSuffix;
        String managerLastName = "Auto";
        createdManagerFullName = managerFirstName + " " + managerLastName;
        createdManagerEmail = "e2e.manager." + uniqueSuffix + "@leavepal.com";
        createdManagerPassword = "Manager@123";

        String employeeFirstName = "E2EEmployee" + uniqueSuffix;
        String employeeLastName = "Auto";
        createdEmployeeFullName = employeeFirstName + " " + employeeLastName;
        createdEmployeeEmail = "e2e.employee." + uniqueSuffix + "@leavepal.com";
        createdEmployeePassword = "Employee@123";

        adminDashboardPage.selectEmployeeDetails();

        employeeDetailsPage.clickAddEmployee();
        employeeDetailsPage.selectRole("manager");
        employeeDetailsPage.enterEmail(createdManagerEmail);
        employeeDetailsPage.enterPassword(createdManagerPassword);
        employeeDetailsPage.enterFirstName(managerFirstName);
        employeeDetailsPage.enterLastName(managerLastName);
        employeeDetailsPage.selectDepartment("Engineering");
        employeeDetailsPage.enterDesignation("Team Lead");
        employeeDetailsPage.enterLocation("Chennai");
        employeeDetailsPage.selectDateOfJoining(
                LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        employeeDetailsPage.clickSaveButton();

        Assert.assertTrue(employeeDetailsPage.waitForCreationCompletion(),
                "Manager creation did not complete.");

        employeeDetailsPage.clickAddEmployee();
        employeeDetailsPage.selectRole("employee");
        employeeDetailsPage.enterEmail(createdEmployeeEmail);
        employeeDetailsPage.enterPassword(createdEmployeePassword);
        employeeDetailsPage.enterFirstName(employeeFirstName);
        employeeDetailsPage.enterLastName(employeeLastName);
        employeeDetailsPage.selectDepartment("Engineering");
        employeeDetailsPage.enterDesignation("Developer");
        employeeDetailsPage.selectReportingManagerByNameContains(createdManagerFullName);
        employeeDetailsPage.enterLocation("Chennai");
        employeeDetailsPage.selectDateOfJoining(
                LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        employeeDetailsPage.clickSaveButton();

        Assert.assertTrue(employeeDetailsPage.waitForCreationCompletion(),
                "Employee creation did not complete.");
    }

    @And("The current user logs out from dashboard")
    public void theCurrentUserLogsOutFromDashboard() {
        if (!getDriver().findElements(PageLocators.AdminDashboard.LOGOUT_BUTTON).isEmpty()) {
            getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.AdminDashboard.LOGOUT_BUTTON)).click();
        } else if (!getDriver().findElements(PageLocators.EditProfile.LOGOUT_BUTTON).isEmpty()) {
            getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.EditProfile.LOGOUT_BUTTON)).click();
        } else {
            throw new TimeoutException("Logout button not found on the current page.");
        }

        getWait().until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.Login.MANAGER_EMPLOYEE_BUTTON),
                ExpectedConditions.urlContains("index")));
    }

    @And("Employee logs in with the created employee credentials")
    public void employeeLogsInWithTheCreatedEmployeeCredentials() {
        loginPage.selectManageroremployee();
        loginPage.enterUsername(createdEmployeeEmail);
        loginPage.enterPassword(createdEmployeePassword);
        loginPage.clickLoginButton();
        employeeDashboardPage.waitForDashboard();
        employeeDashboardPage.navigateToLeaveTracker();
    }

    @And("Employee applies a {string} leave for end-to-end flow")
    public void employeeAppliesLeaveForEndToEndFlow(String leaveType) {
        leaveTrackerPage.clickApplyLeave();

        LocalDate baseDate = LocalDate.now().plusDays(1);
        if ("casual".equalsIgnoreCase(leaveType)) {
            baseDate = LocalDate.now().plusDays(2);
        }

        String date = baseDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String reason = "E2E-" + leaveType.toUpperCase() + "-" + (System.currentTimeMillis() % 1000000);

        leaveTrackerPage.fillLeaveApplication(leaveType, date, date, reason);
        leaveTrackerPage.clickApplyButton();

        String toastMessage = leaveTrackerPage.getToastMessage().toLowerCase();
        Assert.assertTrue(toastMessage.contains("leave applied successfully")
                || (toastMessage.contains("loss of pay") && toastMessage.contains("both sent to your manager")),
                "Leave submission did not succeed for leaveType=" + leaveType + ". Toast: " + toastMessage);

        if ("sick".equalsIgnoreCase(leaveType)) {
            sickLeaveReason = reason;
        } else if ("casual".equalsIgnoreCase(leaveType)) {
            casualLeaveReason = reason;
        }
    }

    @And("Manager logs in with the created manager credentials")
    public void managerLogsInWithTheCreatedManagerCredentials() {
        loginPage.selectManageroremployee();
        loginPage.enterUsername(createdManagerEmail);
        loginPage.enterPassword(createdManagerPassword);
        loginPage.clickLoginButton();

        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(PageLocators.ManagerDashboard.LEAVE_REQUESTS_LINK));
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.ManagerDashboard.LEAVE_REQUESTS_LINK))
                .click();
        leaveRequestsPage.waitForLeaveRequestsTable();
        leaveRequestsPage.clickPendingApprovalsButton();
    }

    @And("Manager approves the created employee {string} leave with comment {string}")
    public void managerApprovesTheCreatedEmployeeLeaveWithComment(String leaveType, String comment) {
        leaveRequestsPage.clickApproveForEmployeeAndLeaveType(createdEmployeeFullName, leaveType);
        leaveRequestsPage.confirmApproveWithComment(comment);

        String toast = leaveRequestsPage.getToastMessage().toLowerCase();
        Assert.assertTrue(toast.contains("leave request approved successfully"),
                "Expected approval success toast but got: " + toast);
    }

    @And("Manager rejects the created employee {string} leave with comment {string}")
    public void managerRejectsTheCreatedEmployeeLeaveWithComment(String leaveType, String comment) {
        leaveRequestsPage.clickRejectForEmployeeAndLeaveType(createdEmployeeFullName, leaveType);
        leaveRequestsPage.confirmReject(comment);

        String toast = leaveRequestsPage.getToastMessage().toLowerCase();
        Assert.assertTrue(toast.contains("leave request rejected successfully"),
                "Expected rejection success toast but got: " + toast);
    }

    @Then("Employee should see {string} leave status as {string} for end-to-end flow")
    public void employeeShouldSeeLeaveStatusForEndToEndFlow(String leaveType, String expectedStatus) {
        String reason = "sick".equalsIgnoreCase(leaveType) ? sickLeaveReason : casualLeaveReason;
        String actualStatus = leaveTrackerPage.getRequestStatusByTypeAndReason(leaveType, reason);

        Assert.assertTrue(actualStatus.equalsIgnoreCase(expectedStatus),
                "Expected status '" + expectedStatus + "' for leaveType '" + leaveType + "' but got: " + actualStatus);
    }

    @And("Admin logs in again for end-to-end verification with username {string} and password {string}")
    public void adminLogsInAgainForEndToEndVerification(String username, String password) {
        loginPage.selectHRLogin();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        getWait().until(ExpectedConditions.or(
                ExpectedConditions.titleContains("HR Dashboard"),
                ExpectedConditions.urlContains("dashboard")));
    }

    @And("Admin navigates to employee details for end-to-end verification")
    public void adminNavigatesToEmployeeDetailsForEndToEndVerification() {
        adminDashboardPage.selectEmployeeDetails();
        employeeDetailsPage.waitForAdminLeaveDataTable();
    }

    @Then("Admin should see leave data updated for the created employee in end-to-end flow")
    public void adminShouldSeeLeaveDataUpdatedForCreatedEmployeeInEndToEndFlow() {
        int sickAvailable = employeeDetailsPage.getAdminLeaveDataMetric(createdEmployeeFullName, 5);
        int casualAvailable = employeeDetailsPage.getAdminLeaveDataMetric(createdEmployeeFullName, 6);
        int sickBooked = employeeDetailsPage.getAdminLeaveDataMetric(createdEmployeeFullName, 7);
        int casualBooked = employeeDetailsPage.getAdminLeaveDataMetric(createdEmployeeFullName, 8);

        Assert.assertTrue(sickBooked >= 1,
                "Expected at least 1 sick leave booked after manager approval, but got: " + sickBooked);
        Assert.assertEquals(casualBooked, 0,
                "Expected casual leave booked to remain 0 because it was rejected.");
        Assert.assertTrue(sickAvailable <= 11,
                "Expected sick available leaves to reduce from default after approval. Current value: "
                        + sickAvailable);
        Assert.assertTrue(casualAvailable >= 11,
                "Casual available leave dropped unexpectedly after rejection. Current value: " + casualAvailable);
    }
}
