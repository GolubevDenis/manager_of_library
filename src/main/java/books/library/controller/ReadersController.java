package books.library.controller;

import books.library.ViewHolder;
import books.library.app.log.MyLogger;
import books.library.app.setting.Setting;
import books.library.interfaces.UpdateData;
import books.library.model.Reader;
import books.library.service.reader.ReaderService;
import books.library.util.fx.UtilFx;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

public class ReadersController implements UpdateData {

    @Autowired
    private ReaderService readerService;

    @FXML private Button history;
    @FXML private Button subscription;

    @FXML private TableColumn<Reader, String> column_phone;
    @FXML private TableColumn<Reader, String> column_last_name;
    @FXML private TableColumn<Reader, String> column_first_name;
    @FXML private TableColumn<Reader, String> column_otchestvo;
    @FXML private TableColumn<Reader, String> column_year_birth;
    @FXML private TableColumn<Reader, String> column_profession;
    @FXML private TableColumn<Reader, String> column_job;
    @FXML private TableColumn<Reader, String> column_school;
    @FXML private TableColumn<Reader, String> column_education;
    @FXML private TableColumn<Reader, String> column_address;
    @FXML private TableColumn<Reader, String> column_number_passport;
    @FXML private TableColumn<Reader, String> column_passport_series;
    @FXML private TableColumn<Reader, String> column_who_and_when;
    @FXML private TableColumn<Reader, String> column_date_entry;
    @FXML private TableColumn<Reader, String> column_number;

    @FXML private Button button_add;
    @FXML private Button button_remove;
    @FXML private Button button_edit;

    @FXML private ComboBox search_box;
    @FXML private TextField search_field;
    @FXML private Button search_button;
    @FXML private TableView<Reader> table_readers;

    @Qualifier(value = "editReaderView")
    @Autowired
    private ViewHolder addReaderView;

    @Qualifier(value = "subscriptionsView")
    @Autowired
    private ViewHolder subscriptionsView;

    @Qualifier(value = "subscriptionsHistoryView")
    @Autowired
    private ViewHolder subscriptionsHistoryView;

    @Autowired
    private MyLogger logger;

    @Autowired
    private UtilFx utilFx;

    @Autowired
    private Setting setting;

    private Stage stageEditReader;
    private Stage stageSubscriptionsView;
    private Stage stageSubscriptionsHistoryView;

    @PostConstruct
    private void init(){
        createSearch();
        createColumn();
        createTable();
    }

    @Override
    public void updateData(){
        createTable();
        updateVisibleColumns();
    }

    private void updateVisibleColumns(){
        column_number.setVisible(setting.getBooleanProperty("reader_column_number"));
        column_phone.setVisible(setting.getBooleanProperty("reader_column_phone"));
        column_last_name.setVisible(setting.getBooleanProperty("reader_column_last_name"));
        column_first_name.setVisible(setting.getBooleanProperty("reader_column_first_name"));
        column_otchestvo.setVisible(setting.getBooleanProperty("reader_column_otchestvo"));
        column_year_birth.setVisible(setting.getBooleanProperty("reader_column_year_birth"));
        column_profession.setVisible(setting.getBooleanProperty("reader_column_profession"));
        column_job.setVisible(setting.getBooleanProperty("reader_column_job"));
        column_school.setVisible(setting.getBooleanProperty("reader_column_school"));
        column_education.setVisible(setting.getBooleanProperty("reader_column_education"));
        column_address.setVisible(setting.getBooleanProperty("reader_column_address"));
        column_number_passport.setVisible(setting.getBooleanProperty("reader_column_number_passport"));
        column_passport_series.setVisible(setting.getBooleanProperty("reader_column_passport_series"));
        column_who_and_when.setVisible(setting.getBooleanProperty("reader_column_who_and_when"));
        column_date_entry.setVisible(setting.getBooleanProperty("reader_column_date_entry"));
    }

    private void createTable(){
        table_readers.setItems(readerService.findAll());
    }

    private void createColumn(){
        column_number.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        column_number.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_number.setOnEditCommit ((t) -> {
                    t.getRowValue().setNumber(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );


        column_phone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        column_phone.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_phone.setOnEditCommit ((t) -> {
                    t.getRowValue().setPhone(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_last_name.setCellValueFactory(cellData -> cellData.getValue().last_nameProperty());
        column_last_name.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_last_name.setOnEditCommit ((t) -> {
                    t.getRowValue().setLast_name(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );


        column_first_name.setCellValueFactory(cellData -> cellData.getValue().first_nameProperty());
        column_first_name.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_first_name.setOnEditCommit ((t) -> {
                    t.getRowValue().setFirst_name(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_otchestvo.setCellValueFactory(cellData -> cellData.getValue().otchestvoProperty());
        column_otchestvo.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_otchestvo.setOnEditCommit ((t) -> {
                    t.getRowValue().setOtchestvo(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_year_birth.setCellValueFactory(cellData -> cellData.getValue().year_birthProperty());
        column_year_birth.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_year_birth.setOnEditCommit ((t) -> {
                    t.getRowValue().setYear_birth(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_profession.setCellValueFactory(cellData -> cellData.getValue().professionProperty());
        column_profession.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_profession.setOnEditCommit ((t) -> {
                    t.getRowValue().setProfession(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_job.setCellValueFactory(cellData -> cellData.getValue().jobProperty());
        column_job.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_job.setOnEditCommit ((t) -> {
                    t.getRowValue().setJob(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_school.setCellValueFactory(cellData -> cellData.getValue().schoolProperty());
        column_school.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_school.setOnEditCommit ((t) -> {
                    t.getRowValue().setSchool(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_education.setCellValueFactory(cellData -> cellData.getValue().educationProperty());
        column_education.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_education.setOnEditCommit ((t) -> {
                    t.getRowValue().setEducation(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        column_address.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_address.setOnEditCommit ((t) -> {
                    t.getRowValue().setAddress(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_number_passport.setCellValueFactory(cellData -> cellData.getValue().number_passportProperty());
        column_number_passport.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_number_passport.setOnEditCommit ((t) -> {
                    t.getRowValue().setNumber_passport(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_passport_series.setCellValueFactory(cellData -> cellData.getValue().passport_seriesProperty());
        column_passport_series.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_passport_series.setOnEditCommit ((t) -> {
                    t.getRowValue().setPassport_series(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_who_and_when.setCellValueFactory(cellData -> cellData.getValue().who_and_whenProperty());
        column_who_and_when.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_who_and_when.setOnEditCommit ((t) -> {
                    t.getRowValue().setWho_and_when(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

        column_date_entry.setCellValueFactory(cellData -> cellData.getValue().date_entryProperty());
        column_date_entry.setCellFactory (TextFieldTableCell.forTableColumn ());
        column_date_entry.setOnEditCommit ((t) -> {
                    t.getRowValue().setDate_entry(t.getNewValue());
                    readerService.update(t.getRowValue());
                }
        );

    }

    private static final String SEARCH_NUMBER = "номеру";
    private static final String SEARCH_PHONE = "номеру телефона";
    private static final String SEARCH_LAST_NAME = "фамилии";
    private static final String SEARCH_FIRST_NAME = "имени";
    private static final String SEARCH_OTCHESTVO = "отчеству";
    private static final String SEARCH_YEAR_BIRTH = "году рождения";
    private static final String SEARCH_PROFESSION = "профессии";
    private static final String SEARCH_JOB = "месту работы";
    private static final String SEARCH_SCHOOL = "месту учебы";
    private static final String SEARCH_EDUCATION = "образованию";
    private static final String SEARCH_ADDRESS = "адресу";
    private static final String SEARCH_NUMBER_PASSPORT = "номеру паспорта";
    private static final String SEARCH_PASSPORT_SERIES = "серии паспорта";
    private static final String SEARCH_WHO_AND_WHEN = "месту выдаче паспорта";
    private static final String SEARCH_DATE_ENTRY = "дате вступления в библиотеку";

    private void createSearch(){
        search_box.getItems().addAll(
                SEARCH_NUMBER,
                SEARCH_LAST_NAME,
                SEARCH_FIRST_NAME,
                SEARCH_OTCHESTVO,
                SEARCH_PHONE,
                SEARCH_YEAR_BIRTH,
                SEARCH_PROFESSION,
                SEARCH_JOB,
                SEARCH_SCHOOL,
                SEARCH_EDUCATION,
                SEARCH_ADDRESS,
                SEARCH_NUMBER_PASSPORT,
                SEARCH_PASSPORT_SERIES,
                SEARCH_WHO_AND_WHEN,
                SEARCH_DATE_ENTRY
        );
    }

    @FXML
    private void search(){
        switch (search_box.getSelectionModel().getSelectedItem().toString()){
            case SEARCH_NUMBER:
                readerService.findByNumber(search_field.getText());
                break;
            case SEARCH_PHONE:
                readerService.findByPhone(search_field.getText());
                break;
            case SEARCH_LAST_NAME:
                readerService.findByLastName(search_field.getText());
                break;
            case SEARCH_FIRST_NAME:
                readerService.findByFirstName(search_field.getText());
                break;
            case SEARCH_OTCHESTVO:
                readerService.findByOtchestvo(search_field.getText());
                break;
            case SEARCH_PROFESSION:
                readerService.findByProfession(search_field.getText());
                break;
            case SEARCH_JOB:
                readerService.findByJob(search_field.getText());
                break;
            case SEARCH_SCHOOL:
                readerService.findBySchool(search_field.getText());
                break;
            case SEARCH_EDUCATION:
                readerService.findByEducation(search_field.getText());
                break;
            case SEARCH_ADDRESS:
                readerService.findByAddress(search_field.getText());
                break;
            case SEARCH_NUMBER_PASSPORT:
                readerService.findByNumberPasport(search_field.getText());
                break;
            case SEARCH_PASSPORT_SERIES:
                readerService.findByPassportSeries(search_field.getText());
                break;
            case SEARCH_WHO_AND_WHEN:
                readerService.findByWhoAndWhen(search_field.getText());
                break;
            case SEARCH_DATE_ENTRY:
                readerService.findByDateEntry(search_field.getText());
                break;
        }
    }

    @FXML
    public void addReader() {
        ((EditReaderController) addReaderView.getController()).setRefactor(false);
        createAndShowEditPane("Добавление читателя");
    }

    @FXML
    public void refactorReader() {
        ((EditReaderController) addReaderView.getController()).setRefactor(true);
        ((EditReaderController) addReaderView.getController()).setReader(table_readers.getSelectionModel().getSelectedItem());
        createAndShowEditPane("Редактирование читателя");
    }

    @FXML
    private void deleteReader(){
        logger.logInfo("Удаление читателя");
        readerService.remove(table_readers.getSelectionModel().getSelectedItem());
    }

    private void createAndShowEditPane(String title){
        if(stageEditReader == null){
            stageEditReader = utilFx.createModalStage(title, button_add.getScene().getWindow());
            stageEditReader.setScene(utilFx.createStyleScene(addReaderView.getView()));
            stageEditReader.show();
        }else {
            stageEditReader.show();
        }
    }

    @FXML
    private void openSubscription() {
        Reader reader = table_readers.getSelectionModel().getSelectedItem();
        ((SubscriptionController) subscriptionsView.getController()).setReader(reader);
        ((UpdateData) subscriptionsView.getController()).updateData();
        if(stageSubscriptionsView == null){
            stageSubscriptionsView = utilFx.createModalStage(reader.getLast_name() + " " + reader.getFirst_name(),
                    button_add.getScene().getWindow());
            stageSubscriptionsView.setScene(utilFx.createStyleScene(subscriptionsView.getView()));
            stageSubscriptionsView.show();
        }else {
            stageSubscriptionsView.show();
        }
    }

    @FXML
    private void openSubscriptionHistory() {
        Reader reader = table_readers.getSelectionModel().getSelectedItem();
        ((SubscriptionHistoryController) subscriptionsHistoryView.getController()).setReader(reader);
        if(stageSubscriptionsHistoryView == null){
            stageSubscriptionsHistoryView = utilFx.createModalStage("История " + reader.getLast_name() + " " + reader.getFirst_name(),
                    button_add.getScene().getWindow());
            stageSubscriptionsHistoryView.setScene(utilFx.createStyleScene(subscriptionsHistoryView.getView()));
            stageSubscriptionsHistoryView.show();
        }else {
            stageSubscriptionsHistoryView.show();
        }
    }
}
