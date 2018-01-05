package books.library.util.fx

import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Control
import javafx.stage.Stage
import javafx.stage.Window
import org.springframework.stereotype.Service

interface UtilFx {

    fun createStage(title: String): Stage
    fun createModalStage(title: String, owner: Window): Stage
    fun createStyleScene(parent: Parent): Scene
    fun setStyle(parent: Parent)
    fun createAndShowAlert(type: Alert.AlertType = Alert.AlertType.INFORMATION, text: String, title: String, owner: Window)
    fun arrowClickFocusListener(vararg controls: Control)
}
