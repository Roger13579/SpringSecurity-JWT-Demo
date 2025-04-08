FROM openjdk:21-jdk
EXPOSE 8080
ADD target/SpringBootMall.jar SpringBootMall.jar
ENTRYPOINT ["java","-jar","SpringBootMall.jar"]