
package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DataInput {

    private static Scanner sc = new Scanner(System.in);

    public static String getString() {
        return sc.nextLine().trim();
    }

    public static String getString(String msg) {
        System.out.print(msg);
        return getString();
    }

    public static String getStringNonEmpty(String msg) throws Exception {
        String s = getString(msg);
        if (!DataValidation.checkStringEmpty(s))
            throw new Exception("Input cannot be empty!");
        return s;
    }

    public static String getStringAllowEmpty(String msg) {
        System.out.print(msg);
        return getString();
    }

    public static int getIntegerNumber() throws Exception {
        String s = getString();
        if (!DataValidation.checkStringWithFormat(s, "\\d{1,10}"))
            throw new Exception("Invalid integer. Digits only.");
        return Integer.parseInt(s);
    }

    public static int getIntegerNumber(String msg) throws Exception {
        System.out.print(msg);
        return getIntegerNumber();
    }

    public static int getIntegerNumber(String msg, int min, int max) throws Exception {
        int n = getIntegerNumber(msg);
        if (!DataValidation.checkNumberInMinMax(n, min, max))
            throw new Exception("Value must be between " + min + " and " + max);
        return n;
    }

// chuyển đổi base salary
    public static double getDoubleNumber() throws Exception {
        String s = getString();
        if (s.isEmpty()) return -1; // dùng để skip
       if (!s.matches("-?\\d+(\\.\\d+)?")) 
            throw new Exception("Invalid number.");
        return Double.parseDouble(s);
    }

    public static double getDoubleNumber(String msg) throws Exception {
        System.out.print(msg);
        return getDoubleNumber();
    }

    public static double getDoubleNumber(String msg, double min) throws Exception {
        double val = getDoubleNumber(msg);
        if (val != -1 && val < min)
            throw new Exception("Value must be >= " + min);
        return val;
    }

    public static LocalDate getDate(String msg) throws Exception {
        String s = getString(msg);
        if (s.isEmpty()) return null;
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new Exception("Invalid date (dd/MM/yyyy)");
        }
    }

    public static LocalDate getDateNonEmpty(String msg) throws Exception {
        LocalDate d = getDate(msg);
        if (d == null) throw new Exception("Date cannot be empty");
        return d;
    }

    public static boolean getYesNo(String msg) {
        System.out.print(msg + " (y/n): ");
        String ans = getString();
        return ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes");
    }
}