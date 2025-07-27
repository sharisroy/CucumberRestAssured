Feature: Get Product List API

  Scenario: Retrieve all orders successfully
    When I send a GET request to get product "order/get-orders-for-customer/"
    Then the order API should return a 200 status code
    And the response should contain a order list
