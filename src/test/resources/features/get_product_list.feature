Feature: Get Product List API

  Scenario: Retrieve all products successfully
    When I send a POST request to "product/get-all-products"
    Then the product API should return a 200 status code
    And the response should contain a product list
