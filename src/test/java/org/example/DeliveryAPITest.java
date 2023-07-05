package org.example;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DeliveryAPITest {

        @BeforeAll
public static void setUp (){
     //   System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();

   //     System.out.println("token " + setupFunctions.getToken());

   //     String token = setupFunctions.getToken();

   //     RestAssured.baseURI = setupFunctions.baseUrl;

//        if (token == null){
//                Assertions.fail();
        }

    @Test
    public void createUser (){
            int a = 1;
    }
}