Feature: POST API Tests

  @smoke
  Scenario: Login API Test with Valid Credentials
    Given I have a JSON payload from "loginPayload.json"
    When I send a POST request to endpoint "auth/login"
    Then I should receive a 200 status code
    And I should get a token in the response

  @login
  Scenario Outline: Login API Negative Tests with Invalid Credentials
    Given I have a JSON payload from "loginPayload.json"
    And I update the email to "<email>" and password to "<password>"
    When I send a POST request to endpoint "auth/login"
    Then I should receive a 400 status code
    And I should see an error message in the response

    Examples:
      | email                 | password         |
      | wrong.email@gmail.com | wrongPassword123 |
      | sq.haris@gmail.com   | badPassword      |
      | bad.user@gmail.com    | H@12345bd        |
