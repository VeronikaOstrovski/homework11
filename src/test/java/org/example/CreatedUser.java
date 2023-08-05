package org.example;

import com.google.gson.Gson;
import dto.RandomUser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.*;

public class CreatedUser {
    @Test
    public void toCreateUserSerialization() {
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
        randomUserEmpty.setUsername("test");
        randomUserEmpty.setFirstName(generatedRandomFirstName());
        randomUserEmpty.setLastName("Ost");
        randomUserEmpty.setEmail("test@gmail.com");
        randomUserEmpty.setPassword("12345678");
        randomUserEmpty.setPhone(generatedRandomFirstName());
        randomUserEmpty.setUserStatus(12);

        Gson gson = new Gson();
        RestAssured.given()
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
        int lengthPhone = 15;
        boolean useLettersPhone = false;
        boolean useNumbersPhone = true;
        String generatedStringPhone = RandomStringUtils.random(lengthPhone, useLettersPhone, useNumbersPhone);

        return generatedStringPhone;
    }

    @Test
    public void toCreateUserDeserialization(){
        RandomUser randomUserEmptyDeserialization = new RandomUser();
        randomUserEmptyDeserialization.setId(8);
        randomUserEmptyDeserialization.setUsername("today");
        randomUserEmptyDeserialization.setFirstName(generatedRandomFirstName());
        randomUserEmptyDeserialization.setLastName("Weekend");
        randomUserEmptyDeserialization.setEmail("goodday@gmail.com");
        randomUserEmptyDeserialization.setPassword("9876543");
        randomUserEmptyDeserialization.setPhone(generatedRandomPhone());
        randomUserEmptyDeserialization.setUserStatus(2017);
//        randomUserEmptyDeserialization.setCode(HttpStatus.SC_OK);
//        randomUserEmptyDeserialization.setType("unknown");
//        randomUserEmptyDeserialization.setMessage("8");


        Gson gsonDeserialization = new Gson();
        Response responseDeserialization = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .body(gsonDeserialization.toJson(randomUserEmptyDeserialization))
                .log()
                .all()
                .post("https://petstore.swagger.io/v2/user")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        RandomUser createdUser = gsonDeserialization.fromJson(responseDeserialization.asString(), RandomUser.class);

        Assertions.assertAll ( "Asserts for CreateUser",
                () -> Assertions.assertEquals(HttpStatus.SC_OK, createdUser.getCode(), "First Assert"),
       //         () -> assertNull( createdUser.getFirstName(), "Second Assert"),
                () -> Assertions.assertEquals("8", createdUser.getMessage(), "Third Assert"),
       //         () -> assertNotEquals(generatedRandomPhone(),createdUser.getPhone(),"FourthAssert"),
                () -> Assertions.assertNotNull(createdUser.getType(),"Fifth Assert")
        );
    }

}

