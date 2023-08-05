package org.example;

import com.google.gson.Gson;
import dto.User;
import io.restassured.RestAssured;

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
    private String baseUrlWeb;
    private String dbhost;
    private String dbport;
    private String dbname;
    private String dbusername;
    private String dbpassword;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseUrlWeb() {
        return baseUrlWeb;
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

    public String getDbhost() {
        return dbhost;
    }

    public String getDbport() {
        return dbport;
    }

    public String getDbname() {
        return dbname;
    }

    public String getDbusername() {
        return dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setBaseUrlWeb(String baseUrlWeb) {
        this.baseUrlWeb = baseUrlWeb;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    // Task 13
    public SetupFunctions() {

        try (InputStream input = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            baseUrlWeb = properties.getProperty("baseUrlWeb");
            dbhost = properties.getProperty("dbhost");
            dbport = properties.getProperty("dbport");
            dbname = properties.getProperty("dbname");
            dbusername = properties.getProperty("dbusername");
            dbpassword = properties.getProperty("dbpassword");


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

        String token = RestAssured.given()
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


