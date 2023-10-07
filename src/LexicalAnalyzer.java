import models.Token;
import services.IAnalyzeServices;
import services.impl.AnalyzeServices;

import java.io.IOException;
import java.util.List;

/**
 * @author ToanNK16 on 2023-10-07
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public class LexicalAnalyzer {
    private static final IAnalyzeServices analyzeServices = new AnalyzeServices();

    public static void main(String[] args) {
        String filePath = "assets\\input.c";
        try {
            List<Token> tokens = analyzeServices.analyzeFile(filePath);
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
