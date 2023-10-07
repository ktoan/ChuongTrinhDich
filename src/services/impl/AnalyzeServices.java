package services.impl;

import enums.TokenType;
import models.Token;
import services.IAnalyzeServices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ToanNK16 on 2023-10-07
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public class AnalyzeServices implements IAnalyzeServices {
    @Override
    public List<Token> analyzeFile(String filePath) throws IOException {
        List<Token> tokens = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            tokens.addAll(analyzeLine(line));
        }

        reader.close();

        return tokens;
    }

    private static List<Token> analyzeLine(String line) {
        List<Token> tokens = new ArrayList<>();

        String keywordRegex = "\\b(if|else|for|while|int|void)\\b";
        String identifierRegex = "[a-zA-Z_][a-zA-Z0-9_]*";
        String numberRegex = "\\b\\d+(\\.\\d+)?\\b";
        String stringRegex = "\"(.*?)\"|'(.*?)'";
        String operatorRegex = "\\+|\\-|\\*|\\/|\\=|\\==|\\>|\\<|\\>=|\\<=|\\!=";
        String punctuatorRegex = "\\,|\\;|\\(|\\)|\\{|\\}|\\[|\\]";

        String regex = String.format("(%s)|(%s)|(%s)|(%s)|(%s)|(%s)", keywordRegex, identifierRegex, numberRegex,
                stringRegex, operatorRegex, punctuatorRegex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String lexeme = matcher.group();
            TokenType tokenType;
            if (lexeme.matches(keywordRegex)) {
                tokenType = TokenType.keyword;
            } else if (lexeme.matches(identifierRegex)) {
                tokenType = TokenType.identifier;
            } else if (lexeme.matches(numberRegex)) {
                tokenType = TokenType.number;
            } else if (lexeme.matches(stringRegex)) {
                tokenType = TokenType.string;
            } else if (lexeme.matches(operatorRegex)) {
                tokenType = TokenType.operator;
            } else if (lexeme.matches(punctuatorRegex)) {
                tokenType = TokenType.punctuator;
            } else {
                tokenType = TokenType.Error;
            }
            Token token = new Token(lexeme, tokenType);
            tokens.add(token);
        }

        return tokens;
    }
}
