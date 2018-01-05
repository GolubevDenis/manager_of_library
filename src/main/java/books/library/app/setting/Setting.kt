package books.library.app.setting

import java.io.FileNotFoundException
import java.io.IOException

interface Setting {

    fun getBooleanProperty(nameProperty: String): Boolean
    fun setProperty(nameProperty: String, property: String)
    fun setProperty(nameProperty: String, property: Boolean)
}
