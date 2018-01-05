package books.library.controller

import books.library.ViewHolder
import books.library.app.log.MyLogger
import books.library.app.setting.Setting
import books.library.interfaces.UpdateData
import books.library.model.Book
import books.library.service.book.BookService
import books.library.util.fx.UtilFx
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TextFieldTableCell
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import javax.annotation.PostConstruct

class BooksController : UpdateData {

    @Autowired private lateinit var bookService: BookService
    @Autowired private lateinit var logger: MyLogger
    @Autowired private lateinit var setting: Setting
    @Autowired private lateinit var utilFx: UtilFx

    @Qualifier(value = "editBookView")
    @Autowired
    private lateinit var editBookView: ViewHolder

    @FXML lateinit var search_box: ComboBox<*>
    @FXML lateinit var search_field: TextField
    @FXML lateinit var tabel_books: TableView<Book>
    @FXML lateinit var button_books_add: Button
    @FXML lateinit var column_count_pages: TableColumn<Book, String>
    @FXML lateinit var column_name_book: TableColumn<Book, String>
    @FXML lateinit var column_author: TableColumn<Book, String>
    @FXML lateinit var column_year: TableColumn<Book, String>
    @FXML lateinit var column_status: TableColumn<Book, String>

    @FXML lateinit var column_otdel: TableColumn<Book, String>
    @FXML lateinit var column_number: TableColumn<Book, String>
    @FXML lateinit var column_publisher: TableColumn<Book, String>
    @FXML lateinit var column_language: TableColumn<Book, String>
    @FXML lateinit var column_genre: TableColumn<Book, String>
    @FXML lateinit var column_description: TableColumn<Book, String>

    private var stageEditBook: Stage? = null

    @PostConstruct
    fun postConstruct() {
        utilFx.setStyle(search_box)
        createColumns()
        createTable()
        createSearch()
    }

    override fun updateData() {
        createTable()
        updateVisibleColumns()
    }

    private fun updateVisibleColumns() {
        column_count_pages.isVisible = setting.getBooleanProperty("book_colunmt_count_pages")
        column_name_book.isVisible = setting.getBooleanProperty("book_colunmt_title")
        column_author.isVisible = setting.getBooleanProperty("book_colunmt_author")
        column_year.isVisible = setting.getBooleanProperty("book_colunmt_year")
        column_status.isVisible = setting.getBooleanProperty("book_colunmt_status")
        column_otdel.isVisible = setting.getBooleanProperty("book_colunmt_otdel")
        column_number.isVisible = setting.getBooleanProperty("book_colunmt_number")
        column_publisher.isVisible = setting.getBooleanProperty("book_colunmt_publisher")
        column_language.isVisible = setting.getBooleanProperty("book_colunmt_language")
        column_genre.isVisible = setting.getBooleanProperty("book_colunmt_genre")
        column_description.isVisible = setting.getBooleanProperty("book_colunmt_description")
    }

    private fun createSearch() {
        val list = search_box.items as ObservableList<Any>
        list.addAll(
                SEARCH_NAME,
                SEARCH_AUTHOR,
                SEARCH_STATUS,
                SEARCH_YEAR,
                SEARCH_COUNT_PAGES,
                SEARCH_OTDEL,
                SEARCH_NUMBER,
                SEARCH_PUBLISHER,
                SEARCH_GENRE,
                SEARCH_LANGUAGE,
                SEARCH_DESCRIPTION
        )
    }

    @FXML
    private fun search() {
        when (search_box.selectionModel.selectedItem.toString()) {
            SEARCH_NAME -> bookService.findBooksByName(search_field.text)
            SEARCH_AUTHOR -> bookService.findBooksByAuthor(search_field.text)
            SEARCH_STATUS -> bookService.findBooksByStatus(search_field.text)
            SEARCH_YEAR -> bookService.findBooksByYear(search_field.text)
            SEARCH_COUNT_PAGES -> bookService.findBooksByCount_pages(search_field.text)
            SEARCH_OTDEL -> bookService.findBooksByOtdel(search_field.text)
            SEARCH_NUMBER -> bookService.findBooksByNumber(search_field.text)
            SEARCH_PUBLISHER -> bookService.findBooksByPublisher(search_field.text)
            SEARCH_GENRE -> bookService.findBooksByGenre(search_field.text)
            SEARCH_LANGUAGE -> bookService.findBooksByLanguage(search_field.text)
            SEARCH_DESCRIPTION -> bookService.findBooksByDescription(search_field.text)
        }
    }

    private fun createTable() {
        tabel_books.items = bookService.findAll()
    }

    private fun createColumns() {

        val newIntValue = {
            t: TableColumn.CellEditEvent<Book, String>, fieldUpdate: (String)->Unit, message: String ->
            try {
                t.newValue.toInt()
                fieldUpdate(t.newValue)
                bookService.update(t.rowValue)
            }catch (e : Exception){
                fieldUpdate(t.oldValue)
                utilFx.createAndShowAlert(Alert.AlertType.WARNING, message, "Предупреждение", button_books_add.scene.window)
            }
        }

        with(column_count_pages){
            setCellValueFactory { cellData -> cellData.value.count_pagesProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                newIntValue(t, {newV -> t.rowValue.count_pages = newV}, "Количество страниц - это числовое поле")
            }
        }

        with(column_year){
            setCellValueFactory { cellData -> cellData.value.yearProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                newIntValue(t, {newV -> t.rowValue.year = newV}, "Год - это числовое поле")
            }
        }

        with(column_description){
            setCellValueFactory { cellData -> cellData.value.descriptionProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.description = t.newValue
                bookService.update(t.rowValue)
            }
        }


        with(column_number){
            setCellValueFactory { cellData -> cellData.value.numberProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.number = t.newValue
                bookService.update(t.rowValue)
            }

        }

        with(column_publisher){
            setCellValueFactory { cellData -> cellData.value.publisherProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.publisher = t.newValue
                bookService.update(t.rowValue)
            }
        }

        with(column_language){
            setCellValueFactory { cellData -> cellData.value.languageProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.language = t.newValue
                bookService.update(t.rowValue)
            }
        }

        with(column_genre){
            setCellValueFactory { cellData -> cellData.value.genreProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.genre = t.newValue
                bookService.update(t.rowValue)
            }
        }

        with(column_otdel){
            setCellValueFactory { cellData -> cellData.value.otdelProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.otdel = t.newValue
                bookService.update(t.rowValue)
            }
        }

        with(column_name_book){
            setCellValueFactory { cellData -> cellData.value.titleProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.title = t.newValue
                bookService.update(t.rowValue)
            }
        }

        with(column_author){
            setCellValueFactory { cellData -> cellData.value.authorProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.author = t.newValue
                bookService.update(t.rowValue)
            }
        }

        with(column_status){
            setCellValueFactory { cellData -> cellData.value.statusProperty() }
            cellFactory = TextFieldTableCell.forTableColumn()
            setOnEditCommit { t ->
                t.rowValue.status = t.newValue
                bookService.update(t.rowValue)
            }
        }
    }

    /**
     * Метод, вызываемый при нажатии на кнопку "Добавить".
     * Привязан к кнопке в FXML файле представления.
     */
    @FXML
    fun addBook() {
        (editBookView.controller as EditBookController).setRefactor(false)
        createAndShowEditPane("Добавление книги")
    }

    @FXML
    fun refactorBook() {
        (editBookView.controller as EditBookController).setRefactor(true)
        (editBookView.controller as EditBookController).setBook(tabel_books.selectionModel.selectedItem)
        createAndShowEditPane("Редактирование книги")
    }

    @FXML
    private fun deleteBook() {
        logger.logInfo("Удаление книги")
        bookService.remove(tabel_books.selectionModel.selectedItem)
    }

    private fun createAndShowEditPane(title: String) {
        if (stageEditBook == null) {
            stageEditBook = utilFx.createModalStage(title, button_books_add.scene.window)
            stageEditBook!!.scene = utilFx.createStyleScene(editBookView.view)
        }
        stageEditBook!!.show()
    }

    companion object {

        private val SEARCH_NAME = "названию"
        private val SEARCH_AUTHOR = "автору"
        private val SEARCH_STATUS = "статусу"
        private val SEARCH_YEAR = "году"
        private val SEARCH_COUNT_PAGES = "количеству страниц"
        private val SEARCH_OTDEL = "отделу"
        private val SEARCH_NUMBER = "номеру"
        private val SEARCH_PUBLISHER = "издательству"
        private val SEARCH_GENRE = "жанру"
        private val SEARCH_LANGUAGE = "языку"
        private val SEARCH_DESCRIPTION = "описанию"
    }
}
