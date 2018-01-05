package books.library.app.file

import java.io.File
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException

interface ProjectFilesManager {

    fun fontsDirectoryFile(): File
    fun fontFile(): File
    fun listNameNeededFiles(): Array<String>
    fun logFile(): File
    fun logPropertiesFile(): File
    fun installFile(): File
    fun dataBaseFile(): File
    fun settingFile(): File

    /*
            Метод возвращает директорию к jar, текущего приложения
    */
    fun applicationJarFile(): String

    fun imagesDirectoryName(): String
    fun dataDirectoryName(): String
    fun reportDirectoryName(): String
    fun installFileName(): String
    fun logPropertiesFileName(): String
    fun dataBaseNameFile(): String
    fun logDirectoryName(): String
    fun logFileName(): String

    fun fontFileByName(name: String): File
    fun imageFileByName(name: String): File
    fun reportFileByName(name: String): File
}
