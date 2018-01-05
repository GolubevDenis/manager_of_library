package books.library.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class SplashScreenController {

    public ProgressIndicator progress;
    private Stage primaryStage;
    private Parent view;

    public void start(){
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (progress.getProgress() < 1){
                    progress.setProgress(progress.getProgress() + 0.01);
                    Thread.sleep(30);
                }
                Platform.runLater(() -> {
                    progress.getScene().getWindow().hide();
                    showMainWindow();
                });

                return null;
            }
        };
        new Thread(task).start();
    }

    public void setPrimaryStage(Stage primaryStage, Parent view) {
        this.primaryStage = primaryStage;
        this.view = view;
    }

    private void showMainWindow(){
        primaryStage.setTitle("Library Books Manager");
        primaryStage.setScene(new Scene(view));
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
