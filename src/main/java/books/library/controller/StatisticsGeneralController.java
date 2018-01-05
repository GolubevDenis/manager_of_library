package books.library.controller;

import books.library.interfaces.UpdateData;
import books.library.model.SubscriptionHistory;
import books.library.service.book.BookService;
import books.library.service.reader.ReaderService;
import books.library.service.subscription.SubscriptionService;
import books.library.service.subscription_history.SubscriptionHistoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class StatisticsGeneralController implements UpdateData{

    @FXML private Label count_books;
    @FXML private Label count_readers;
    @FXML private Label count_all_subscription;
    @FXML private Label count_active_subscription;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionHistoryService subscriptionHistoryService;

    @Override
    public void updateData() {
        count_books.setText(String.valueOf(bookService.getCountRows()));
        count_readers.setText(String.valueOf(readerService.getCountRows()));
        count_all_subscription.setText(String.valueOf(subscriptionService.getCountRows()));
        count_active_subscription.setText(String.valueOf(subscriptionHistoryService.getCountRows()));
    }
}