package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import models.Token;

/**
 * @author ToanNK16 on 2023-10-07
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */
public interface IAnalyzeServices {
    List<Token> analyzeFile(String filePath) throws IOException;
}
