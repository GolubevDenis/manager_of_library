package books.library.app.setting

import books.library.app.file.ProjectFilesManager
import books.library.app.log.MyLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Properties

@Service
class SettingImpl : Setting {

    @Autowired private lateinit var projectFilesManager: ProjectFilesManager
    @Autowired private lateinit var logger: MyLogger

    private lateinit var properties: Properties

    @PostConstruct
    fun postConstruct() {
        properties = Properties()
        try {
            logger.logInfo("Загрузка настроек...")
            properties.load(FileInputStream(projectFilesManager.settingFile()))
            logger.logInfo("Загрузка настроек завершена")
        } catch (e: IOException) {
            logger.logError("Ошибка загрузки настроек", e)
        }

    }

    override fun getBooleanProperty(nameProperty: String): Boolean {
        return java.lang.Boolean.valueOf(properties.getProperty(nameProperty))
    }

    override fun setProperty(nameProperty: String, property: String) {
        properties.setProperty(nameProperty, property)
        properties.store(FileOutputStream(projectFilesManager.settingFile()), "")
    }

    override fun setProperty(nameProperty: String, property: Boolean) {
        properties.setProperty(nameProperty, property.toString())
        properties.store(FileOutputStream(projectFilesManager.settingFile()), "")
    }
}
