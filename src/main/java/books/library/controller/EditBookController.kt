package books.library.controller

import books.library.model.Book
import books.library.service.book.BookService
import books.library.util.fx.UtilFx
import javafx.fxml.FXML
import javafx.scene.control.*
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

class EditBookController {

    @FXML private lateinit var edit_title:TextField
    @FXML private lateinit var edit_author:TextField
    @FXML private lateinit var edit_publisher:TextField
    @FXML private lateinit var edit_year:TextField
    @FXML private lateinit var edit_genre:TextField
    @FXML private lateinit var edit_count_page:TextField
    @FXML private lateinit var edit_language:TextField
    @FXML private lateinit var box_status:ComboBox<String>
    @FXML private lateinit var edit_number:TextField
    @FXML private lateinit var edit_otdel:TextField
    @FXML private lateinit var edit_description:TextArea

    @FXML private lateinit var button_save:Button

    @Autowired private lateinit var bookService:BookService
    @Autowired private lateinit var utilFx: UtilFx

    //для редактирование
    private var book:Book? = null
    //true - означает, что окно открыто с целью редактирования книги, false - с целью добавления
    private var refactor:Boolean = false

    private var status = Book.STATUS_IS_NOT_ISSUED
    private lateinit var error_validation:String

    @PostConstruct
    private fun initValues() {
        createBoxStatus()
        createDownClickListener()
    }

    private fun createBoxStatus(){
        box_status.items.addAll(Book.STATUS_IS_NOT_ISSUED, Book.STATUS_ISSUED)
        box_status.selectionModel.selectedItemProperty()
                .addListener{ observable, oldValue, newValue -> status = newValue }
    }

    private fun createDownClickListener() {
        utilFx.arrowClickFocusListener(edit_title
                ,edit_author
                ,edit_genre
                ,edit_year
                ,edit_publisher
                ,edit_count_page
                ,edit_language
                ,box_status
                ,edit_number
                ,edit_otdel
                ,edit_description)
    }

    private fun validation():Boolean {
        val count_page = edit_count_page.text
        if (!count_page.isEmpty()) {
            try {
                count_page.toInt()
            } catch (e:Exception) {
                error_validation = "Количество страниц должно быть числом"
                return false
            }
        }

        val year = edit_year.text
        if (!year.isEmpty()) {
            try {
                year.toInt()
            } catch (e:Exception) {
                error_validation = "Год должен быть числом быть числом"
                return false
            }
        }
        return true
    }

    @FXML
    private fun save() {
        if (validation()) {
            val thisBook = if(refactor) book else Book()
            thisBook?.let {
                it.title = edit_title.text
                it.author = edit_author.text
                it.publisher = edit_publisher.text
                it.year = edit_year.text
                it.genre = edit_genre.text
                it.count_pages = edit_count_page.text
                it.language = edit_language.text
                it.number = edit_number.text
                it.otdel = edit_otdel.text
                it.description = edit_description.text
                it.status = status

                if(refactor) bookService.update(thisBook)
                else bookService.add(thisBook)
            }
            button_save.scene.window.hide()
        } else {
            utilFx.createAndShowAlert(Alert.AlertType.WARNING, error_validation,"Предупреждение", button_save.scene.window)
        }
    }

    fun setRefactor(refactor:Boolean) {
        this.refactor = refactor
    }

    fun setBook(book:Book) {
        this.book = book

        //инициализируем значения для редактирования
        if (refactor) {
            edit_title.text = book.title
            edit_author.text = book.author
            edit_publisher.text = book.publisher
            edit_year.text = book.year.toString()
            edit_genre.text = book.genre
            edit_count_page.text = book.count_pages.toString()
            edit_language.text = book.language
            edit_number.text = book.number.toString()
            edit_otdel.text = book.otdel
            edit_description.text = book.description
            box_status.selectionModel.select(book.status)
        }
    }

    @FXML
    private fun close() {
        edit_author.scene.window.hide()
    }
}
