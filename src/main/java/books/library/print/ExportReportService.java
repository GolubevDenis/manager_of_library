package books.library.print;

import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface ExportReportService<T> {

    String XML = "XML";
    String HTML = "HTML";
    String PDF = "PDF";

    //Экспортирует обьекты в EXEL, HTML, PDF. Файл сохраняется по пути path
    void export(List<T> objectList, String to, String path) throws JRException;
    void print(List<T> objectList) throws JRException;
}
