package books.library.util.fx

import books.library.app.file.ProjectFilesManager
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Control
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.io.FileInputStream
import java.io.FileNotFoundException

@Service
class FxHelper : UtilFx {

    @Autowired
    private val projectFilesManager: ProjectFilesManager? = null

    override fun createStage(title: String): Stage {
        val stage = Stage()
        stage.title = title
        try {
            stage.icons.add(Image(FileInputStream(projectFilesManager!!.imageFileByName("book.png"))))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return stage
    }

    override fun createModalStage(title: String, owner: Window): Stage {
        val stage = Stage()
        stage.title = title
        stage.isResizable = false
        stage.centerOnScreen()
        stage.initModality(Modality.WINDOW_MODAL)
        stage.initOwner(owner)
        try {
            stage.icons.add(Image(FileInputStream(projectFilesManager!!.imageFileByName("book.png"))))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return stage
    }

    override fun createStyleScene(parent: Parent): Scene {
        val scene = Scene(parent)
        scene.stylesheets.add(javaClass.classLoader.getResource("css/neitral_style.css")!!.toExternalForm())
        return scene
    }

    override fun setStyle(parent: Parent) {
        val s = javaClass.classLoader.getResource("css/neitral_style.css")!!.toExternalForm()
        if (!parent.stylesheets.contains(s))
            parent.stylesheets.add(javaClass.classLoader.getResource("css/neitral_style.css")!!.toExternalForm())
    }

    override fun createAndShowAlert(type: Alert.AlertType, text: String, title: String, owner: Window){
        val alert = Alert(type)
        alert.contentText = text
        alert.initModality(Modality.WINDOW_MODAL)
        alert.initOwner(owner)
        alert.title = title
        alert.showAndWait()
    }

    //Метод переводит фокус на элемены с помощью кнопок UP и DOWN
    override fun arrowClickFocusListener(vararg controls: Control) {
        for (i in controls.indices) {
            controls[i].addEventHandler(KeyEvent.KEY_PRESSED ){ event ->
                if (event.code == KeyCode.DOWN) {
                    var t = i + 1
                    if (t >= controls.size) t = 0
                    controls[t].requestFocus()
                }else if(event.code == KeyCode.UP){
                    var t = i - 1
                    if (t < 0) t = controls.size - 1
                    controls[t].requestFocus()
                }
            }
        }
    }
}
