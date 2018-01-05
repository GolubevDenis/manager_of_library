package books.library.controller;

import books.library.app.install.Installator;
import books.library.app.message.InstallingMessageEvent;
import books.library.util.file.UtilFile;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.io.File;
import java.util.*;

public class InstallPageController implements ApplicationListener<InstallingMessageEvent>{

    @Autowired
    private UtilFile utilFile;

    @Autowired
    private Installator installer;

    @FXML private Button button_select_path;
    @FXML private Button button_installing;
    @FXML private CheckBox box_on_desk;
    @FXML private ProgressBar progress_bar;
    @FXML private TextField field_path_directory;
    @FXML private Label label_error;
    @FXML private Label label_progress;

    private List<String> listMessagesEvent = new ArrayList();

    private File directoryForInstallation;


    @FXML
    private void clickSelectPath(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выберите путь для установки приложения");
        this.directoryForInstallation = chooser.showDialog(label_progress.getScene().getWindow());
        if(directoryForInstallation == null || !utilFile.isExistDirectory(directoryForInstallation)){
            label_error.setText("Выберите существующую директорию");
            directoryForInstallation = null;
        }else {
            field_path_directory.setText(directoryForInstallation.getAbsolutePath());
        }
    }

    private boolean isInstalled = false;

    @FXML
    private void install(){

        Task t = new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                if(!isInstalled){
                    if(directoryForInstallation != null){
                        installer.install(directoryForInstallation.getAbsolutePath(), box_on_desk.isSelected());
                        isInstalled = true;
                        Platform.runLater(()->{button_installing.setText("Завершить");});
                    }else {
                        Platform.runLater(()->{label_error.setText("Выберите директорию прежде чем установить программу");});
                    }
                }else {
                    Platform.exit();
                }
                return null;
            }
        };
        new Thread(t).start();
    }

    @Override
    public void onApplicationEvent(InstallingMessageEvent message) {
        if(message.isError()){
            showAllert(message);
            return;
        }
        Platform.runLater( () -> {
            listMessagesEvent.add(message.getMessage());
            label_progress.setText(message.getMessage());
            progress_bar.setProgress(progress_bar.getProgress() + 0.15);
        });
    }

    //метод для обработки ошибок установки
    void showAllert(InstallingMessageEvent message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message.getMessage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(label_progress.getScene().getWindow());
        alert.setTitle("Предупреждение");
        alert.showAndWait();
    }
}

