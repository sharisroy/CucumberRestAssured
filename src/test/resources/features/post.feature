Feature: POST API Test

  Scenario: Login API Test
    Given I have the login credentials email "sqa.haris@gmail.com" and password "H@12345bd"
    When I send a POST request to "https://rahulshettyacademy.com/api/ecom/auth/login"
    Then I should receive a 200 status code
    And I should get a token in response
