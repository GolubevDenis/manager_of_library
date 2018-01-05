package books.library.app.install

interface Installator {

    val isInstalled: Boolean
    fun install(path: String, isCreateLauncherOnDesk: Boolean)
}
