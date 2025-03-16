import java.util.*;

public class Calculator {
    private static List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the Calculator");
        System.out.println("Please enter functions according to the instructions: \n pow  (x y)  |  abs(x) \n round(x.y)  |  sqrt(x)");
        while (true) {
            System.out.print("Enter your arithmetic equation (or 'history' to view past calculations): ");
            String equation = in.nextLine().trim();

            if (equation.equalsIgnoreCase("history")) {
                displayHistory();
                continue;
            }

            Map<String, Integer> priority = new HashMap<>();
            priority.put("+", 1);
            priority.put("-", 1);
            priority.put("*", 2);
            priority.put("/", 2);
            priority.put("%", 2);
            priority.put("pow", 3);
            priority.put("sqrt", 4);
            priority.put("round", 4);
            priority.put("abs", 4);

            List<String> tokens = tokenize(equation);
            List<String> out = toRPN(tokens, priority);
            double result = evaluateDouble(out);
            System.out.println("Result: " + result);

            // Store the calculation in history
            history.add(equation + " = " + result);

            System.out.print("Do you want to continue? (y/n): ");
            String choice = in.nextLine().trim().toLowerCase();
            if (choice.equals("n")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            }
        }
    }

    public static void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            System.out.println("Calculation History:");
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
    }

    public static double evaluateDouble(List<String> out) {
        Stack<Double> numbers = new Stack<>();
        for (String token : out) {
            try {
                numbers.push(Double.parseDouble(token));
            } catch (NumberFormatException e) {
                switch (token) {
                    case "+":
                        numbers.push(numbers.pop() + numbers.pop());
                        break;
                    case "-":
                        double b = numbers.pop();
                        double a = numbers.pop();
                        numbers.push(a - b);
                        break;
                    case "*":
                        numbers.push(numbers.pop() * numbers.pop());
                        break;
                    case "/":
                        b = numbers.pop();
                        a = numbers.pop();
                        numbers.push(a / b);
                        break;
                    case "%":
                        b = numbers.pop();
                        a = numbers.pop();
                        numbers.push(a % b);
                        break;
                    case "pow":
                        b = numbers.pop();
                        a = numbers.pop();
                        numbers.push(Math.pow(a, b));
                        break;
                    case "sqrt":
                        numbers.push(Math.sqrt(numbers.pop()));
                        break;
                    case "round":
                        numbers.push((double) Math.round(numbers.pop()));
                        break;
                    case "abs":
                        numbers.push(Math.abs(numbers.pop()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
            }
        }
        return numbers.pop();
    }

    public static List<String> toRPN(List<String> tokens, Map<String, Integer> priority) {
        Stack<String> operators = new Stack<>();
        List<String> out = new ArrayList<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                out.add(token);
            } else if (priority.containsKey(token)) {
                while (!operators.isEmpty() && priority.containsKey(operators.peek())
                        && priority.get(operators.peek()) >= priority.get(token)) {
                    out.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    out.add(operators.pop());
                }
                operators.pop();
            } else {
                throw new IllegalArgumentException("Unknown token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            out.add(operators.pop());
        }

        return out;
    }

    public static List<String> tokenize(String equation) {
        List<String> tokens = new ArrayList<>();
        int length = equation.length();
        int i = 0;

        while (i < length) {
            char ch = equation.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if (Character.isDigit(ch) || ch == '-' && (i == 0 || "+-*/%(".contains(String.valueOf(equation.charAt(i - 1))))) {
                StringBuilder number = new StringBuilder();
                number.append(ch);
                i++;
                while (i < length && (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.')) {
                    number.append(equation.charAt(i));
                    i++;
                }
                tokens.add(number.toString());
                continue;
            }

            if ("+-*/%".indexOf(ch) != -1) {
                tokens.add(String.valueOf(ch));
                i++;
                continue;
            }

            if (ch == '(' || ch == ')') {
                tokens.add(String.valueOf(ch));
                i++;
                continue;
            }

            if (Character.isLetter(ch)) {
                StringBuilder function = new StringBuilder();
                while (i < length && Character.isLetter(equation.charAt(i))) {
                    function.append(equation.charAt(i));
                    i++;
                }
                tokens.add(function.toString());
                continue;
            }

            throw new IllegalArgumentException("Invalid character: " + ch);
        }

        return tokens;
    }
}
