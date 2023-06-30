package org.example;

import com.google.gson.Gson;
import dto.RandomUser;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class CreatedUser {
    @Test
    public void toCreateUser() {
        RandomUser randomUser = new RandomUser(25, "test", "Veronika", "Ost", "test@gmail.com", "1234567", "+37212345678", 12);
        RandomUser randomUserEmpty = new RandomUser();
        randomUser.setId(25);
        randomUser.setUsername("test");
        randomUser.setFirstName("Veronika");
        randomUser.setLastName("Ost");
        randomUser.setEmail("test@gmail.com");
        randomUser.setPassword("12345678");
        randomUser.setPhone("+37212345678");
        randomUser.setUserStatus(12);
        randomUserEmpty.setId(25);
        randomUserEmpty.setUsername("test1");
        randomUserEmpty.setFirstName(generatedRandomFirstName());
        randomUserEmpty.setLastName("Ostrov");
        randomUserEmpty.setEmail("post@gmail.com");
        randomUserEmpty.setPassword("7654321");
        randomUserEmpty.setPhone(generatedRandomFirstName());
        randomUserEmpty.setUserStatus(12);

        Gson gson = new Gson();
        given()
                .when()
                .header("Content-Type", "application/json")
                .body(gson.toJson(randomUserEmpty))
                .log()
                .all()
                .post("https://petstore.swagger.io/v2/user")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Assertions.assertEquals( 25, randomUser.getId());

    }

    public String generatedRandomFirstName() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthFirstName = 10;
        boolean useLettersFirstName = false;
        boolean useSymbolsFirstName = true;
        String generatedStringFirstName = RandomStringUtils.random(lengthFirstName, useLettersFirstName, useSymbolsFirstName);

             return generatedStringFirstName;
    }

    public String generatedRandomPhone() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthFirstName = 15;
        boolean useLettersPhone = false;
        boolean useNumbersPhone = true;
        String generatedStringPhone = RandomStringUtils.random(lengthFirstName, useLettersPhone, useNumbersPhone);

        return generatedStringPhone;
    }
}

