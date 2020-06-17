# simpleapi<pre>
Simple REST API

Build application: 
Application with in-memory DB: Comment mysql and enable h2 library in pom.xml
Application with MySQL DB: Comment h2 and enable mysql library in pom.xml. Also update the application-prod.properties with DB config.
mvn clean install -DskipTests

Running application with in-memory DB: java -jar -Dspring.profiles.active=h2 simpleapi.jar
Running application with MySQL DB: java -jar -Dspring.profiles.active=prod simpleapi.jar

Test home page: http://localhost:8080

Get all person:
URL: http://localhost:8080/api/simpleapi/v1/person
Method: GET

Get person by ID
URL: http://localhost:8080/api/simpleapi/v1/person/1
Method: GET

Delete person by ID
URL: http://localhost:8080/api/simpleapi/v1/person/1
Method: DELETE

Create a person:
URL: http://localhost:8080/api/simpleapi/v1/person
Method: POST
Sample Request:
{
	"firstName":"Alex",
	"lastName": "Kumar",
	"address":"215 watford",
	"city" : "Chennai",
	"country": "UK",
	"zipCode": "600019",
	"phone":"1234567890",
	"email":"dummyemail@gmail.com"
}


Update Person by ID:
URL: http://localhost:8080/api/simpleapi/v1/person/1
Method: PUT
Sample Request:
{
	"firstName":"Alexander",
	"lastName": "Kumaran",
	"address":"215 watford",
	"city" : "Chennai",
	"country": "United Kingdom",
	"zipCode": "560059",
	"phone":"9876543210",
	"email":"dummyemail1@gmail.com"
}

Partial update:
URL: http://localhost:8080/api/simpleapi/v1/person/1
Method: PATCH
Sample Request:
{
	"address":"215 london",
	"city" : "Bangalore",
	"country": "India",
	"zipCode": "560029"
}

</pre>
