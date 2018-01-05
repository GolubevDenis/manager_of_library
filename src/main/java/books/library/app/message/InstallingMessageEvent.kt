package books.library.app.message

class InstallingMessageEvent(source: Any, val isError: Boolean = false) : Message(source)
