Feature: End-to-end leave lifecycle across Admin, Employee, and Manager

  Scenario: Admin creates manager and employee, leave lifecycle is processed, and admin verifies final leave data
    Given Admin logs in for end-to-end setup with username 'admin' and password 'admin123'
    When Admin creates a manager and an employee assigned to that manager for end-to-end flow
    And The current user logs out from dashboard
    And Employee logs in with the created employee credentials
    And Employee applies a 'sick' leave for end-to-end flow
    And Employee applies a 'casual' leave for end-to-end flow
    And The current user logs out from dashboard
    And Manager logs in with the created manager credentials
    And Manager approves the created employee 'sick' leave with comment 'Approved. Planned and manageable.'
    And Manager rejects the created employee 'casual' leave with comment 'Cannot approve because project deadline is near.'
    And The current user logs out from dashboard
    And Employee logs in with the created employee credentials
    Then Employee should see 'sick' leave status as 'Approved' for end-to-end flow
    And Employee should see 'casual' leave status as 'Rejected' for end-to-end flow
    And The current user logs out from dashboard
    And Admin logs in again for end-to-end verification with username 'admin' and password 'admin123'
    And Admin navigates to employee details for end-to-end verification
    Then Admin should see leave data updated for the created employee in end-to-end flow
    And The current user logs out from dashboard
