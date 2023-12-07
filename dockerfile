FROM openjdk:11
EXPOSE 8080
ADD target/SpringBootMall.jar SpringBootMall.jar
ENTRYPOINT ["java","-jar","SpringBootMall.jar"]