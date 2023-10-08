import java.io.IOException;
import java.util.List;

/**
 * @author ToanNK16 on 2023-10-08
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public interface ILexicalAnalyzeServices {
    List<Token> analyzeFile(String filePath) throws IOException;
}
