package books.library.app.install

import books.library.app.file.ProjectFilesManager
import books.library.app.log.MyLogger
import books.library.app.message.InstallingMessageEvent
import books.library.util.file.UtilFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@Lazy
@Component
class Installer : Installator {

    @Autowired private lateinit var logger: MyLogger
    @Autowired private lateinit var fileHelper: UtilFile
    @Autowired private lateinit var projectFilesManager: ProjectFilesManager

    companion object {
        //переключает аппендер логгера на папку logs
        private val LOGS_PATH_PROPERTY = "log4j.appender.file.File"
        private val COMMANT_FOR_LAUNCHER_BAR = "start javaw -jar "
        private val JAR_NAME = "book_manager.jar"
    }

    /*
        Метод проверяет, была ли установлена программа ранее
    */
    override val isInstalled: Boolean
        get() {
            try {
                val path = fileHelper.readFile(projectFilesManager.installFile())
                if (path.isEmpty()) {
                    return false
                } else {
                    val file = File(path)
                    if (file.exists()) {
                        val listExistsFiles = file.list()
                        return projectFilesManager.listNameNeededFiles().all { listExistsFiles.contains(it) }
                    }
                    return true
                }
            } catch (e: Exception) {
                logger.logError("Ошибка при проверке на установленность", e)
            }
            return false
        }

    override fun install(path: String, isCreateLauncherOnDesk: Boolean) {
        logger.logInfoAndMessage("Начинается процесс установки приложения...",
                InstallingMessageEvent(this))

        connectLogger(path)
        createInstalledFile(path)

        copyNeededFiles(path)
        if (isCreateLauncherOnDesk) {
            createBatLauncher(path)
        }
        logger.logInfoAndMessage("Завершение установки",
                InstallingMessageEvent(this))
    }

    private fun createInstalledFile(path: String) {
        try {
            val installedFile = projectFilesManager.installFile()
            installedFile.createNewFile()
            fileHelper.writeToFile(installedFile, path)
        } catch (e: Exception) {
            logger.logError("Ошибка создания файла инсталляции", e)
        }

    }

    private fun copyNeededFiles(path: String) {
        logger.logInfoAndMessage("Копирование файлов в " + path,
                InstallingMessageEvent(this))
        try {
            fileHelper.copyFilesFromDirectory(projectFilesManager.applicationJarFile(), path)
        } catch (e: IOException) {
            logger.logErrorAndMessage("Ошибка при копировании файлов в " + path, e,
                    InstallingMessageEvent(this, true))
        }

        logger.logInfoAndMessage("Копирование файлов в $path завершено",
                InstallingMessageEvent(this))
    }

    private fun connectLogger(pathRootDir: String) {
        logger.logInfoAndMessage("Настройка логирования...",
                InstallingMessageEvent(this))
        val path = pathRootDir + "/" + projectFilesManager.logDirectoryName() + File.separator + projectFilesManager.logDirectoryName()
        logger.logInfoAndMessage("Логирование в " + path,
                InstallingMessageEvent(this))
        val properties = Properties()
        var fileInputStream: FileInputStream? = null
        try {
            val logFile = projectFilesManager.logPropertiesFile()
            fileInputStream = FileInputStream(logFile)
            properties.load(fileInputStream)
            properties.setProperty(LOGS_PATH_PROPERTY, path)
            properties.store(FileOutputStream(logFile), "")
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close()
                } catch (e: IOException) {
                    logger.logError("Ошибка закрытия потока чтения", e)
                }

            }
        }
        logger.logInfoAndMessage("Настройка логирования завершена",
                InstallingMessageEvent(this))
    }

    //Создает лаунчер bat на рабочем столе
    private fun createBatLauncher(path: String) {
        logger.logInfoAndMessage("Создание лаунчера на рабочем столе...",
                InstallingMessageEvent(this))
        val file = File(System.getProperty("user.home") + "/Desktop")
        val bat = File(file, "Library Books Manager.bat")
        try {
            bat.createNewFile()
        } catch (e: IOException) {
            logger.logErrorAndMessage("Ошибка в создание bat launcher",
                    InstallingMessageEvent(this, true))
        }

        fileHelper.writeToFile(bat, COMMANT_FOR_LAUNCHER_BAR + path + "\\" + JAR_NAME)
        logger.logInfoAndMessage("Создание лаунчера на рабочем столе завершено...",
                InstallingMessageEvent(this))

    }
}
