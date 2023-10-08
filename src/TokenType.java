/**
 * @author ToanNK16 on 2023-10-08
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public enum TokenType {
    KEYWORD("keyword"), IDENTIFIER("identifier"), NUMBER("num"), OTHERS, ERROR("Error");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    TokenType() {}

    public String getValue() {
        return value;
    }
}
