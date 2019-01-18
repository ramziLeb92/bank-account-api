Feature: no account found scenario

  Background:
    * url baseUrl

  Scenario Outline: no account found

    Given path '/operations/<accountNumber>/status'
    When method GET
    Then status 404

    Examples:
      | accountNumber |
      | 124 |