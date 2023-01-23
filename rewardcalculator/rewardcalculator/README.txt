Customer Rewards Application
---------------------------------------------------------------------------

SpringBoot Application that demonstrates REST API Development using Java 8 or higher version

Features:
---------------------------------------------------------------------------

This application has three primary REST end-points that calculates reward points for customers based on their transactions.

GET /transactions - eg: http://localhost:8080/transactions
GET /rewards/{customerName} - eg: http://localhost:8080/rewards/John
GET /rewards/{customerName}/{month} - eg: http://localhost:8080/rewards/John/January
GET /rewards - eg: http://localhost:8080/rewards

Transaction Record Input text file:
---------------------------------------------------------------------------

The dataset.txt file located in src/main/resources directory contains all the transaction history. 
Please view the /transactions endpoint for the input transaction record history to use in other endpoints so that you can enter customer name, month etc accordingly for all other endpoints.
The file contains transactions in the format {name},{month},{amount}.

Technologies used:
---------------------------------------------------------------------------

Java (Programming Language)
Spring Boot (Application Platform)
JUnit, with Spring Testing (Unit Testing)

GETTING STARTED:

Prerequisites:
---------------------------------------------------------------------------

Java 8 or higher
Git

Installing & Running:
---------------------------------------------------------------------------

Clone this repo into your local using the command 'git clone https://github.com/chiragreddy/CustomerRewardsCalculatorApplication.git"

Steps to run application:
---------------------------------------------------------------------------

1. Build using maven wrapper - mvnw clean install (this command should be run inside the path "CustomerRewardsCalculatorApplication\rewardcalculator\rewardcalculator" where pom.xml is located)
2. Start the app - mvnw spring-boot:run

Steps to run tests:
---------------------------------------------------------------------------

1. mvn test -Dtest=ClassName
   e.g: mvn test -Dtest=RewardsControllerTest, mvn test -Dtest=RewardcalculatorApplicationTests
   (this command should be run inside the path "CustomerRewardsCalculatorApplication\rewardcalculator\rewardcalculator" where pom.xml is located)
   
