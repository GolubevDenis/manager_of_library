package books.library.controller;

import books.library.interfaces.UpdateData;
import books.library.model.Subscription;
import books.library.service.subscription.SubscriptionService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ExpiredController implements UpdateData {

    @FXML private TableView<Subscription> table;

    @FXML private TableColumn<Subscription, String> column_date_return;
    @FXML private TableColumn<Subscription, String> column_number;
    @FXML private TableColumn<Subscription, String> column_otdel;
    @FXML private TableColumn<Subscription, String> column_author;
    @FXML private TableColumn<Subscription, String> column_title;

    @FXML private Label number;
    @FXML private Label otdel;
    @FXML private Label description;
    @FXML private Label title;
    @FXML private Label author;
    @FXML private Label genre;
    @FXML private Label publisher;
    @FXML private Label year;
    @FXML private Label count_pages;
    @FXML private Label language;

    @FXML private Label job;
    @FXML private Label profession;
    @FXML private Label education;
    @FXML private Label school;
    @FXML private Label first_name;
    @FXML private Label last_name;
    @FXML private Label phone;
    @FXML private Label address;
    @FXML private Label number_passport;
    @FXML private Label otchestvo;
    @FXML private Label passport_series;
    @FXML private Label who_and_when;
    @FXML private Label year_birth;
    @FXML private Label date_entry;

    @Autowired
    private SubscriptionService subscriptionService;


    @PostConstruct
    public void init(){
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                initDatas(table.getSelectionModel().getSelectedItem());
            }
        });
        createColumns();
        table.setItems(subscriptionService.findExpired());
        try {
            initDatas(table.getItems().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData(){
        table.setItems(subscriptionService.findExpired());
    }

    private void initDatas(Subscription subscription) {
        number.setText(subscription.getBook().getNumber());
        otdel.setText(subscription.getBook().getOtdel());
        description.setText(subscription.getBook().getDescription());
        title.setText(subscription.getBook().getTitle());
        author.setText(subscription.getBook().getAuthor());
        genre.setText(subscription.getBook().getGenre());
        publisher.setText(subscription.getBook().getPublisher());
        year.setText(subscription.getBook().getYear());
        count_pages.setText(subscription.getBook().getCount_pages());
        language.setText(subscription.getBook().getLanguage());

        job.setText(subscription.getReader().getJob());
        profession.setText(subscription.getReader().getProfession());
        education.setText(subscription.getReader().getEducation());
        school.setText(subscription.getReader().getSchool());
        first_name.setText(subscription.getReader().getFirst_name());
        last_name.setText(subscription.getReader().getLast_name());
        phone.setText(subscription.getReader().getPhone());
        address.setText(subscription.getReader().getAddress());
        number_passport.setText(subscription.getReader().getNumber_passport());
        otchestvo.setText(subscription.getReader().getOtchestvo());
        passport_series.setText(subscription.getReader().getPassport_series());
        who_and_when.setText(subscription.getReader().getWho_and_when());
        year_birth.setText(subscription.getReader().getYear_birth());
        date_entry.setText(subscription.getReader().getDate_entry());
    }

    private void createColumns(){
        column_date_return.setCellValueFactory(cellData
                -> cellData.getValue().date_returnProperty());
        column_number.setCellValueFactory(cellData -> cellData.getValue().getBook().numberProperty());
        column_otdel.setCellValueFactory(cellData -> cellData.getValue().getBook().otdelProperty());
        column_author.setCellValueFactory(cellData -> cellData.getValue().getBook().authorProperty());
        column_title.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
    }

}
