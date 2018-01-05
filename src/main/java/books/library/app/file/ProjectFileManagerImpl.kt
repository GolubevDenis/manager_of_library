package books.library.app.file

import org.springframework.stereotype.Component
import java.io.File
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

/*
    Класс для управления файлами проекта. Получения ресурсов.
*/
@Component
class ProjectFileManagerImpl : ProjectFilesManager {

    private val IMAGES_DIRECTORY_NAME = "images"
    private val DATA_DIRECTORY_NAME = "data"
    private val REPORT_DIRECTORY_NAME = "reports"
    private val INSTALL_FILE_NAME = "installed.txt"
    private val LOG_PROPERTIES_FILE_NAME = "log4j.properties"
    private val DATA_BASE_NAME_FILE = "books.db"
    private val SETTING_NAME_FILE = "setting.properties"
    //Имя папки с логами
    private val LOG_DIRECTORY_NAME = "logs"
    //Имя файла с логами
    private val LOG_FILE_NAME = "file_log.log"
    private val FONTS_FILE_DIRECTORY_NAME = "fonts"
    private val FONT_ARIAL_FILE = "arial.ttf"
    private val DEFAULT_FONT = "arial.ttf"
    private val S = File.separator

    //Структура папок, которая должна быть создана при установке
    private val listNeededFiles = arrayOf(DATA_DIRECTORY_NAME, LOG_DIRECTORY_NAME)

    override fun fontsDirectoryFile() = File(applicationJarFile() + S + FONTS_FILE_DIRECTORY_NAME)
    override fun fontFile() = File(applicationJarFile() + S + FONTS_FILE_DIRECTORY_NAME + S + DEFAULT_FONT)
    override fun fontFileByName(name: String) = File(applicationJarFile() + S + FONTS_FILE_DIRECTORY_NAME + S + name)
    override fun listNameNeededFiles() = listNeededFiles
    override fun imageFileByName(name: String) = File(applicationJarFile() + S + IMAGES_DIRECTORY_NAME + S + name)
    override fun logFile() = File(applicationJarFile() + S + LOG_DIRECTORY_NAME + S + LOG_FILE_NAME)
    override fun logPropertiesFile() = File(applicationJarFile() + S + LOG_PROPERTIES_FILE_NAME)
    override fun installFile() = File(applicationJarFile() + S + INSTALL_FILE_NAME)
    override fun dataBaseFile() = File(applicationJarFile() + S + DATA_DIRECTORY_NAME + S + DATA_BASE_NAME_FILE)
    override fun settingFile() = File(applicationJarFile() + S + DATA_DIRECTORY_NAME + S + SETTING_NAME_FILE)
    override fun reportFileByName(name: String) = File(applicationJarFile() + S + REPORT_DIRECTORY_NAME + S + name)

    override fun imagesDirectoryName() = IMAGES_DIRECTORY_NAME
    override fun dataDirectoryName() = DATA_DIRECTORY_NAME
    override fun reportDirectoryName() = REPORT_DIRECTORY_NAME
    override fun installFileName() = INSTALL_FILE_NAME
    override fun logPropertiesFileName() = LOG_PROPERTIES_FILE_NAME
    override fun dataBaseNameFile() = DATA_BASE_NAME_FILE
    override fun logDirectoryName() = LOG_DIRECTORY_NAME
    override fun logFileName() = LOG_FILE_NAME

    /*
        Метод возвращает директорию к jar, текущего приложения
    */
    override fun applicationJarFile(): String {
        val startupUrl = javaClass.protectionDomain.codeSource.location

        var path: Path
        try {
            path = Paths.get(startupUrl.toURI())
        } catch (e: Exception) {
            try {
                path = Paths.get(URL(startupUrl.path).path)
            } catch (ipe: Exception) {
                path = Paths.get(startupUrl.path)
            }

        }

        path = path!!.parent
        return path!!.toAbsolutePath().toString()
    }
}
