# #Dhanush Raj R

# Feature: Edit Profile functionality of LeavePal application

#   Scenario: User can view their profile information
#     Given The user is logged in as an employee with username "asta@leavepal.com" and password "Asta@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     Then The Edit Profile page should be displayed
#     And The employee name should be visible
#     And The employee ID field should be disabled
#     And The official email field should be disabled

#   Scenario: User can update profile with valid information
#     Given The user is logged in as an employee with username "asta@leavepal.com" and password "Asta@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     And The user enters phone number as "9876543210"
#     And The user enters nationality as "Indian"
#     And The user selects blood group as "O+"
#     And The user selects marital status as "Single"
#     And The user enters date of birth as "10-02-2003"
#     And The user enters personal email as "asta@example.com"
#     And The user selects gender as "Male"
#     And The user enters address as "123 Main Street, City, State"
#     And The user clicks on Save Changes button
#     Then The profile should be updated successfully

#   Scenario: User can cancel profile changes
#     Given The user is logged in as an employee with username "messi@leavepal.com" and password "Messi@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     And The user enters phone number as "9876543210"
#     And The user enters nationality as "Indian"
#     And The user clicks on Cancel button
#     Then The changes should be discarded

#   Scenario: User cannot save profile with invalid phone number
#     Given The user is logged in as an employee with username "messi@leavepal.com" and password "Messi@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     And The user enters phone number as "invalid-phone"
#     And The user clicks on Save Changes button
#     Then An error message should be displayed for phone field

#   Scenario: User cannot save profile with invalid date of birth
#     Given The user is logged in as an employee with username "messi@leavepal.com" and password "Messi@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     And The user enters date of birth as "invalid-date"
#     And The user clicks on Save Changes button
#     Then An error message should be displayed for date of birth field

#   Scenario: User cannot save profile with invalid personal email
#     Given The user is logged in as an employee with username "messi@leavepal.com" and password "Messi@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     And The user enters personal email as "invalid-email"
#     And The user clicks on Save Changes button
#     Then An error message should be displayed for personal email field

#   Scenario: User can logout from edit profile page
#     Given The user is logged in as an employee with username "messi@leavepal.com" and password "Messi@123"
#     When The user navigates to Employee Details page
#     And The user clicks on Edit Details button
#     And The user clicks on Logout button
#     Then The user should be logged out