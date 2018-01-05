package books.library.controller;

import books.library.app.log.MyLogger;
import books.library.service.subscription.SubscriptionService;
import books.library.util.date.UtilDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

public class StatisticsSubscriptionController {

    @FXML private LineChart<String, Number> chart;
    @FXML private DatePicker date_start;
    @FXML private DatePicker date_end;
    @FXML private Button show;

    @Autowired
    private MyLogger logger;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UtilDate utilDate;


    @PostConstruct
    public void init(){
        date_start.getEditor().setText(utilDate.getDateText(utilDate.getDatePlusOrMinusWeek(new Date(), false)));
        date_end.getEditor().setText(utilDate.getDateText(new Date()));
        show();
    }

    @FXML
    private void show(){
        logger.logInfo("Show statistics");
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();

        Map<String, Number> map =
                subscriptionService.getCountSubscriptionByDates(utilDate.parseDate(date_start.getEditor().getText()), utilDate.parseDate(date_end.getEditor().getText()));
        for(Map.Entry<String, Number> e : map.entrySet()){
            data.add(new XYChart.Data(e.getKey(), e.getValue()));
        }
        logger.logInfo("Show statistics: data.lenght = " + data.size());
        XYChart.Series series = new XYChart.Series();
        series.setData(data);
        series.setName("Статистика взятых книг");
        chart.getData().clear();
        chart.getData().add(series);
    }

}
