Here if you're a coder

* To run as spring boot applicaiton mvn spring-boot:run

What happens on startup
* trys to run using the config in application 
* further queries based on rest api


Standard Maven package layout
Standard Srping boot app

entry points ...


Spring end points ....
https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#actuator.endpoints

http://localhost:7000/swagger-ui.html
http://localhost:7000/swagger-ui/

configuration ...

Logging ...

extending ...   




Build an executable JAR

You can run the application from the command line with Gradle or Maven. You can also build a single executable JAR file that contains all the necessary dependencies, classes, and resources and run that. Building an executable jar makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

If you use Gradle, you can run the application by using ./gradlew bootRun. Alternatively, you can build the JAR file by using ./gradlew build and then run the JAR file, as follows:

java -jar build/libs/gs-serving-web-content-0.1.0.jar

