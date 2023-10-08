/**
 * @author ToanNK16 on 2023-10-08
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public class Token {
    private String lexeme;
    private TokenType type;

    public Token() {
    }

    public Token(String lexeme, TokenType type) {
        this.lexeme = lexeme;
        this.type = type;
    }

    @Override
    public String toString() {
        if (this.type == TokenType.KEYWORD || this.type == TokenType.IDENTIFIER
                || this.type == TokenType.NUMBER || this.type == TokenType.ERROR) {
            return type.getValue() + " : " + lexeme;
        } else {
            return lexeme + " : " + lexeme;
        }
    }
}
