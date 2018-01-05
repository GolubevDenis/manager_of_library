package books.library.app.message

import org.springframework.context.ApplicationEvent

open class Message(source: Any) : ApplicationEvent(source) {
    var message: String? = null
}
