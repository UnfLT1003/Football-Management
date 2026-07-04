
package Utilities;

import java.util.Arrays;
import java.util.List;

public class Menu {

    public static void printMenu(String str) {
        List<String> items = Arrays.asList(str.split("\\|"));
        for (String item : items) {
            if (item.equalsIgnoreCase("Select:"))
                System.out.print(item);
            else if (item.equalsIgnoreCase("Select"))
                System.out.print(item + ": ");
            else
                System.out.println(item);
        }
    }

    public static int getUserChoice() {
        try {
            return DataInput.getIntegerNumber();
        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
            return -1;
        }
    }

    public static boolean confirmAction(String action) {
        System.out.print(">> Are you sure to " + action + "? (y/n): ");
        String ans = DataInput.getString();
        return ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes");
    }
}