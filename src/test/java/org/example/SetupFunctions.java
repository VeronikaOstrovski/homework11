package org.example;

import com.google.gson.Gson;
import dto.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class SetupFunctions {

    String baseUrl;
    String username;
    String password;

    public SetupFunctions () {
        try (InputStream input = new FileInputStream("settings.properties")){
            Properties properties = new Properties();
            properties.load(input);
            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("user-v-07");
            password = properties.getProperty("pwd876543");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createUser(){
        User user = new User (username, password);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public String getBaseUrl (){
        return baseUrl;
    }

    public String getToken (){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return given()
                .header("Content-Type", "application/json")
                .log()
                .all()
                .body(createUser())
                .when()
                .post(baseUrl + "/login/student")
                .then()
                .log()
                .all()
                .and()
                .extract ()
                .response()
                .asString();

//        return token;

    }
}


