package books.library.controller;

import books.library.model.Reader;
import books.library.model.Subscription;
import books.library.model.SubscriptionHistory;
import books.library.service.subscription_history.SubscriptionHistoryService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionHistoryController {

    @FXML private TableView<SubscriptionHistory> subsctiprionsTable;

    @FXML private TableColumn<SubscriptionHistory, String> date_return;
    @FXML private TableColumn<SubscriptionHistory, String> number;
    @FXML private TableColumn<SubscriptionHistory, String> otdel;
    @FXML private TableColumn<SubscriptionHistory, String> author;
    @FXML private TableColumn<SubscriptionHistory, String> title;

    @Autowired
    private SubscriptionHistoryService subscriptionService;

    private boolean isCreated = false;

    private Reader reader;

    public void setReader(Reader reader) {
        this.reader = reader;

        if(!isCreated){
            createColumns();
            isCreated = true;
        }
        subsctiprionsTable.setItems(subscriptionService.insertData(reader.getSubscriptionsHistory()));
    }

    private void createColumns() {
        date_return.setCellValueFactory(cellData -> cellData.getValue().date_returnProperty());
        number.setCellValueFactory(cellData -> cellData.getValue().getBook().numberProperty());
        otdel.setCellValueFactory(cellData -> cellData.getValue().getBook().otdelProperty());
        author.setCellValueFactory(cellData -> cellData.getValue().getBook().authorProperty());
        title.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
    }

    @FXML
    private void close() {
        subsctiprionsTable.getScene().getWindow().hide();
    }
}
