package books.library.controller;

import books.library.ViewHolder;
import books.library.app.file.ProjectFilesManager;
import books.library.interfaces.UpdateData;
import books.library.repository.book.BookRepository;
import books.library.repository.reader.ReaderRepository;
import books.library.repository.subsctiprion.SubscriptionRepository;
import books.library.util.file.UtilFile;
import books.library.util.fx.UtilFx;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class MainController {

    @Qualifier("booksView")
    @Autowired ViewHolder booksView;

    @Qualifier("readersView")
    @Autowired ViewHolder readersView;

    @Qualifier("expiredView")
    @Autowired ViewHolder expiredView;

    @Qualifier("statisticsGeneralView")
    @Autowired ViewHolder statisticsGeneralView;

    @Qualifier("statisticsSubscriptionView")
    @Autowired ViewHolder statisticsSubscriptionView;

    @Qualifier("settingView")
    @Autowired ViewHolder settingView;

    @Autowired
    private ProjectFilesManager projectFilesManager;

    @Autowired
    private UtilFile utilFile;

    @Autowired
    private UtilFx utilFx;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;



    @FXML private BorderPane main_pane;
    @FXML private BorderPane main_border_pane;

    @FXML private Label label_books;
    @FXML private Label label_readers;
    @FXML private Label statistics_general;
    @FXML private Label label_expired;
    @FXML private Label statistics_subscriptions;
    @FXML private Label label_setting;


    @FXML private Label copy_db;
    @FXML private Label clear_db;
    @FXML private Label replace_db;

    @PostConstruct
    public void init() {
        showBooksView();
        createButtons();

        main_border_pane.getStylesheets().add(getClass().getClassLoader().getResource("css/neitral_style.css").toExternalForm());
    }


    private final int SIZE_IMAGES = 17;
    private void createButtons(){
        try {
            copy_db.graphicProperty().setValue(new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("db_warn.png")))));
            clear_db.graphicProperty().setValue(new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("db_delete.png")))));
            replace_db.graphicProperty().setValue(new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("db_copy.png")))));


            ImageView view_expired = new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("clock.png"))));
            view_expired.setFitWidth(SIZE_IMAGES);
            view_expired.setFitHeight(SIZE_IMAGES);
            label_expired.graphicProperty().setValue(view_expired);

            ImageView view_profil = new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("profil.png"))));
            view_profil.setFitWidth(SIZE_IMAGES);
            view_profil.setFitHeight(SIZE_IMAGES);
            label_readers.graphicProperty().setValue(view_profil);

            ImageView view_books = new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("books.png"))));
            view_books.setFitWidth(SIZE_IMAGES);
            view_books.setFitHeight(SIZE_IMAGES);
            label_books.graphicProperty().setValue(view_books);

            ImageView view_statistics_general = new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("bar.png"))));
            view_statistics_general.setFitWidth(SIZE_IMAGES);
            view_statistics_general.setFitHeight(SIZE_IMAGES);
            statistics_general.graphicProperty().setValue(view_statistics_general);

            ImageView view_statistics_subscriptions = new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("line.png"))));
            view_statistics_subscriptions.setFitWidth(SIZE_IMAGES);
            view_statistics_subscriptions.setFitHeight(SIZE_IMAGES);
            statistics_subscriptions.graphicProperty().setValue(view_statistics_subscriptions);

            ImageView view_setting = new ImageView(new Image(new FileInputStream(projectFilesManager.imageFileByName("settings.png"))));
            view_setting.setFitWidth(SIZE_IMAGES);
            view_setting.setFitHeight(SIZE_IMAGES);
            label_setting.graphicProperty().setValue(view_setting);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showBooksView(){
        main_pane.getChildren().clear();
        try{
            ((UpdateData)booksView.getController()).updateData();
        }catch (Exception e){
            e.printStackTrace();
        }
        utilFx.setStyle(booksView.getView());
        main_pane.setCenter(booksView.getView());
    }

    @FXML
    private void showExpiredView(){
        main_pane.getChildren().clear();
        try{
            ((UpdateData)expiredView.getController()).updateData();
        }catch (Exception e){
            e.printStackTrace();
        }
        utilFx.setStyle(expiredView.getView());
        main_pane.setCenter(expiredView.getView());
    }

    @FXML
    private void showReadersView(){
        main_pane.getChildren().clear();
        try{
            ((UpdateData)readersView.getController()).updateData();
        }catch (Exception e){
            e.printStackTrace();
        }
        utilFx.setStyle(readersView.getView());
        main_pane.setCenter(readersView.getView());
    }

    @FXML
    private void showSettingView() {
        main_pane.getChildren().clear();
        try{
            ((UpdateData)settingView.getController()).updateData();
        }catch (Exception e){
            e.printStackTrace();
        }
        utilFx.setStyle(settingView.getView());
        main_pane.setCenter(settingView.getView());
    }

    @FXML
    private void showStatisticsGeneralView(){
        main_pane.getChildren().clear();
        try{
            ((UpdateData)statisticsGeneralView.getController()).updateData();
        }catch (Exception e){
            e.printStackTrace();
        }
        utilFx.setStyle(statisticsGeneralView.getView());
        main_pane.setCenter(statisticsGeneralView.getView());
    }

    @FXML
    private void showStatisticsSubscriptionView(){
        main_pane.getChildren().clear();
        utilFx.setStyle(statisticsSubscriptionView.getView());
        main_pane.setCenter(statisticsSubscriptionView.getView());
    }

    @FXML
    private void copy_db() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выберите директорию, в которую необходимо поместить копию базы данных");
        File directoryForCoping = chooser.showDialog(clear_db.getScene().getWindow());
        if(directoryForCoping == null){
            return;
        }
        utilFile.copyFile(projectFilesManager.dataBaseFile().getAbsolutePath(),
                directoryForCoping.getAbsolutePath() + File.separator + "copy_data_base.db");
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Информация");
        alertInfo.setHeaderText("Операция прошла успешно.");
        alertInfo.setContentText("Копия базы данных помещена в " + directoryForCoping.getAbsolutePath());
        alertInfo.showAndWait();
    }

    @FXML
    private void clear_db() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Вы действительно хотите удалить данные из базы данных?");
        alert.setContentText("Эта операция необратима");
        alert.getButtonTypes().clear();

        ButtonType yes = new ButtonType("Да");

        alert.getButtonTypes().addAll(yes);

        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        ButtonType click = optionalButtonType.get();

        if(click == yes){
            bookRepository.clear();
            readerRepository.clear();
            subscriptionRepository.clear();

            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setTitle("Информация");
            alertInfo.setHeaderText("Операция прошла успешно. База данных очищена.");
            alertInfo.setContentText("Для корректной работы зайдите в программу еще раз. Программа будет выключна");
            alertInfo.showAndWait();
            Platform.exit();
        }
        alert.close();
    }

    @FXML
    private void replace_db() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Вы действительно хотите заменить базу данных?");
        alert.setContentText("Эта операция необратима. Если база данных имеет другую структуру - программа будет работать некоректно");
        alert.getButtonTypes().clear();

        ButtonType yes = new ButtonType("Да");

        alert.getButtonTypes().addAll(yes);

        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        ButtonType click = optionalButtonType.get();

        if(click == yes){
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Выберите новую базу данных");
            File newDB = chooser.showOpenDialog(clear_db.getScene().getWindow());
            if(newDB == null){
                return;
            }
            projectFilesManager.dataBaseFile().delete();
            utilFile.copyFile(newDB.getAbsolutePath(),
                    projectFilesManager.dataBaseFile().getAbsolutePath());
            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setTitle("Информация");
            alertInfo.setHeaderText("Операция прошла успешно. База данных успешно заменена.");
            alertInfo.setContentText("Для корректной работы зайдите в программу еще раз. Программа будет выключна");
            alertInfo.showAndWait();
            Platform.exit();
        }
    }
}
