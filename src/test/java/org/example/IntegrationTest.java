package org.example;

import db.DbManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IntegrationTest {

   static Connection connection;

    @Test
    public void connectionTest (){
        DbManager dbManager = new DbManager();
        connection = dbManager.connectionToDb();
        executeSearchAndCompare(4207);
        dbManager.close(connection);

    }

    public void executeSearchAndCompare(int orderId) {

        // step 1
        String sql = String.format("select * from orders where id = %d ;", orderId);

        System.out.println();

        try {
            System.out.println("Executing sql ... ");
            System.out.println("SQL is : " + sql);

            // step 2
            Statement statement = connection.createStatement();
            // step 3
            ResultSet resultSet = statement.executeQuery( sql );

            //int size = 0;
            String statusFromDb = null;
            if (resultSet != null) {

                while ( resultSet.next() ) {
                    System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
                    statusFromDb = resultSet.getString(3);
                }

                Assertions.assertEquals( "OPEN", statusFromDb);

            } else {
                Assertions.fail("Result set is null");
            }

        } catch (SQLException e) {

            System.out.println("Error while executing sql ");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            e.printStackTrace();

            Assertions.fail("SQLException");

        }

    }
}
