Feature: insufficientFund scenario

  Background:
    * url baseUrl

  Scenario Outline: insufficientFund

    Given path '/operations/'
    And request {accountNumber:<accountNumber>, operationAmount: <operationAmount>}
    When method POST
    Then status 400

    Examples:
      | accountNumber | operationAmount |
      | 899 | -10000 |