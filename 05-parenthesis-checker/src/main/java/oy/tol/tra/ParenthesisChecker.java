package oy.tol.tra;

public class ParenthesisChecker {

    private ParenthesisChecker() {}

    public static int checkParentheses(StackInterface<Character> stack, String fromString) throws ParenthesesException {
        int count = 0;
        for (int i = 0; i < fromString.length(); i++) {
            char ch = fromString.charAt(i);
            if (isOpenParenthesis(ch)) {
                try {
                    stack.push(ch);
                    count++;
                } catch (Exception e) {
                    throw new ParenthesesException("Failed to push", ParenthesesException.STACK_FAILURE);
                }
            } else if (isCloseParenthesis(ch)) {
                if (stack.isEmpty()) {
                    throw new ParenthesesException("There are too many closing parentheses", ParenthesesException.TOO_MANY_CLOSING_PARENTHESES);
                } else if (!isMatchingParenthesis(stack.peek(), ch)) {
                    throw new ParenthesesException("Wrong kind of parenthesis were in the text", ParenthesesException.PARENTHESES_IN_WRONG_ORDER);
                } else {
                    stack.pop();
                    count++;
                }
            }
        }
        if (!stack.isEmpty()) {
            throw new ParenthesesException("The string has more opening than closing parentheses.", ParenthesesException.TOO_FEW_CLOSING_PARENTHESES);
        }
        return count;
    }

    private static boolean isOpenParenthesis(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    private static boolean isCloseParenthesis(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    private static boolean isMatchingParenthesis(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '[' && close == ']') ||
                (open == '{' && close == '}');
    }

    private static char findMatch(char parentheses) {
        switch (parentheses) {
            case '(':
                return ')';
            case '[':
                return ']';
            default:
                return '}';
        }
    }
}
