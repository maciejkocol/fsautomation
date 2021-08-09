Feature: FS


  @fs @fs_01
  Scenario: FS data capture
    Given A user navigates to page "https://fruitshoppe.firebaseapp.com"
    When Adds featured "Bluebs" to cart
    And Adds "Oranges de Florida" to cart
    And Adds "Cyber Melon" to cart
    And Adds "Raspberries" to cart
    And Adds "Mangocados" to cart
    And Adds "Mangocados" to cart
    And Goes to cart
    And Goes to checkout
    And Fills "First Name" as "John"
    And Fills "Last Name" as "Smith"
    And Fills "1st" instance of "Address 1" as "100 Main St"
    And Fills "1st" instance of "City" as "Montgomery"
    And Fills "1st" instance of "Zip Code" as "36104"
    And Makes shipping same as billing
    And Fills "Credit Card Number" as "4488223344556677"
    And Fills "Security Code" as "345"
    And Agrees to purchase
    And Makes purchase
    Then Confirm order received
    And Validate FS captured data


