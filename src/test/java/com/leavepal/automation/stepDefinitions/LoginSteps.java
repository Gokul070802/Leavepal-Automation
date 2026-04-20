package com.leavepal.automation.stepDefinitions;

import static org.testng.Assert.assertTrue;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.pages.LoginPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends BaseClass {
    LoginPage loginPage = new LoginPage();

    @Given("The user is on the login page and select the manageroremployee login")
    public void selectmanageroremployeelogin() {
        loginPage.selectManageroremployee();
    }

    @Given("The user is on the login page and select the HR login")
    public void selectHRLogin() {
        loginPage.selectHRLogin();
    }

    @When("Enter the username as {string}")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("Enter the password as {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Then("Click on the Login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting after login click", e);
        }
    }

    @Then("The user should be loggedin")
    public void verifyLogin() {
        String title = getDriver().getTitle();
        if (title.contains("Dashboard - LeavePal")) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }
    }

    @When("The user should not be loggedin")
    public void verifyFailedLogin() {
        String title = getDriver().getTitle();
        if (!title.contains("Dashboard - LeavePal")) {
            System.out.println("Login failed as expected");
        } else {
            System.out.println("Unexpected login success");
        }
    }

    @Then("The password should be masked and not visible in plain text")
    public void verifyPasswordMasked() {
        if (loginPage.isPasswordMasked()) {
            System.out.println("Password is masked");
        } else {
            System.out.println("Password is not masked");
        }
    }

    @And("Click on the eye icon to view the password")
    public void clickEyeIcon() {
        loginPage.clickEyeIcon();
    }

    @Then("The password should be visible in plain text")
    public void verifyPasswordVisible() {
        if (loginPage.isPasswordVisible()) {
            System.out.println("Password is visible");
        } else {
            System.out.println("Password is not visible");
        }
    }

    @And("Click on the eye icon again to hide the password")
    public void clickEyeIconAgain() {
        loginPage.clickEyeIcon();
    }

    @Then("The forgot password option should be visible on the login page")
    public void forgotPasswordOptionShouldBeVisible() {
        assertTrue(loginPage.isForgotPasswordVisible());
    }

    @When("Click on the forgot password option")
    public void clickForgotPasswordOption() {
        loginPage.clickForgotPasswordOption();
    }

    @And("Enter the email as {string}")
    public void enterEmailInForgotPassword(String email) {
        loginPage.enterEmailInForgotPassword(email);
    }

    @And("Click on the send request button")
    public void clickSendRequestButtonInForgotPassword() {
        loginPage.clickSendRequestButtonInForgotPassword();
    }

    @Then("A password reset link should be sent to the registered email address")
    public void verifyPasswordResetLinkSent() {
        String toastMessage = loginPage.getToastMessage();
        if (toastMessage.contains("Password reset request submitted")) {
            System.out.println("Password reset link sent successfully");
        } else {
            System.out.println("Failed to send password reset link");
        }
    }

    @Then("An error message should be displayed indicating that the email is not registered")
    public void verifyErrorMessageForNonRegisteredEmail() {
        String toastMessage = loginPage.getToastMessage();
        if (toastMessage.contains("No employee or manager account was found for that email or username")) {
            System.out.println("Error message displayed as expected");
        } else {
            System.out.println("Unexpected behavior: Error message not displayed");
        }
    }
}
