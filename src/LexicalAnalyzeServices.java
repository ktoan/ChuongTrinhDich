import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ToanNK16 on 2023-10-08
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public class LexicalAnalyzeServices implements ILexicalAnalyzeServices {
    private static final List<String> KEYWORDS = Arrays.asList("while", "if", "else", "return", "break", "continue", "int", "float", "void");
    private static final List<String> ACCEPT_DIGITS = Arrays.asList("+", "-", "*", "/", "<", ">", "=", "<=", ">=", "==", "!=", "!", "&&", "(", ")", "{", "}", "[", "]", ";", ":");

    @Override
    public List<Token> analyzeFile(String filePath) throws IOException {
        List<Token> tokens = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            tokens.addAll(analyzeLine(line));
        }
        return tokens;
    }

    private static List<Token> analyzeLine(String line) {
        List<Token> tokens = new ArrayList<>();
        int currentIndex = 0;
        while (currentIndex < line.length()) {
            char currentChar = line.charAt(currentIndex);
            if (Character.isWhitespace(currentChar)) {
                currentIndex++;
                continue;
            }
            if (Character.isLetter(currentChar)) {
                String letter = parseIdentifier(line, currentIndex);
                if (KEYWORDS.contains(letter)) {
                    tokens.add(new Token(letter, TokenType.KEYWORD));
                } else {
                    tokens.add(new Token(letter, TokenType.IDENTIFIER));
                }
                currentIndex += letter.length();
            } else if (Character.isDigit(currentChar)) {
                String number = parseNumber(line, currentIndex);
                tokens.add(new Token(number, TokenType.NUMBER));
                currentIndex += number.length();
            } else if (isAcceptedDigit(currentChar)) {
                String operator = parseOperator(line, currentIndex);
                tokens.add(new Token(operator, TokenType.OTHERS));
                currentIndex += operator.length();
            } else {
                tokens.add(new Token(String.valueOf(currentChar), TokenType.ERROR));
                currentIndex++;
            }
        }
        return tokens;
    }

    private static String parseIdentifier(String line, int startIndex) {
        StringBuilder identifier = new StringBuilder();
        int currentIndex = startIndex;
        while (currentIndex < line.length()) {
            char currentChar = line.charAt(currentIndex);
            if (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
                identifier.append(currentChar);
                currentIndex++;
            } else {
                break;
            }
        }
        return identifier.toString();
    }

    private static String parseNumber(String line, int startIndex) {
        StringBuilder number = new StringBuilder();
        int currentIndex = startIndex;
        boolean hasDot = false;
        boolean hasE = false;
        while (currentIndex < line.length()) {
            char currentChar = line.charAt(currentIndex);
            if (Character.isDigit(currentChar)) {
                number.append(currentChar);
                currentIndex++;
            } else if (currentChar == '.') {
                if (hasDot || hasE) {
                    break;
                }
                number.append(currentChar);
                currentIndex++;
                hasDot = true;
            } else if (currentChar == 'E') {
                if (hasE) {
                    break;
                }
                number.append(currentChar);
                currentIndex++;
                hasE = true;
            } else if (currentChar == '+' || currentChar == '-') {
                if (currentIndex > startIndex && line.charAt(currentIndex - 1) == 'E') {
                    number.append(currentChar);
                    currentIndex++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return number.toString();
    }

    private static String parseOperator(String line, int startIndex) {
        StringBuilder operator = new StringBuilder();
        int currentIndex = startIndex;
        while (currentIndex < line.length()) {
            char currentChar = line.charAt(currentIndex);
            String currentOperator = operator.toString() + currentChar;
            if (ACCEPT_DIGITS.contains(currentOperator)) {
                operator.append(currentChar);
                currentIndex++;
            } else {
                break;
            }
        }
        return operator.toString();
    }

    private static boolean isAcceptedDigit(char c) {
        return c == ';' || c == ':' || c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '=' || c == '!' || c == '&' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }
}
