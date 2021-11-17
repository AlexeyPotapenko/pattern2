package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Value;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    public DataGenerator() {
    }

    @Data
    @Value
    public static class Registration {
        String login;
        String password;
        String status;
    }

    private static void sendRequest(Registration user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
    public static String getRandomLogin() {
        String login = faker.name().firstName();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public Registration getActiveUser(){
        Registration activeUser = new Registration(faker.name().firstName(), faker.internet().password(), "active");
        sendRequest(activeUser);
        return activeUser;
    }

    public Registration getBlockedUser(){
        Registration blockedUser = new Registration(faker.name().firstName(), faker.internet().password(), "blocked");
        sendRequest(blockedUser);
        return blockedUser;
    }
}
