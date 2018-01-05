package books.library.print;

import books.library.app.file.ProjectFilesManager;
import books.library.app.log.MyLogger;
import books.library.model.Subscription;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component(value = "exportServiceSubscriptions")
public class ExportServiceSubscriptions implements ExportReportService<Subscription> {

    private boolean isInit = false;

    private JasperReport report;

    @Autowired
    private ProjectFilesManager projectFilesManager;

    @Autowired
    private MyLogger logger;

    private final String COLUMN_DATE_RETURN = "date_return";
    private final String COLUMN_NUMBER = "number";
    private final String COLUMN_OTDEL = "otdel";
    private final String COLUMN_TITLE = "title";

    private final int MIN_ROWS = 16;

    //Создает таблицу заполненную данными
    private DefaultTableModel createTableData(List<Subscription> listData) {

        DefaultTableModel tableModel;
        String[] columns = new String[]{
                COLUMN_DATE_RETURN, COLUMN_NUMBER, COLUMN_OTDEL, COLUMN_TITLE
        };

        String[][] data = null;
        if(listData.size() < MIN_ROWS){
            data = new String[MIN_ROWS][4];
        }else {
            data = new String[listData.size()][4];
        }

        for (int i = 0; i < listData.size(); i++) {
            Subscription s = listData.get(i);
            data[i][0] = s.getDate_return();
            data[i][1] = s.getBook().getNumber();
            data[i][2] = s.getBook().getOtdel();
            data[i][3] = s.getBook().getAuthor() + " " + s.getBook().getTitle();
        }

        //минимальное кол-во строк должно быть равно MIN_ROWS
        if (listData.size() < MIN_ROWS) {
            for (int i = listData.size(); i < MIN_ROWS; i++) {
                data[i][0] = " ";
                data[i][1] = " ";
                data[i][2] = " ";
                data[i][3] = " ";
            }
        }

        tableModel = new DefaultTableModel(data, columns);
        return tableModel;
    }

    @Override
    public void export(List<Subscription> subscription, String to, String path) throws JRException {
        JasperPrint printReport = prepare(subscription);
        logger.logInfo("Начало экспорта " + to + " отчета в " + path + File.separator +"t.html");
        switch (to){
            case ExportReportService.XML:
                JasperExportManager.exportReportToXmlFile(printReport, path + File.separator + new Random().nextInt() + ".xml", false);
                break;
            case ExportReportService.HTML:
                JasperExportManager.exportReportToHtmlFile(printReport, path + File.separator +"t.html");
                break;
            case ExportReportService.PDF:
                JasperExportManager.exportReportToPdfFile(printReport, path+ File.separator + new Random().nextInt() + ".pdf");
                break;
        }
        logger.logInfo("Экспорт " + to + " отчета в " + path + " завершен");
    }

    @Override
    public void print(List<Subscription> objectList) throws JRException {
        JasperPrint printReport = prepare(objectList);
        JasperPrintManager.printReport(printReport, true);
    }

    //метод должен вызываться в начале export() и print(). Инициализурует сервис и заполняет данными отчет
    //возвращает готовый к печати или экспорту JasperPrint
    private JasperPrint prepare(List<Subscription> subscription) throws JRException {
        if(!isInit()){
            init();
        }
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), new JRTableModelDataSource(createTableData(subscription)));
//
//
//        JRReportFont font = new JRDesignReportFont();
//        font.setPdfFontName(this.getServletContext().getRealPath("/") + "actions/arial.ttf");
//        font.setPdfEncoding("UTF-8");
//        font.setPdfEmbedded(true);
//        pr.setDefaultFont(font);

        return print;
    }


    //метод вызывается для инициализации отчета
    private void init(){
        try {
            logger.logInfo("Начало инициализации сервиса подписок...");
            report = JasperCompileManager.compileReport(projectFilesManager.reportFileByName("report_subscriptions.jrxml").getAbsolutePath());
            logger.logInfo("Завершение инициализации сервиса подписок...");
        } catch (Exception e) {
            logger.logError("Ошибка инициализации сервиса печати подписок", e);
        }
    }

    private boolean isInit(){
        return isInit;
    }
}
