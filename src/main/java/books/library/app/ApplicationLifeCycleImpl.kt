package books.library.app

import books.library.ViewHolder
import books.library.app.file.ProjectFilesManager
import books.library.app.install.Installator
import books.library.controller.SplashScreenController
import books.library.util.fx.UtilFx
import javafx.scene.image.Image
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

import java.io.FileInputStream
import java.io.FileNotFoundException

@Lazy
@Component
class ApplicationLifeCycleImpl : ApplicationLifeCycle {

    @Autowired
    private val installator: Installator? = null
    @Autowired
    private val projectFilesManager: ProjectFilesManager? = null
    @Autowired
    private val utilFx: UtilFx? = null

    private var primaryStage: Stage? = null

    @Qualifier("mainView")
    @Autowired
    private val mainView: ViewHolder? = null

    @Qualifier("splashScreenView")
    @Autowired
    private val splashScreenView: ViewHolder? = null

    @Qualifier("installPageView")
    @Autowired
    private val installPageView: ViewHolder? = null

    override fun startApplication(primaryStage: Stage) {
        this.primaryStage = primaryStage
        try {
            this.primaryStage!!.icons.add(Image(FileInputStream(projectFilesManager!!.imageFileByName("book.png"))))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        if (!installator!!.isInstalled) {
            showInstallingWindow()
        } else {
            showSplashScreen()
        }
    }

    private fun showSplashScreen() {
        val stage = utilFx!!.createStage("Library Books Manager")
        stage.title = "Library Books Manager"
        stage.scene = utilFx.createStyleScene(splashScreenView!!.view)
        stage.isResizable = true
        stage.centerOnScreen()
        (splashScreenView.controller as SplashScreenController).setPrimaryStage(primaryStage, mainView!!.view)
        stage.show()
        (splashScreenView.controller as SplashScreenController).start()
    }

    private fun showMainWindow() {
        primaryStage!!.title = "Library Books Manager"
        primaryStage!!.scene = utilFx!!.createStyleScene(mainView!!.view)
        primaryStage!!.isResizable = true
        primaryStage!!.centerOnScreen()
        primaryStage!!.show()
    }

    private fun showInstallingWindow() {
        val stage = utilFx!!.createStage("Мастер установки Library Books Manager")
        stage.scene = utilFx.createStyleScene(installPageView!!.view)
        stage.isResizable = false
        stage.centerOnScreen()
        stage.show()
    }
}
