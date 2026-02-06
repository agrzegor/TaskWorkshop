package pl.coderslab;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {

    public Validation() {
    }

    public boolean dateValidation(String input) {

        DateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
        dt.setLenient(false);
        try {
            dt.parse(input);
            return true;
        } catch (ParseException e) {
            System.out.println("Provided date is incorrect. Please try again.");
            return false;
        }
    }

    public boolean descriptionValidation(String input) {

        if (!StringUtils.isEmpty(input) && StringUtils.isNotBlank(input)) {
            return true;
        }
        System.out.println("Description can not be empty. Please provide description for task.");
        return false;
    }

    public boolean importanceOfValidation(String input) {

        input = input.toLowerCase();
        if ((input.equals("true")) || (input.equals("false"))) {
            return true;
        }
        System.out.println("Please provide true/false values.");
        return false;
    }

    public boolean numberToRemoveValidation(String input, String[][] array) {

        if (input == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(input);
            if (number < 0) {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0.");
            } else if (number >= array.length) {
                System.out.println("Incorrect argument passed. Please give number lower or equal %d."
                        .formatted(array.length-1));
            }else{
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect argument passed. Please give number in valid format");
        }

        return false;
    }
}










