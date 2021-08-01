Feature:  Green Kart

  Scenario: Vegges
    Given Invoking the browser with chrome and navigate to "https://rahulshettyacademy.com/seleniumPractise/#/"
    When Enter the product needed and add to cart
    And Comparing number of selected product is displayed
    Then Click on checkout
    When Comparing and calculating the total amount
    And Add promo code and verify it
    And Compare the total amount before and after the promo code applied
    Then Click on Place order
    When User should enter country "Netherlands"
    Then Click to proceed
    And Verify the conformation of order


