package org.example;


import org.junit.jupiter.api.Test;

public class Array {

    @Test
    public void testPositiveArrayIndex1OutOfBoundsException() {

        String[] day = new String[5];

        day[0] = "Monday";
        day[1] = "Tuesday";
        day[2] = "Wednesday";
        day[3] = "Thursday";
        day[4] = "Friday";

        System.out.println("day[1] : " + day[1]);
    }

    @Test
    public void testPositiveArrayIndex4OutOfBoundsException() {

        String[] day = new String[5];

        day[0] = "Monday";
        day[1] = "Tuesday";
        day[2] = "Wednesday";
        day[3] = "Thursday";
        day[4] = "Friday";

        System.out.println("day[4] : " + day[4]);
    }

    @Test
    public void testNegativeArrayIndex6OutOfBoundsException() {

        String[] day = new String[5];

        day[0] = "Monday";
        day[1] = "Tuesday";
        day[2] = "Wednesday";
        day[3] = "Thursday";
        day[4] = "Friday";

        System.out.println("day[6] : " + day[6]);
    }

    @Test
    public void testNegativeArrayIndex7OutOfBoundsException() {

        String[] day = new String[5];

        day[0] = "Monday";
        day[1] = "Tuesday";
        day[2] = "Wednesday";
        day[3] = "Thursday";
        day[4] = "Friday";

        System.out.println("day[6] : " + day[7]);
    }
}