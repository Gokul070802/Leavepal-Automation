Feature: Apply Leave functionality of Leavepal application

    Scenario: Employee can apply for Sick Leave with valid details
        Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
        When Navigate to the Leave Tracker section
        And Click on the Apply Leave button
        And Fill in the leave application details with leaveType 'sick', fromDate '17-04-2026', toDate '20-04-2026' , reason 'Feeling unwell' and add medical certificate as attachment
        And Click on the Apply button
        Then The leave application should be submitted successfully

    Scenario: Manager approves the leave application
        Given The user is logged in as a manager with username 'ronaldo@leavepal.com' and password 'Ronaldo@123'
        When Navigate to the Leave Requests section
        And Click on the Pending Approvals button
        And Approve the leave application for employee 'messi@leavepal.com'
        Then The leave application should be approved successfully
        Then The leave application should be submitted successfully