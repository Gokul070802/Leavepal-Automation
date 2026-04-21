# Feature: Forget Password Flow

#   @forgetPassword
#   Scenario: Employee resets password using admin generated password
#     Given user launches the application
#     When user clicks forgot password
#     And user enters email "neymar@leavepal.com"
#     And user sends reset request
#     And admin generates temporary password
#     And user logs in with username 'neymar@leavepal.com' and generated password
#     And user resets password to "Neymar@123"
#     Then user should login successfully

#   @forgetPassword @negative
#   Scenario: User tries to reset password with unregistered email
#     Given user launches the application
#     When user clicks forgot password
#     And user enters email "invaliduser@leavepal.com"
#     And user sends reset request
#     Then user should see reset request failure message

#   @forgetPassword @negative
#   Scenario: User submits forgot password request without entering email
#     Given user launches the application
#     When user clicks forgot password
#     And user sends reset request
#     Then user should see email required error message
