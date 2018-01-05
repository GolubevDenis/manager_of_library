package books.library.controller;

import books.library.ViewHolder;
import books.library.app.file.ProjectFilesManager;
import books.library.app.log.MyLogger;
import books.library.interfaces.UpdateData;
import books.library.model.Book;
import books.library.model.Reader;
import books.library.model.Subscription;
import books.library.print.ExportReportService;
import books.library.service.book.BookService;
import books.library.service.subscription.SubscriptionService;
import books.library.util.date.UtilDate;
import books.library.util.fx.UtilFx;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Optional;

public class SubscriptionController implements UpdateData{

    @FXML private Button delete_button;
    @FXML private Button add_button;
    @FXML private Button refactor_button;
    @FXML private Label print_button;
    @FXML private Button close_button;
    @FXML private Label export_button;

    @FXML private TableView<Subscription> subsctiprionsTable;

    @Autowired
    private MyLogger logger;

    @Autowired
    private ProjectFilesManager projectFilesManager;

    @Qualifier("editSubscriptionsView")
    @Autowired
    private ViewHolder editSubscriptionsView;

    @Autowired
    private UtilDate utilDate;

    @FXML private TableColumn<Subscription, String> date_return;
    @FXML private TableColumn<Subscription, String> number;
    @FXML private TableColumn<Subscription, String> otdel;
    @FXML private TableColumn<Subscription, String> author;
    @FXML private TableColumn<Subscription, String> title;

    private Stage stageEditSubscriptionsView;

    @Qualifier("exportServiceSubscriptions")
    @Autowired
    private ExportReportService reportService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UtilFx utilFx;

    private Reader reader;

    private boolean isCreated = false;

    public void setReader(Reader reader) {
        this.reader = reader;

        if(!isCreated){
            createColumns();
            createButtons();
            isCreated = true;
        }
        subsctiprionsTable.setItems(subscriptionService.insertData(reader.getSubscriptions()));
    }

    @PostConstruct
    public void init(){
        subsctiprionsTable.setRowFactory(param -> {
            TableRow rt = new ColorRow();
            param.refresh();
            return rt;
        });

    }

    @Override
    public void updateData() {
        subsctiprionsTable.setItems(subscriptionService.insertData(reader.getSubscriptions()));
    }

    private void createButtons(){
        try {
            print_button.graphicProperty().setValue(new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("documents.png")))));
            export_button.graphicProperty().setValue(new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("export.png")))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createColumns() {
        date_return.setCellValueFactory(cellData -> cellData.getValue().date_returnProperty());
        date_return.setCellFactory (TextFieldTableCell.forTableColumn ());
        date_return.setOnEditCommit ((t) -> {
                    t.getRowValue().setDate_return(t.getNewValue());
                    subscriptionService.update(t.getRowValue());
                }
        );

        number.setCellValueFactory(cellData -> cellData.getValue().getBook().numberProperty());
        number.setCellFactory (TextFieldTableCell.forTableColumn ());
        number.setOnEditCommit ((t) -> {
                    t.getRowValue().getBook().setNumber(t.getNewValue());
                    bookService.update(t.getRowValue().getBook());
                }
        );

        otdel.setCellValueFactory(cellData -> cellData.getValue().getBook().otdelProperty());
        otdel.setCellFactory (TextFieldTableCell.forTableColumn ());
        otdel.setOnEditCommit ((t) -> {
                    t.getRowValue().getBook().setOtdel(t.getNewValue());
                    bookService.update(t.getRowValue().getBook());
            }
        );

        author.setCellValueFactory(cellData -> cellData.getValue().getBook().authorProperty());
        author.setCellFactory (TextFieldTableCell.forTableColumn ());
        author.setOnEditCommit ((t) -> {
                    t.getRowValue().getBook().setAuthor(t.getNewValue());
                    bookService.update(t.getRowValue().getBook());
                }
        );

        title.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        title.setCellFactory (TextFieldTableCell.forTableColumn ());
        title.setOnEditCommit ((t) -> {
                    t.getRowValue().getBook().setTitle(t.getNewValue());
                    bookService.update(t.getRowValue().getBook());
                }
        );
    }

    @FXML
    private void delete() {
        try {
            Subscription subscription = subsctiprionsTable.getSelectionModel().getSelectedItem();
            subscription.getBook().setStatus(Book.STATUS_IS_NOT_ISSUED);
            bookService.update(subscription.getBook());
            subscriptionService.remove(subsctiprionsTable.getSelectionModel().getSelectedItem());
        }catch (Exception e){
            logger.logError("Ошибка удаления подписки", e);
        }
    }

    @FXML
    private void add() {
        EditSubscriptionController controller =  ((EditSubscriptionController) editSubscriptionsView.getController());
        controller.setRefactor(false);
        controller.setReader(reader);
        showEdit();
    }

    private void showEdit(){
        ((UpdateData) editSubscriptionsView.getController()).updateData();
        if(stageEditSubscriptionsView == null){
            stageEditSubscriptionsView = utilFx.createModalStage(reader.getLast_name() + " " + reader.getFirst_name(),
                    subsctiprionsTable.getScene().getWindow());
            stageEditSubscriptionsView.setScene(utilFx.createStyleScene(editSubscriptionsView.getView()));

            stageEditSubscriptionsView.show();
        }else {
            stageEditSubscriptionsView.show();
        }
    }

    @FXML
    private void print() {
        try {
            reportService.print(reader.getSubscriptions());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void export() {
        DirectoryChooser chooser = new DirectoryChooser();
        File directoryForInstallation = chooser.showDialog(subsctiprionsTable.getScene().getWindow());
        if(directoryForInstallation == null){
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Выберите формат отчета");

        ButtonType pdf = new ButtonType("PDF");
        ButtonType xml = new ButtonType("XML");
        ButtonType no = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(pdf, xml, no);

        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        try {
            if(optionalButtonType.get() == pdf){
                reportService.export(reader.getSubscriptions(), ExportReportService.PDF, directoryForInstallation.getAbsolutePath());
            }else if(optionalButtonType.get() == xml){
                reportService.export(reader.getSubscriptions(), ExportReportService.XML, directoryForInstallation.getAbsolutePath());
            }
        } catch (JRException e) {
            logger.logError("Ошибка экспорта", e);
        }
    }

    @FXML
    private void redactor() {
        Subscription selectedItem = subsctiprionsTable.getSelectionModel().getSelectedItem();
        EditSubscriptionController controller =  ((EditSubscriptionController) editSubscriptionsView.getController());
        controller.setSubscription(selectedItem);
        controller.setRefactor(true);
        controller.setReader(reader);
        showEdit();
    }

    @FXML
    private void close() {
        subsctiprionsTable.getScene().getWindow().hide();
    }

    private class ColorRow extends TableRow <Subscription> {
        @Override
        protected void updateItem(Subscription vars, boolean b) {
            super.updateItem(vars, b);
            try {
                if(utilDate.compareDates(new Date(), vars.getDate_return()))
                    setStyle("-fx-background-color:#eed5d2");
                else
                    setStyle("-fx-background-color: #E1F5FE");
            } catch (Exception e) {}
        }
    }
}
