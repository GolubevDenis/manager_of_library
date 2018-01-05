package books.library;

import books.library.app.ApplicationLifeCycleImpl;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.Map;
import java.util.Properties;

public class Application extends AbstractJavaFxApplicationSupport {

    @Autowired
    private ApplicationLifeCycleImpl applicationLifeCycle;

    @Override
    public void start(Stage primaryStage) throws Exception {
        applicationLifeCycle.startApplication(primaryStage);
    }

    public static void main(String[] args) {
        launchApp(Application.class, args);
    }

}
