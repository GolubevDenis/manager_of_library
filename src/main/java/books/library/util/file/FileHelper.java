package books.library.util.file;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Lazy
@Component
public class FileHelper implements UtilFile{

    /*
        Метод записывает строку в файл
    */
    @Override
    public void writeToFile(File file, String txt){
        try(OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(new FileOutputStream(file))) {
            outputStreamWriter.write(txt);
            outputStreamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static String[] extensionImages = new String[]{
        "jpg", "jpeg", "png", "bmp", "gif"
    };

    /*
        Метод возвращает все содержимое файла в обьекте String
    */
    @Override
    public String readFile(File file) {
        return readFile(file.getAbsolutePath());
    }

    /*
        Метод возвращает все содержимое файла в обьекте String
    */
    @Override
    public String readFile(String path) {
        StringBuilder stringBuilder = new StringBuilder("");

        try(Scanner scanner = new Scanner(new FileInputStream(path))) {
            while (scanner.hasNextLine()){
                stringBuilder.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /*
        Метод возвращает все содержимое потока в обьекте String
    */
    @Override
    public String readFile(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder("");

        try(Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()){
                stringBuilder.append(scanner.nextLine());
            }
        }

        return stringBuilder.toString();
    }

    /*
        Проверяет существует ли данная директория
    */
    @Override
    public boolean isExistDirectory(File file) {
        return (file.exists() && file.isDirectory());
    }

    /*
        Проверяет существует ли данный файл
    */
    @Override
    public boolean isExistFile(File file) {
        return (file.exists() && !file.isDirectory());
    }

    /*
        Метод возвращает содержимое файла в List<String>, где каждая его итерация - новая строка файла
    */
    @Override
    public List<String> readFileLineByLine(String path) {
        List<String> listTests = new ArrayList<>();

        try(Scanner scanner = new Scanner(new FileInputStream(path))) {
            while (scanner.hasNextLine()){
                listTests.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return listTests;
    }

    /*
        Метод копирует все файлы из одной директории в другую
    */
    @Override
    public void copyFilesFromDirectory(String from, String to) throws IOException {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        File[] files = fileFrom.listFiles();
        for(File f : files){
            File oldFile = new File(fileFrom, f.getName());
            File newFile = new File(fileTo, f.getName());
            if(f.isDirectory()){
                copyDir(oldFile.getAbsolutePath(), newFile.getAbsolutePath());
            }else {
                copyFile(oldFile.getAbsolutePath(), newFile.getAbsolutePath());
            }
        }
    }

    @Override
    public boolean copyDir(final String src, final String dst) {
        final File srcFile = new File(src);
        final File dstFile = new File(dst);
        if (srcFile.exists() && srcFile.isDirectory() && !dstFile.exists()) {
            dstFile.mkdir();
            File nextSrcFile;
            String nextSrcFilename, nextDstFilename;
            for (String filename : srcFile.list()) {
                nextSrcFilename = srcFile.getAbsolutePath()
                        + File.separator + filename;
                nextDstFilename = dstFile.getAbsolutePath()
                        + File.separator + filename;
                nextSrcFile = new File(nextSrcFilename);
                if (nextSrcFile.isDirectory()) {
                    copyDir(nextSrcFilename, nextDstFilename);
                } else {
                    copyFile(nextSrcFilename, nextDstFilename);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isImage(File imageFile) {
        String path = imageFile.getAbsolutePath();
        String extension = getFileExtension(path);
        if(extension.length() < 5)
            for(String e : extensionImages){
                if(extension.equals(e)){
                    return true;
                }
            }
        return false;
    }

    private static String getFileExtension(String mystr) {
        int index = mystr.indexOf('.');
        return index == -1? null : mystr.substring(index);
    }

    private static final int BUFFER_SIZE = 1024;

    @Override
    public boolean copyFile(final String src, final String dst) {
        final File srcFile = new File(src);
        final File dstFile = new File(dst);
        if (srcFile.exists() && srcFile.isFile() && !dstFile.exists()) {
            try (InputStream in = new FileInputStream(srcFile);
                 OutputStream out = new FileOutputStream(dstFile)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes;
                while ((bytes = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytes);
                }
            } catch (Exception ex) {

                return false;
            }
            return true;
        } else {
            return false;
        }
    }

}
