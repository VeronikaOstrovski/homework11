package org.example;

import com.google.gson.Gson;
import dto.User;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

// Task 13
public class SetupFunctions {
    // Task 14
    private String username;
    private String password;
    private String baseUrl;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Task 13
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





    // Task 13
    public String getBaseUrl (){
        return baseUrl;
    }

    // Task 13
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


