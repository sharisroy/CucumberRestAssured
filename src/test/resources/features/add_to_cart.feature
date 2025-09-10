Feature: Add To Cart API Tests
  @smoke
  Scenario: Add to Cart
    Given I have a payload from "anotherRequest.json"
    When I send a POST request to add-to-cart "user/add-to-cart"
    Then I should receive a 200 status
#    And I should get a success message in the response
