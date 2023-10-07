package models;

import enums.TokenType;

/**
 * @author ToanNK16 on 2023-10-07
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public class Token {
    private String lexeme;
    private TokenType tokenType;


    public Token() {
    }

    public Token(String lexeme, TokenType tokenType) {
        this.lexeme = lexeme;
        this.tokenType = tokenType;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return tokenType + ": " + lexeme;
    }
}
