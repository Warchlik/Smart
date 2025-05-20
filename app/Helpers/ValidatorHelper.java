package app.Helpers;

import java.util.List;
import java.util.Scanner;

public class ValidatorHelper {
    private ValidatorHelper(){}

    public static String checkInputValueString(String string, Scanner scanner) {
        while (true) {
            String line = PrintHelper.readLine(string, scanner);
            if (line.trim().isEmpty()){
                System.out.println("\nInvalid Value, please try again.");
            }else{
                return line;
            }
        }
    }

//    public static String checkIndexString(String string, Scanner scanner , List<?> list) {
//        while (true) {
//            String line = PrintHelper.readLine(string, scanner);
//            if (line.trim().isEmpty() || (Integer.parseInt(line) > list.size())){
//                System.out.println("\nInvalid Value, please try again.");
//            }else{
//                return line;
//            }
//        }
//    }

    public static int checkIndexInt(String string , Scanner scanner , List<?> list){
        while(true){
            String line = PrintHelper.readLine(string, scanner);
            if (isValidate(line, list)) {
                System.out.println("\nInvalid Value, please try again.");
                continue;
            }

            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Value, please try again.");
            }
        }
    }

    public static int checkInputValueInt(String string, Scanner scanner) {
        while (true) {
            String line = PrintHelper.readLine(string, scanner);
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Value, please try again.");
            }
        }
    }

    public static float checkInputValueFloat(String string, Scanner scanner) {
        while (true) {
            String line = PrintHelper.readLine(string, scanner);
            try {
                return Float.parseFloat(line);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Value, please try again.");
            }
        }
    }

    public static <E> E checkInputValueEnum(String string, Scanner scanner , E[] enumList){
        while (true){
                printEnum(enumList);
                int newValue = checkInputValueInt(string, scanner);
            if (newValue > enumList.length - 1){
                System.out.println("\nInvalid Value, please try again.");
            }else{
                return enumList[newValue - 1];
            }
        }
    }


    private static <E> void printEnum(E[] enumList){
        for (int i = 0; i < enumList.length; i++) {
            System.out.printf("%d) %s%n", i + 1, enumList[i]);
        }
    }

    protected static boolean isValidate(String line , List<?> list){
        if (line.trim().isEmpty()){
            return true;
        }
        if (!line.matches("\\d+")){
            return true;
        }

        int val = Integer.parseInt(line);
        if (val < 0 || val >= list.size()) {
            return true;
        }
        return false;
    }
}
