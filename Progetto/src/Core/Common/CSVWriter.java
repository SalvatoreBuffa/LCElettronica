package Core.Common;

import java.io.IOException;
import java.util.List;
/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public interface CSVWriter<T> {
    void openWriter(String fileName) throws IOException;
    default void createHeader(String type) throws IOException {};
    default void createHeader() throws IOException {};
    void createBody(List<T> data) throws IOException;
    void closeWriter() throws IOException;
}
