//Lathika
package com.leavepal.automation.stepDefinitions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.AdminDashboardpage;
import com.leavepal.automation.pages.ForgetPasswordPage;
import com.leavepal.automation.pages.LoginPage;
import com.leavepal.automation.utils.ConfigReader;
import com.leavepal.automation.utils.PageLocators;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ForgetPasswordSteps extends BaseClass {

    private final LoginPage loginPage = new LoginPage();
    private final ForgetPasswordPage forgetPasswordPage = new ForgetPasswordPage();
    private final AdminDashboardpage adminDashboardpage = new AdminDashboardpage();
    private String generatedPassword;

    @Given("user launches the application")
    public void launchApp() {
        Assert.assertTrue(getDriver().getCurrentUrl().contains("leave-management-app"),
                "The LeavePal application did not launch correctly.");
    }

    @When("user clicks forgot password")
    public void clickForgotPassword() {
        forgetPasswordPage.clickForgotPasswordLink();
    }

    @And("user enters email {string}")
    public void enterEmail(String email) {
        forgetPasswordPage.enterResetEmail(email);
    }

    @And("user sends reset request")
    public void sendRequest() {
        forgetPasswordPage.clickSendRequest();
    }

    @And("admin generates temporary password")
    public void adminGeneratesPassword() {
        loginPage.openHomePage();
        loginPage.selectHRLogin();
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();

        adminDashboardpage.waitForAdminResetWorkflow();
        adminDashboardpage.clickAdminResetNotification();
        adminDashboardpage.clickGenerateTempPassword();
        generatedPassword = adminDashboardpage.getGeneratedPassword();

        System.out.println("Generated Password: " + generatedPassword);

        adminDashboardpage.clickCloseButton();
        adminDashboardpage.clickLogout();
    }

    @And("user logs in with username {string} and generated password")
    public void loginWithGeneratedPassword(String username) {
        loginPage.selectManageroremployee();
        loginPage.enterUsername(username);
        loginPage.enterPassword(generatedPassword);
        loginPage.clickLoginButton();
    }

    @And("user resets password to {string}")
    public void resetPassword(String newPass) {
        forgetPasswordPage.enterNewPassword(newPass);
        forgetPasswordPage.enterConfirmPassword(newPass);
        forgetPasswordPage.clickResetPasswordButton();
    }

    /*
     * //@When("user logs in with new password {string}")
     * //public void loginWithNewPassword(String newPassword) {
     * // if
     * (!getDriver().getWindowHandles().contains(getDriver().getWindowHandle())) {
     * // String firstWindow = getDriver().getWindowHandles().iterator().next();
     * //getDriver().switchTo().window(firstWindow);
     * //}
     * 
     * loginPage.openHomePage();
     * getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(
     * "username")));
     * loginPage.selectManageroremployee();
     * loginPage.enterUsername("lathika@leavepal.com");
     * loginPage.enterPassword(newPassword);
     * loginPage.clickLoginButton();
     * }
     */

    @Then("user should login successfully")
    public void user_login_successfully_dashboard_after_login() {

        WebDriverWait wait = new WebDriverWait(getDriver(), java.time.Duration.ofSeconds(10));

        // ✅ Wait until user is redirected to dashboard
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("User navigated to: " + currentUrl);

        // ✅ Validate dashboard URL
        Assert.assertTrue(
                currentUrl.equals(ConfigReader.getRequiredProperty("app.dashboard.url")),
                "Login failed: User did not land on Dashboard page");
    }

    @Then("user should see reset request failure message")
    public void user_should_see_reset_request_failure_message() {

        String pageSource = getDriver().getPageSource().toLowerCase();

        Assert.assertTrue(
                pageSource.contains(
                        "no employee or manager account was found for that email or username"),
                "Expected reset request failure message was not displayed");
    }

    @Then("user should see email required error message")
    public void user_should_see_email_required_error_message() {

        // Locate the email input field
        WebElement emailField = getDriver().findElement(PageLocators.ForgotPassword.EMAIL_FIELD);

        // Get HTML5 validation message
        String validationMessage = emailField.getAttribute("validationMessage");

        System.out.println("Validation message: " + validationMessage);

        Assert.assertEquals(
                validationMessage,
                "Please fill out this field.",
                "Expected HTML5 required field validation message was not shown");
    }

}
