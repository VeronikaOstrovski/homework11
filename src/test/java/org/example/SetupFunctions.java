package org.example;

import com.google.gson.Gson;
import dto.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class SetupFunctions {

    String username;
    String password;
    String baseUrl;

    public SetupFunctions() {

        try (InputStream input = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public String createUser(){
//        User user = new User (username, password);
//        Gson gson = new Gson();
//        return gson.toJson(user);
//    }
//
//    public String getBaseUrl (){
//        return baseUrl;
//    }

    public String getToken() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Gson gson = new Gson();
        String credentials = gson.toJson(user);

        String token = given()
                .header("Content-Type", "application/json")
                .log()
                .all()
                .body(credentials)
                .when()
                .post(baseUrl + "/login/student")
                .then()
                .log()
                .all()
                .and()
                .extract()
                .asString();

        return token;

    }
}


