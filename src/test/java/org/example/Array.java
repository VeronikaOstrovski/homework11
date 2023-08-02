package org.example;


import org.junit.jupiter.api.Test;

public class Array {

    //Positive test: check index 1 at the array
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

    //Positive test: check index 4 at the array
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

    //Negative test: check index 6 at the array
    @Test
    public void testNegativeArrayIndex6OutOfBoundsException() {

        try {
            String[] day = new String[5];

            day[0] = "Monday";
            day[1] = "Tuesday";
            day[2] = "Wednesday";
            day[3] = "Thursday";
            day[4] = "Friday";

            System.out.println("day[6] : " + day[6]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    //Negative test: check index 7 at the array
    @Test
    public void testNegativeArrayIndex7OutOfBoundsException() {

        try {
            String[] day = new String[5];

            day[0] = "Monday";
            day[1] = "Tuesday";
            day[2] = "Wednesday";
            day[3] = "Thursday";
            day[4] = "Friday";

            System.out.println("day[6] : " + day[7]);
        }
        catch
        (ArrayIndexOutOfBoundsException e) {
    }
}
}