package com.leavepal.automation.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class ForgetPasswordPage extends BaseClass {

    public void clickForgotPasswordLink() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.Login.FORGOT_PASSWORD_LINK)).click();
    }

    public void enterResetEmail(String email) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(PageLocators.ForgotPassword.EMAIL_FIELD))
                .sendKeys(email);
    }

    public void clickSendRequest() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.ForgotPassword.SUBMIT_BUTTON)).click();
    }

    public void enterNewPassword(String password) {
        getWait()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(PageLocators.ForgotPassword.FORCE_RESET_NEW_PASSWORD))
                .sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        getDriver().findElement(PageLocators.ForgotPassword.FORCE_RESET_CONFIRM_PASSWORD).sendKeys(password);
    }

    public void clickResetPasswordButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(PageLocators.ForgotPassword.RESET_PASSWORD_BUTTON))
                .click();
    }
}
