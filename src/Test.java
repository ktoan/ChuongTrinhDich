/**
 * @author ToanNK16 on 2023-10-08
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Test {
    public static void main(String[] args) {
        String filename = "assets\\input.c"; // Replace with your input file path
        List<Token> tokens = analyzeFile(filename);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    public static List<Token> analyzeFile(String filename) {
        List<Token> tokens = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tokens.addAll(analyzeLine(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

    public static List<Token> analyzeLine(String line) {
        List<Token> tokens = new ArrayList<>();
        int currentIndex = 0;
        while (currentIndex < line.length()) {
            char currentChar = line.charAt(currentIndex);
            if (Character.isWhitespace(currentChar)) {
                currentIndex++;
                continue;
            }
            if (Character.isLetter(currentChar)) {
                String identifier = parseIdentifier(line, currentIndex);
                tokens.add(new Token(identifier, TokenType.IDENTIFIER));
                currentIndex += identifier.length();
            } else if (Character.isDigit(currentChar)) {
                String number = parseNumber(line, currentIndex);
                tokens.add(new Token(number, TokenType.NUMBER));
                currentIndex += number.length();
            } else {
                tokens.add(new Token(String.valueOf(currentChar), TokenType.OTHERS));
                currentIndex++;
            }
        }
        return tokens;
    }

    public static String parseIdentifier(String line, int startIndex) {
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

    public static String parseNumber(String line, int startIndex) {
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
                    break; // Invalid number format
                }
                number.append(currentChar);
                currentIndex++;
                hasDot = true;
            } else if (currentChar == 'E') {
                if (hasE) {
                    break; // Invalid number format
                }
                number.append(currentChar);
                currentIndex++;
                hasE = true;
            } else if (currentChar == '+' || currentChar == '-') {
                if (currentIndex > startIndex && line.charAt(currentIndex - 1) == 'E') {
                    number.append(currentChar);
                    currentIndex++;
                } else {
                    break; // Invalid number format
                }
            } else {
                break;
            }
        }
        return number.toString();
    }
}
