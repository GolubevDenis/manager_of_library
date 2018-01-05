package books.library.app.log

import books.library.app.file.ProjectFilesManager
import books.library.app.message.Message
import books.library.util.file.UtilFile
import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.impl.Log4jLoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.util.Properties

@Lazy
@Component
class Log : MyLogger {

    @Autowired
    private val l: Logger? = null

    @Autowired
    private val projectFilesManager: ProjectFilesManager? = null

    private var context: ApplicationContext? = null

    @PostConstruct
    fun post() {
        try {
            val logFile = projectFilesManager!!.logPropertiesFile()
            val p = Properties()
            p.load(FileInputStream(logFile))
            PropertyConfigurator.configure(p)
            l!!.info("Настройки логирования загружены!")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun logInfo(message: String) {
        l!!.info(message)
    }

    override fun logWarn(message: String) {
        l!!.warn(message)
    }

    override fun logError(message: String) {
        l!!.error(message)
    }

    override fun logInfo(message: String, e: Exception) {
        l!!.info(message, e)
    }

    override fun logWarn(message: String, e: Exception) {
        l!!.warn(message, e)
    }

    override fun logError(message: String, e: Exception) {
        l!!.error(message, e)
    }

    override fun logInfoAndMessage(message: String, m: Message) {
        l!!.info(message)
        m.message = message
        context!!.publishEvent(m)
    }

    override fun logWarnAndMessage(message: String, m: Message) {
        l!!.warn(message)
        m.message = message
        context!!.publishEvent(m)
    }

    override fun logErrorAndMessage(message: String, m: Message) {
        l!!.error(message)
        m.message = message
        context!!.publishEvent(m)
    }

    override fun logInfoAndMessage(message: String, e: Exception, m: Message) {
        l!!.info(message, e)
        m.message = message
        context!!.publishEvent(m)
    }

    override fun logWarnAndMessage(message: String, e: Exception, m: Message) {
        l!!.warn(message, e)
        m.message = message
        context!!.publishEvent(m)
    }

    override fun logErrorAndMessage(message: String, e: Exception, m: Message) {
        l!!.error(message, e)
        m.message = message
        context!!.publishEvent(m)
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.context = applicationContext
    }
}

