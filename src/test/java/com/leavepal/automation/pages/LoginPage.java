package com.leavepal.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.ConfigReader;
import com.leavepal.automation.utils.PageLocators;

public class LoginPage extends BaseClass {

    public void selectManageroremployee() {
        getDriver().findElement(PageLocators.Login.MANAGER_EMPLOYEE_BUTTON).click();
    }

    public void selectHRLogin() {
        getDriver().findElement(PageLocators.Login.HR_LOGIN_BUTTON).click();
    }

    public void enterUsername(String username) {
        getDriver().findElement(PageLocators.Login.USERNAME_FIELD).sendKeys(username);
    }

    public void enterPassword(String password) {
        getDriver().findElement(PageLocators.Login.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickLoginButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.Login.LOGIN_BUTTON)).click();
    }

    public void clickEyeIcon() {
        getDriver().findElement(PageLocators.Login.EYE_ICON).click();
    }

    public boolean isPasswordMasked() {
        WebElement field = getDriver().findElement(PageLocators.Login.PASSWORD_FIELD);
        return "password".equals(field.getAttribute("type"));
    }

    public boolean isPasswordVisible() {
        WebElement field = getDriver().findElement(PageLocators.Login.PASSWORD_FIELD);
        return "text".equals(field.getAttribute("type"));
    }

    public boolean isForgotPasswordVisible() {
        return getDriver().findElement(PageLocators.Login.FORGOT_PASSWORD_LINK).isDisplayed();
    }

    public void clickForgotPasswordOption() {
        getDriver().findElement(PageLocators.Login.FORGOT_PASSWORD_LINK).click();
    }

    public void enterEmailInForgotPassword(String email) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(PageLocators.ForgotPassword.EMAIL_FIELD))
                .sendKeys(email);
    }

    public void clickSendRequestButtonInForgotPassword() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.ForgotPassword.SUBMIT_BUTTON)).click();
    }

    public void openHomePage() {
        getDriver().get(ConfigReader.getRequiredProperty("app.url"));
    }
}
