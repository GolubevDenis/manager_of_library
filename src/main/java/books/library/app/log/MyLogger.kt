package books.library.app.log

import books.library.app.message.Message
import org.springframework.context.ApplicationContextAware

interface MyLogger : ApplicationContextAware {

    fun logInfo(message: String)
    fun logWarn(message: String)
    fun logError(message: String)

    fun logInfo(message: String, e: Exception)
    fun logWarn(message: String, e: Exception)
    fun logError(message: String, e: Exception)

    fun logInfoAndMessage(message: String, m: Message)
    fun logWarnAndMessage(message: String, m: Message)
    fun logErrorAndMessage(message: String, m: Message)

    fun logInfoAndMessage(message: String, e: Exception, m: Message)
    fun logWarnAndMessage(message: String, e: Exception, m: Message)
    fun logErrorAndMessage(message: String, e: Exception, m: Message)
}
