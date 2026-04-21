package com.leavepal.automation.utils;

import org.openqa.selenium.By;

/**
 * Central repository for all Selenium locators, grouped by page.
 * No page class or step definition should contain any hardcoded By expressions.
 */
public final class PageLocators {

    private PageLocators() {
    }

    // ─── Shared (app-wide) ────────────────────────────────────────────────────

    /** Toast notification stack used on every page. */
    public static final By TOAST_MESSAGE = By.id("leavepal-toast-stack");

    // ─── Login Page ───────────────────────────────────────────────────────────

    public static final class Login {
        private Login() {
        }

        public static final By MANAGER_EMPLOYEE_BUTTON = By.id("index-button-manager-employee-login-1");
        public static final By HR_LOGIN_BUTTON = By.id("index-button-hr-login-2");
        public static final By USERNAME_FIELD = By.id("username");
        public static final By PASSWORD_FIELD = By.id("password");
        public static final By LOGIN_BUTTON = By.id("index-button-login-4");
        public static final By FORGOT_PASSWORD_LINK = By.linkText("FORGOT PASSWORD?");
        public static final By EYE_ICON = By.id("togglePassword");
    }

    // ─── Forgot Password / Force-Reset Page ───────────────────────────────────

    public static final class ForgotPassword {
        private ForgotPassword() {
        }

        public static final By EMAIL_FIELD = By.id("email");
        public static final By SUBMIT_BUTTON = By.id("forgot-password-button-send-request-1");
        public static final By FORCE_RESET_NEW_PASSWORD = By.id("forceResetNewPassword");
        public static final By FORCE_RESET_CONFIRM_PASSWORD = By.id("forceResetConfirmPassword");
        public static final By RESET_PASSWORD_BUTTON = By.xpath("//button[text()='Reset Password']");
    }

    // ─── Admin Dashboard Page ─────────────────────────────────────────────────

    public static final class AdminDashboard {
        private AdminDashboard() {
        }

        public static final By EMPLOYEE_DETAILS_LINK = By.partialLinkText("Details");
        public static final By RESET_NOTIFICATION_ROOT = By.id("adminResetNotificationRoot");
        public static final By GENERATE_TEMP_PASSWORD_BUTTON = By
                .xpath("//button[contains(normalize-space(.), 'Generate Temporary Password')]");
        public static final By CONFIRM_CODE_DIV = By.xpath("//div[contains(@class, 'lp-confirm-code')]");
        public static final By CLOSE_BUTTON = By.xpath("//button[text()='Close']");
        public static final By LOGOUT_BUTTON = By.className("logout-btn");
    }

    // ─── Employee Dashboard Page ──────────────────────────────────────────────

    public static final class EmployeeDashboard {
        private EmployeeDashboard() {
        }

        public static final By LEAVE_TRACKER_LINK = By.linkText("Leave Tracker");
        public static final By EMPLOYEE_DETAILS_LINK = By.linkText("Employee Details");
        public static final By EDIT_DETAILS_BUTTON = By.xpath("//button[normalize-space()='Edit Details']");
    }

    // ─── Manager Dashboard Page ───────────────────────────────────────────────

    public static final class ManagerDashboard {
        private ManagerDashboard() {
        }

        public static final By LEAVE_REQUESTS_LINK = By.linkText("Leave Requests");
    }

    // ─── Leave Tracker Page ───────────────────────────────────────────────────

    public static final class LeaveTracker {
        private LeaveTracker() {
        }

        /** ID string kept as a constant so the JS-click fallback can reference it. */
        public static final String APPLY_BUTTON_ID = "apply-leave-button-apply-3";

        public static final By APPLY_LEAVE_BUTTON = By.id("leave-tracker-button-apply-leave-2");
        public static final By LEAVE_TYPE_DROPDOWN = By.id("leaveType");
        public static final By FROM_DATE_INPUT = By.id("fromDate");
        public static final By TO_DATE_INPUT = By.id("toDate");
        public static final By DAY_TYPE_DROPDOWN = By.id("dayType");
        public static final By REASON_INPUT = By.id("reason");
        public static final By MEDICAL_CERTIFICATE_UPLOAD = By.id("sickAttachment");
        public static final By APPLY_BUTTON = By.id(APPLY_BUTTON_ID);
        public static final By CANCEL_BUTTON = By.id("apply-leave-button-cancel-2");
        public static final By LEAVE_SPLIT_OK_BUTTON = By.id("leaveSplitOkBtn");
        public static final By LEAVE_SPLIT_CANCEL_BUTTON = By.id("leaveSplitCancelBtn");
    }

    // ─── Leave Requests Page (Manager view) ──────────────────────────────────

    public static final class LeaveRequests {
        private LeaveRequests() {
        }

        public static final By STATUS_FILTER = By.id("managerStatusFilter");
        public static final By TABLE_BODY = By.id("managerRequestsTableBody");
        public static final By TABLE_ROWS = By.cssSelector("#managerRequestsTableBody tr");
        public static final By PENDING_REQUESTS_COUNT = By.id("pendingRequestsCount");
        public static final By APPROVED_REQUESTS_COUNT = By.id("approvedRequestsCount");
        public static final By REJECTED_REQUESTS_COUNT = By.id("rejectedRequestsCount");

        // Approve modal
        public static final By APPROVE_MODAL_COMMENT = By.cssSelector("#approveModal:not(.hidden) #approvalComment");
        public static final By APPROVE_MODAL_CONFIRM_BUTTON = By
                .cssSelector("#approveModal:not(.hidden) button.primary-btn");

        // Reject modal
        public static final By REJECT_MODAL_REASON_FIELD = By.cssSelector("#rejectModal:not(.hidden) #rejectionReason");
        public static final By REJECT_MODAL_CONFIRM_BUTTON = By
                .cssSelector("#rejectModal:not(.hidden) button.danger-btn");
    }

    // ─── Employee Details / Add-Employee Page ─────────────────────────────────

    public static final class EmployeeDetails {
        private EmployeeDetails() {
        }

        public static final By ADD_EMPLOYEE_BUTTON = By.id("employee-details-button-add-employee-3");
        public static final By ROLE_DROPDOWN = By.id("role");
        public static final By EMAIL_FIELD = By.id("emailId");
        public static final By PASSWORD_FIELD = By.id("password");
        public static final By FIRST_NAME_FIELD = By.id("firstName");
        public static final By LAST_NAME_FIELD = By.id("lastName");
        public static final By DEPARTMENT_DROPDOWN = By.id("department");
        public static final By DESIGNATION_FIELD = By.id("designation");
        public static final By REPORTING_MANAGER_DROPDOWN = By.id("reportingEmployeeId");
        public static final By LOCATION_FIELD = By.id("location");
        public static final By DATE_OF_JOINING_FIELD = By.id("dateJoining");
        public static final By SAVE_BUTTON = By.id("saveEmployeeBtn");
        public static final By CANCEL_BUTTON = By.id("cancel-btn");

        // Workforce records table (admin view on employee-details.html)
        public static final By EMPLOYEE_TABLE_BODY = By.id("employeeTableBody");

        // Employee profile modal
        public static final By EMPLOYEE_PROFILE_MODAL = By.id("employeeProfileModal");
        public static final By EMPLOYEE_PROFILE_MODAL_CLOSE = By.cssSelector("#employeeProfileModal .close-button");
        public static final By MODAL_PROFILE_GRID = By.id("modalProfileGrid");

        // Temporary password confirmation dialog
        public static final By TEMP_PASSWORD_CONFIRM_BUTTON = By.cssSelector("button.lp-confirm-btn.lp-confirm-btn-ok");
    }

    // ─── Edit Profile Page ────────────────────────────────────────────────────

    public static final class EditProfile {
        private EditProfile() {
        }

        public static final By PAGE_CONTAINER = By.id("edit-profile-page");
        public static final By USER_NAME = By.cssSelector(".user-name");
        public static final By EMPLOYEE_ID = By.id("employeeIdInput");
        public static final By EMAIL_ID = By.id("emailInput");
        public static final By PHONE_INPUT = By.id("phoneInput");
        public static final By NATIONALITY_INPUT = By.id("nationalityInput");
        public static final By BLOOD_GROUP_SELECT = By.id("bloodGroupInput");
        public static final By MARITAL_STATUS_SELECT = By.id("maritalStatusInput");
        public static final By DOB_INPUT = By.id("dobInput");
        public static final By PERSONAL_EMAIL_INPUT = By.id("personalEmailInput");
        public static final By GENDER_SELECT = By.id("genderInput");
        public static final By ADDRESS_INPUT = By.id("addressInput");
        public static final By SAVE_BUTTON = By.id("edit-profile-button-save-changes-3");
        public static final By CANCEL_BUTTON = By.id("edit-profile-button-cancel-2");
        public static final By LOGOUT_BUTTON = By.id("edit-profile-button-logout-1");
        public static final By PHONE_ERROR = By.id("phoneError");
        public static final By DOB_ERROR = By.id("dobError");
        public static final By PERSONAL_EMAIL_ERROR = By.id("personalEmailError");
        public static final By ADDRESS_ERROR = By.id("addressError");
    }
}
