# Feature: Apply Leave functionality of Leavepal application

#   Scenario: Employee can apply for Sick Leave with valid details and attachment for 3 days or more
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'sick', fromDate '17-04-2026', toDate '20-04-2026' , reason 'Feeling unwell' and add medical certificate as attachment
#     And Click on the Apply button
#     Then The leave application should be submitted successfully

#   Scenario: Employee can apply for Casual Leave with valid details
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'casual', fromDate '21-04-2026', toDate '22-04-2026', reason 'Personal work'
#     And Click on the Apply button
#     Then The leave application should be submitted successfully

#   Scenario: Employee can apply for Loss Of Pay leave directly
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'lop', fromDate '25-04-2026', toDate '25-04-2026', reason 'Personal emergency'
#     And Click on the Apply button
#     Then The leave application should be submitted successfully

#   Scenario: Loss of pay is automatically generated when casual leave balance is insufficient
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'casual', fromDate '18-04-2026', toDate '30-04-2026', reason 'Extended personal work'
#     And Click on the Apply button
#     Then The loss of pay generator should be displayed
#     And Confirm the loss of pay generator
#     Then The leave application should be submitted successfully

#   Scenario: Manager approves the leave application
#     Given The user is logged in as a manager with username 'ronaldo@leavepal.com' and password 'Ronaldo@123'
#     When Navigate to the Leave Requests section
#     And Click on the Pending Approvals button
#     And Approve the leave application for employee 'LP-003' with reason 'Approved for the requested dates'
#     Then The leave application should be approved successfully

#   Scenario: Manager rejects the leave application
#     Given The user is logged in as a manager with username 'ronaldo@leavepal.com' and password 'Ronaldo@123'
#     When Navigate to the Leave Requests section
#     And Click on the Pending Approvals button
#     And Reject the leave application for employee 'LP-003' with reason 'Cannot approve due to project deadlines'
#     Then The leave application should be rejected successfully

#   Scenario: Manager can view sick leave attachment PDF
#     Given The user is logged in as a manager with username 'ronaldo@leavepal.com' and password 'Ronaldo@123'
#     When Navigate to the Leave Requests section
#     And Click on the Pending Approvals button
#     And View the sick leave attachment for employee 'LP-003'
#     Then The sick leave attachment should open in a new tab

#   Scenario: Manager can download sick leave attachment PDF
#     Given The user is logged in as a manager with username 'ronaldo@leavepal.com' and password 'Ronaldo@123'
#     When Navigate to the Leave Requests section
#     And Click on the Pending Approvals button
#     And Download the sick leave attachment for employee 'LP-003'
#     Then The sick leave attachment PDF should be downloaded successfully

#   Scenario: Employee can apply for leave with same from and to date
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'casual', fromDate '24-04-2026', toDate '24-04-2026', Day type 'full', reason 'Personal work'
#     And Click on the Apply button
#     Then The leave application should be submitted successfully

#   Scenario: Employee can apply for leave with same from and to date
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'casual', fromDate '24-04-2026', toDate '24-04-2026', Day type 'half', reason 'Personal work'
#     And Click on the Apply button
#     Then The leave application should be submitted successfully

#   Scenario: Employee can apply for Sick Leave with valid details and no attachment for less than 3 days
#     Given The user is logged in as an employee with username 'messi@leavepal.com' and password 'Messi@123'
#     When Navigate to the Leave Tracker section
#     And Click on the Apply Leave button
#     And Fill in the leave application details with leaveType 'sick', fromDate '17-04-2026', toDate '18-04-2026' , reason 'Feeling unwell' and no attachment
#     And Click on the Apply button
#     Then The leave application should be submitted successfully
