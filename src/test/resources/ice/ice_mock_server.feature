Feature: Ice

  Scenario: mock ice response
    Given ice mock server with response "ice response"
    When ice client send request
    Then ice client get server response "ice response"

  Scenario: response as structure
    Given 存在"TimeOfDay":
      | hour | minute | second |
      | 10   | 57     | 15     |
    Given ice mock server with for object "Clock"
    When ice client send get time request
    Then ice client get server response structure
    """
    : {
      hour: 10
      minute: 57
      second: 15
    }
    """
