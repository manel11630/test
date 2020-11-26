Feature: Launch test
@launch
Scenario: Open google and launch the application
    Given The app is displayed
    Then Accept the cookies
    Then Click on 'Request a demo' button
    Then Fill the 'first name' field with the value 'Manel'
    Then Fill the 'last name' field with the value ''
    Then Fill the 'email' field with the value 'random'
    Then Choose a value in 'Company Size'
    Then Click on 'Submit' button and verify the error messages
    

    