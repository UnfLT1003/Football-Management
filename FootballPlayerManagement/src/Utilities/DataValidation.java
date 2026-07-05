
package Utilities;
/**
 *
 * @author Admin
 */

   
public final class DataValidation {

    public static boolean checkNumberInMinMax(int number, int min, int max) {
        return number >= min && number <= max;
    }

    public static boolean checkNumberInMinMax(double number, double min, double max) {
        return number >= min && number <= max;
    }

    public static boolean checkStringEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean checkStringLengthInRange(String value, int min, int max) {
        if (!checkStringEmpty(value)) return false;
        int len = value.trim().length();
        return len >= min && len <= max;
    }

    public static boolean checkStringWithFormat(String value, String pattern) {
        if (!checkStringEmpty(value)) return false;
        return value.trim().matches(pattern);
    }

    // Các helper riêng cho hệ thống bóng đá
    public static boolean isValidJerseyNumber(int number) {
        return number >= 1 && number <= 99;
    }

    public static boolean isValidPosition(String position) {
        String[] valid = {"Goalkeeper", "Defender", "Midfielder", "Forward"};
        for (String v : valid) {
            if (v.equalsIgnoreCase(position.trim())) return true;
        }
        return false;
    }

    public static boolean isValidPlayerId(String id) {
        return checkStringWithFormat(id, "P\\d{3}");
    }

    public static boolean isValidTeamId(String id) {
        return checkStringWithFormat(id, "T\\d{3}");
    }
}