package books.library.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

public interface UtilFile {

    void writeToFile(File file, String text);
    String readFile(File file);
    String readFile(String path);
    List<String> readFileLineByLine(String path);
    String readFile(InputStream inputStream);
    boolean isExistDirectory(File file);
    boolean isExistFile(File file);
    void copyFilesFromDirectory(String from, String to) throws IOException;
    boolean copyFile(final String src, final String dst);
    boolean copyDir(final String src, final String dst);
    boolean isImage(File imageFile);
}
