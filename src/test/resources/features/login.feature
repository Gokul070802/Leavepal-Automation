# Feature: Login module of Leavepal application

#   Scenario: Login with valid credentials
#     Given The user is on the login page and select the manageroremployee login
#     When Enter the username as 'gokul@leavepal.com'
#     And Enter the password as 'Gokul@123'
#     And Click on the Login button
#     Then The user should be loggedin

#   Scenario: Login with invalid Password
#     Given The user is on the login page and select the manageroremployee login
#     When Enter the username as 'gokul@leavepal.com'
#     And Enter the password as 'wrongpassword'
#     And Click on the Login button
#     Then The user should not be loggedin

#   Scenario: Login with invalid username
#     Given The user is on the login page and select the manageroremployee login
#     When Enter the username as 'goku@leavepal.com'
#     And Enter the password as '123123123'
#     And Click on the Login button
#     Then The user should not be loggedin

#   Scenario: admin can login successfully using valid credentials
#     Given The user is on the login page and select the HR login
#     When Enter the username as 'admin'
#     And Enter the password as 'admin123'
#     And Click on the Login button
#     Then The user should be loggedin

#   Scenario: admin cannot login with invalid credentials
#     Given The user is on the login page and select the HR login
#     When Enter the username as 'admin'
#     And Enter the password as 'wrongpassword'
#     And Click on the Login button
#     Then The user should not be loggedin

#   Scenario: admin cannot log in using employee credentials
#     Given The user is on the login page and select the HR login
#     When Enter the username as 'gokul@leavepal.com'
#     And Enter the password as '123123123'
#     And Click on the Login button
#     Then The user should not be loggedin

#   Scenario: password stays masked while the user is typing it
#     Given The user is on the login page and select the manageroremployee login
#     When Enter the password as '123123123'
#     Then The password should be masked and not visible in plain text

#   Scenario: password can be viewed and hidden using eye icon
#     Given The user is on the login page and select the manageroremployee login
#     When Enter the password as '123123123'
#     And Click on the eye icon to view the password
#     Then The password should be visible in plain text
#     When Click on the eye icon again to hide the password
#     Then The password should be masked and not visible in plain text

#   Scenario: forgot password option is available for employee
#     Given The user is on the login page and select the manageroremployee login
#     Then The forgot password option should be visible on the login page

#   Scenario: employee can submit forgot password request using official email
#     Given The user is on the login page and select the manageroremployee login
#     When Click on the forgot password option
#     And Enter the username as 'gokul@leavepal.com'
#     And Click on the send request button
#     Then A password reset link should be sent to the registered email address

#   Scenario: employee cannot submit forgot password request using non-official email
#     Given The user is on the login page and select the manageroremployee login
#     When Click on the forgot password option
#     And Enter the username as 'nonofficial@leavepal.com'
#     And Click on the send request button
#     Then An error message should be displayed indicating that the email is not registered