import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {

        input = input.replaceAll("\\s+", "");


        if (!input.startsWith("\"")) {
            throw new Exception("Первый аргумент должен быть строкой.");
        }

        int operatorIndex = -1;
        char[] operators = {'+', '-', '*', '/'};
        for (char op : operators) {
            int quoteIndex = input.indexOf('"', 1);
            if (quoteIndex != -1) {
                operatorIndex = input.indexOf(op, quoteIndex);
                if (operatorIndex != -1) {
                    break;
                }
            }
        }

        if (operatorIndex == -1) {
            throw new Exception("Неподдерживаемая операция.");
        }

        String leftPart = input.substring(0, operatorIndex);
        String rightPart = input.substring(operatorIndex + 1);


        String leftString = extractString(leftPart);
        String rightStringOrNumber = rightPart;

        String result;
        switch (input.charAt(operatorIndex)) {
            case '+':
                result = leftString + extractString(rightStringOrNumber);
                break;
            case '-':
                String rightString = extractStringForSubtraction(rightStringOrNumber);
                if (leftString.contains(rightString)) {
                    result = leftString.replaceFirst(rightString, "");
                } else {
                    result = leftString;
                }
                break;
            case '*':
                int multiplier = extractNumber(rightStringOrNumber);
                result = leftString.repeat(multiplier);
                break;
            case '/':
                int divisor = extractNumber(rightStringOrNumber);
                if (divisor == 0) {
                    throw new Exception("Деление на ноль невозможно.");
                }
                result = leftString.substring(0, leftString.length() / divisor);
                break;
            default:
                throw new Exception("Неподдерживаемая операция.");
        }

        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }

        return "\"" + result + "\"";
    }

    private static String extractString(String input) throws Exception {
        if (!input.startsWith("\"") || !input.endsWith("\"")) {
            throw new Exception("Неверный формат строки.");
        }
        String str = input.substring(1, input.length() - 1);
        if (str.length() > 10) {
            throw new Exception("Длина строки превышает 10 символов.");
        }
        return str;
    }

    private static String extractStringForSubtraction(String input) throws Exception {
        if (!input.startsWith("\"") || !input.endsWith("\"")) {
            throw new Exception("Неверный формат строки.");
        }
        String str = input.substring(1, input.length() - 1);
        if (str.length() > 10) {
            throw new Exception("Длина строки превышает 10 символов.");
        }
        return str;
    }

    private static int extractNumber(String input) throws Exception {
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > 10) {
                throw new Exception("Число должно быть в диапазоне от 1 до 10.");
            }
            return number;
        } catch (NumberFormatException e) {
            throw new Exception("Неверный формат числа.");
        }
    }
}