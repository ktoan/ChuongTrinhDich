import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSolving {
    private static class Token {
        private String type;
        private String lexeme;

        public Token(String type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }

        @Override
        public String toString() {
            if (this.type.equals("keyword") || this.type.equals("identifier") || this.type.equals("num")) {
                return type + " : " + lexeme;
            } else {
                return lexeme + " : " + lexeme;
            }
        }
    }

    public static void main(String[] args) {
        String filename = "assets\\input.c";
        List<Token> tokens = analyzeFile(filename);
        printTokens(tokens);
    }

    private static List<Token> analyzeFile(String filename) {
        List<Token> tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tokens.addAll(tokenizeLine(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    private static List<Token> tokenizeLine(String line) {
        List<Token> tokens = new ArrayList<>();

        // Regular expressions for tokens
        String keywordRegex = "\\b(void|int|for)\\b";
        String identifierRegex = "[a-zA-Z_][a-zA-Z0-9_]*";
        String numberRegex = "\\d+(\\.\\d+)?([Ee][+-]?\\d+)?";
        String operatorRegex = "[+\\-*/=<>]+";
        String punctuationRegex = "[{}();]";

        String regex = String.format("(%s)|(%s)|(%s)|(%s)|(%s)", keywordRegex, identifierRegex, numberRegex,
                operatorRegex, punctuationRegex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String matchedToken = matcher.group();
            if (matchedToken.matches(keywordRegex)) {
                tokens.add(new Token("keyword", matchedToken));
            } else if (matchedToken.matches(identifierRegex)) {
                tokens.add(new Token("identifier", matchedToken));
            } else if (matchedToken.matches(numberRegex)) {
                tokens.add(new Token("num", matchedToken));
            } else if (matchedToken.matches(operatorRegex)) {
                tokens.add(new Token("operator", matchedToken));
            } else if (matchedToken.matches(punctuationRegex)) {
                tokens.add(new Token("punctuation", matchedToken));
            }
        }

        return tokens;
    }

    private static void printTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}