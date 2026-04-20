package com.leavepal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;

public class LoginPage extends BaseClass {

    // locators
    private final By managerOrEmployeeLoginButton = By.id("index-button-manager-employee-login-1");
    private final By hrLoginButton = By.id("index-button-hr-login-2");
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("index-button-login-4");
    private final By forgotPasswordLink = By.linkText("FORGOT PASSWORD?");
    private final By eyeIcon = By.id("togglePassword");
    private final By toastMessage = By.id("leavepal-toast-stack");
    private final By forgotPasswordUsernameField = By.name("email");
    private final By forgotPasswordSubmitButton = By.xpath("//button[text()='SEND REQUEST']");

    // method to select manager or employee login
    public void selectManageroremployee() {
        getDriver().findElement(managerOrEmployeeLoginButton).click();
    }

    // method to enter username
    public void enterUsername(String username) {
        getDriver().findElement(usernameField).sendKeys(username);
    }

    // method to enter password
    public void enterPassword(String password) {
        getDriver().findElement(passwordField).sendKeys(password);
    }

    // method to click on login button
    public void clickLoginButton() {
        getDriver().findElement(loginButton).click();
    }

    // method to click on forgot password link
    public void forgotPassword() {
        getDriver().findElement(forgotPasswordLink).click();
    }

    // method to select HR login
    public void selectHRLogin() {
        getDriver().findElement(hrLoginButton).click();
    }

    // toggle eye icon
    public void clickEyeIcon() {
        getDriver().findElement(eyeIcon).click();
    }

    // check if password is masked
    public boolean isPasswordMasked() {
        WebElement field = getDriver().findElement(passwordField);
        return field.getAttribute("type").equals("password");
    }

    // check if password is visible
    public boolean isPasswordVisible() {
        WebElement field = getDriver().findElement(passwordField);
        return field.getAttribute("type").equals("text");
    }

    // check if forgot password link is visible
    public boolean isForgotPasswordVisible() {
        return getDriver().findElement(forgotPasswordLink).isDisplayed();
    }

    // method to click on forgot password option
    public void clickForgotPasswordOption() {
        getDriver().findElement(forgotPasswordLink).click();
    }

    public String getToastMessage() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).getText();
    }

    public void enterEmailInForgotPassword(String email) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordUsernameField)).sendKeys(email);
    }

    public void clickSendRequestButtonInForgotPassword() {
        getWait().until(ExpectedConditions.elementToBeClickable(forgotPasswordSubmitButton)).click();
    }
}
