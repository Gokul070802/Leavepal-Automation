# Feature: Employee creation of Leavepal application

#   Scenario: Admin can create a new manager with valid details
#     Given The user is logged in as an admin with username 'admin' and password 'admin123'
#     When Navigate to the employee details section
#     And Click on the Add Employee button
#     And Fill in the manager details with role 'manager', email 'chotabheem@leavepal.com', password 'Manager@123', firstName 'Chota', lastName 'Bheem', department 'Engineering', designation 'Team Lead', location 'Chennai' and dateOfJoining '19-04-2026'
#     And Click on the Save button
#     Then A new manager should be created successfully

#   Scenario: Admin can create a new employee with valid details
#     Given The user is logged in as an admin with username 'admin' and password 'admin123'
#     When Navigate to the employee details section
#     And Click on the Add Employee button
#     And Fill in the employee details with role 'employee', email 'spiderman@leavepal.com', password 'Employee@123', firstName 'Peter', lastName 'Parker', department 'Engineering', designation 'Developer', reportingManager 'Chota Bheem (LP-008)', location 'Chennai' and dateOfJoining '19-04-2026'
#     And Click on the Save button
#     Then A new employee should be created successfully
