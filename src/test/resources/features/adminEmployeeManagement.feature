# Feature: Admin workforce management on Employee Details page

#   Background:
#     Given The user is logged in as an admin with username 'admin' and password 'admin123'
#     And Navigate to the employee details section

#   Scenario: Admin can view the profile of an employee from the workforce table
#     When Admin clicks View Profile for employee 'Gokulakannan R'
#     Then The employee profile modal is displayed
#     And The modal profile shows full name 'Gokulakannan R'
#     And Admin closes the employee profile modal

#   Scenario: Admin can set a temporary password for an employee
#     When Admin clicks Set Temp Password for employee 'Neymar Junior'
#     And Admin confirms temporary password generation
#     Then A temporary password confirmation message is shown

#   Scenario: Admin can delete an employee from the workforce table
#     When Admin clicks Delete for employee 'spiderman'
#     Then Employee 'spiderman' is no longer in the workforce table
