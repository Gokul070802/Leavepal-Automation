
package com.leavepal.automation.pages;

import org.openqa.selenium.support.ui.Select;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.PageLocators;

public class EditProfilePage extends BaseClass {

    public boolean isPageLoaded() {
        return getDriver().findElement(PageLocators.EditProfile.PAGE_CONTAINER).isDisplayed();
    }

    public String getUserName() {
        return getDriver().findElement(PageLocators.EditProfile.USER_NAME).getText();
    }

    public boolean isEmployeeIdDisabled() {
        return !getDriver().findElement(PageLocators.EditProfile.EMPLOYEE_ID).isEnabled();
    }

    public boolean isEmailDisabled() {
        return !getDriver().findElement(PageLocators.EditProfile.EMAIL_ID).isEnabled();
    }

    public void enterPhone(String phone) {
        getDriver().findElement(PageLocators.EditProfile.PHONE_INPUT).clear();
        getDriver().findElement(PageLocators.EditProfile.PHONE_INPUT).sendKeys(phone);
    }

    public void enterNationality(String nationality) {
        getDriver().findElement(PageLocators.EditProfile.NATIONALITY_INPUT).clear();
        getDriver().findElement(PageLocators.EditProfile.NATIONALITY_INPUT).sendKeys(nationality);
    }

    public void selectBloodGroup(String bloodGroup) {
        new Select(getDriver().findElement(PageLocators.EditProfile.BLOOD_GROUP_SELECT))
                .selectByVisibleText(bloodGroup);
    }

    public void selectMaritalStatus(String maritalStatus) {
        new Select(getDriver().findElement(PageLocators.EditProfile.MARITAL_STATUS_SELECT))
                .selectByVisibleText(maritalStatus);
    }

    public void enterDOB(String dob) {
        getDriver().findElement(PageLocators.EditProfile.DOB_INPUT).clear();
        getDriver().findElement(PageLocators.EditProfile.DOB_INPUT).sendKeys(dob);
    }

    public void enterPersonalEmail(String email) {
        getDriver().findElement(PageLocators.EditProfile.PERSONAL_EMAIL_INPUT).clear();
        getDriver().findElement(PageLocators.EditProfile.PERSONAL_EMAIL_INPUT).sendKeys(email);
    }

    public void selectGender(String gender) {
        new Select(getDriver().findElement(PageLocators.EditProfile.GENDER_SELECT))
                .selectByVisibleText(gender);
    }

    public void enterAddress(String address) {
        getDriver().findElement(PageLocators.EditProfile.ADDRESS_INPUT).clear();
        getDriver().findElement(PageLocators.EditProfile.ADDRESS_INPUT).sendKeys(address);
    }

    public void saveChanges() {
        getDriver().findElement(PageLocators.EditProfile.SAVE_BUTTON).click();
    }

    public void cancelEdit() {
        getDriver().findElement(PageLocators.EditProfile.CANCEL_BUTTON).click();
    }

    public void logout() {
        getDriver().findElement(PageLocators.EditProfile.LOGOUT_BUTTON).click();
    }

    public String getPhoneError() {
        return getDriver().findElement(PageLocators.EditProfile.PHONE_ERROR).getText();
    }

    public String getDobError() {
        return getDriver().findElement(PageLocators.EditProfile.DOB_ERROR).getText();
    }

    public String getPersonalEmailError() {
        return getDriver().findElement(PageLocators.EditProfile.PERSONAL_EMAIL_ERROR).getText();
    }

    public String getAddressError() {
        return getDriver().findElement(PageLocators.EditProfile.ADDRESS_ERROR).getText();
    }

    public String getEmployeeId() {
        return getDriver().findElement(PageLocators.EditProfile.EMPLOYEE_ID).getAttribute("value");
    }

    public String getEmail() {
        return getDriver().findElement(PageLocators.EditProfile.EMAIL_ID).getAttribute("value");
    }

    public String getPhone() {
        return getDriver().findElement(PageLocators.EditProfile.PHONE_INPUT).getAttribute("value");
    }

    public String getNationality() {
        return getDriver().findElement(PageLocators.EditProfile.NATIONALITY_INPUT).getAttribute("value");
    }

    public String getBloodGroup() {
        return new Select(getDriver().findElement(PageLocators.EditProfile.BLOOD_GROUP_SELECT))
                .getFirstSelectedOption().getText();
    }

    public String getMaritalStatus() {
        return new Select(getDriver().findElement(PageLocators.EditProfile.MARITAL_STATUS_SELECT))
                .getFirstSelectedOption().getText();
    }

    public String getDOB() {
        return getDriver().findElement(PageLocators.EditProfile.DOB_INPUT).getAttribute("value");
    }

    public String getPersonalEmail() {
        return getDriver().findElement(PageLocators.EditProfile.PERSONAL_EMAIL_INPUT).getAttribute("value");
    }

    public String getGender() {
        return new Select(getDriver().findElement(PageLocators.EditProfile.GENDER_SELECT))
                .getFirstSelectedOption().getText();
    }

    public String getAddress() {
        return getDriver().findElement(PageLocators.EditProfile.ADDRESS_INPUT).getAttribute("value");
    }

}