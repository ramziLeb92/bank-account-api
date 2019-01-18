Feature: operations account scenario

  Background:
  * url baseUrl

  Scenario Outline: check account

    Given path '/operations/<accountNumber>/status'
    When method GET
    Then status 200
    And match $ == {"amount": '#notnull'}

    Examples:
      | accountNumber|
      | 123 |

  Scenario Outline: save money

  Given path '/operations/'
  And request {accountNumber:<accountNumber>, operationAmount: <operationAmount>}
  When method POST
  Then status 201
  And match $ contains {"label": "deposit"}

  Examples:
  | accountNumber | operationAmount |
  | 123 | 20 |

  Scenario Outline: withdrawl money

    Given path '/operations/'
    And request {accountNumber:<accountNumber>, operationAmount: <operationAmount>}
    When method POST
    Then status 201
    And match $ contains {"label": "withdrawl"}

    Examples:
      | accountNumber | operationAmount |
      | 123 | -20 |

  Scenario Outline: check account operations
    * def query = { accountNumber: <accountNumber>, startDate: <startDate>, endDate: <endDate>}
    Given path '/operations'
    And params query
    When method GET
    Then status 200
    And assert response.length >= 2

    Examples:
      | accountNumber | startDate | endDate |
      | 123 | 2018-01-01 | 2022-01-01       |