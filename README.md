
This project is designed with spring boot and h2.
For integration test I used the framework Karate.

## Getting Started

To run this project, please follow those instructions

### Prerequises

Clone this repository:

```text
    git clone gitadresse
```

Then open the project with your IDE, Right click on the main class named BankAccountApplication, and then press "run BankAccountApplication".

You can also execute the project with maven:
```text
    mvn package && java -jar target/bank_account-0.0.1-SNAPSHOT.jar
```

To execute the unit and integration tests :
```text
    mvn test
```

## API

With this API, you can deposit and withdrawal operations on your bank account.

When the API is running, an embedded Apache Tomcat Server will be running at :  

```text
    http://localhost:8080/
```  
to access the embedded data base, please check :

```text
    http://localhost:8080/h2-console
```  

you will see that the client table is already filled with the data below :
|       |        |   | |

| client_id    |     email |   firstname | lastname
| ------------- | -------------: | ---------: | ---------: |
| 1       |        'toto@gmail.com'|     'toto'   |      'titi' |
| 2       |        'tata@gmail.com' |    'tata'   |      'roro' |
| 3      |        'julien@gmail.com' |    'julien'   |      'dupond' |
| 4      |        'juliette@gmail.com' |    'juliette'   |      'herve' |

and the account table is already filled with the data :
|       |        |   | |

| account_id    |     account_number |   amount | client_id
| ------------- | -------------: | ---------: | ---------: |
| 1       |        123|     100   |      1 |
| 2       |        456 |    250   |      2 |
| 3      |        678 |    500   |      3 |
| 4      |        899 |    900   |      4 |

if you want to make a deposit into the account number 123 with CURL :
```text
curl -X POST -H "Content-Type: application/json" -d "{\"accountNumber\":123, \"operationAmount\":10}" localhost:8080/operations/
```  
if you want to withdrawal from the account number 123 with CURL :
```text
curl -X POST -H "Content-Type: application/json" -d "{\"accountNumber\":123, \"operationAmount\":-10}" localhost:8080/operations/
```
if you want to see the history of operation of the account number 123 with CURL :
```text
curl -X GET -H "Content-Type: application/json" "localhost:8080/operations?accountNumber=123&startDate=2019-01-01&endDate=2019-02-02"
```
if you want to check the account amount of the account number 123 with CURL :
```text
curl -X GET -H "Content-Type: application/json" "localhost:8080/operations/123/status"
```
if you want to test what happens if you ask for a non-existent account with CURL :
```text
curl -X GET -H "Content-Type: application/json" "localhost:8080/operations/333/status"
```
if you want to test what happens if you ask to withdrawal more then the amount of your account with CURL :
```text
curl -X POST -H "Content-Type: application/json" -d "{\"accountNumber\":899, \"operationAmount\":-10000}" localhost:8080/operations/
```