package Core.Common;



import java.io.IOException;
import java.util.List;
/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public interface CSVReader<T> {
    void openReader(String fileName) throws IOException;
    List<T> readBody() throws IOException;
    void closeReader() throws IOException;
}
