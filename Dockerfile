FROM openjdk:17
COPY target/MyHouse24.jar myhouse24-admin.jar
ENTRYPOINT ["java","-jar", "myhouse24-admin.jar"]