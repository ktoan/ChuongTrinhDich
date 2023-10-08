import java.io.IOException;
import java.util.List;

/**
 * @author ToanNK16 on 2023-10-07
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public class LexicalAnalyzer {
    private static final ILexicalAnalyzeServices analyzeServices = new LexicalAnalyzeServices();


    public static void main(String[] args) throws IOException {
        String assetFile = "assets\\input.c";
        List<Token> tokens = analyzeServices.analyzeFile(assetFile);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
