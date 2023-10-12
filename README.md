# Backend JVM Software Engineer - Technical Challenge (Solution)

## Stack
 - java 17
 - Spring Data jpa
 - Spring Web
 - H2
## Explanation
I created a three service class, the first one to our internal system , 
second one to the external system or API (a fictional implementation) and the
last one is to make the process to convert from customer(lead) to prospect.
So basically we send a customer's id of the customer that we want convert to 
prospect, our LeadConversionServiceImp receive it and start the process. Using 
a completableFuture to make two process asynchronously. The
process is: 
 - valid that the customer exist in our internal database and the external
 - valid that the customer's information be the same that the info got from external system
 - valid that the customer has not judicial records
 - valid the score made from our system be grater than 60

If one of these steps not is valid,the conversion will fail.

### LeadConversionServiceImp
 In this class is like the principal service class because there I make the call
to the others services, two call are made asynchronously, the external system and the internal system to make
the process of the conversion after receiving the result from these systems.
### InternalSystemServiceImp
Here we get the score to know if the customer is valid as prospect
### ExternalSystemServiceImp
Here we can get the data of the customer to know if it exists (in national system)
in the external system also we can get the judicial record of the customer, if it has
of course.

## Endpoints to use the app
* http://localhost:8080/lead/convert/79 -> it will fail because the customer has judicial records
* http://localhost:8080/lead/convert/78 -> it will success
* http://localhost:8080/lead/convert/58 -> it will fail because the data of customer doesn't match between our system and
the external system
* http://localhost:8080/lead/convert/44 -> it will fail because the customer doesn't exist in the external system
* http://localhost:8080/lead/convert/99 -> it will fail because the customer doesn't exist in our system

## Responses
The structure of the response is : "conversion failed: {the reason}" or "conversion successfully"

 



