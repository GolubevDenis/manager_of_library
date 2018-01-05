package books.library.controller

import books.library.interfaces.UpdateData
import books.library.model.Book
import books.library.model.Reader
import books.library.model.Subscription
import books.library.service.book.BookService
import books.library.service.subscription.SubscriptionService
import books.library.util.date.UtilDate
import books.library.validation.StandartValidator
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.controlsfx.control.textfield.TextFields
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import java.util.*

class EditSubscriptionController:UpdateData {

    @FXML private lateinit var date_return:DatePicker
    @FXML private lateinit var number:TextField
    @FXML private lateinit var title:TextField
    @FXML private lateinit var author:TextField
    @FXML private lateinit var otdel:TextField

    @Autowired private lateinit var bookService:BookService
    @Autowired private lateinit var utilDate:UtilDate
    @Autowired private lateinit var subscriptionService:SubscriptionService

    private val fieldsList = ArrayList<Node>()
    private var error_message:String? = null

    private var countTitles = 0
    private var countAuthors = 0

    private var subscription:Subscription? = null
    var reader:Reader? = null
    private var refactor:Boolean = false

    @PostConstruct
    fun init() {
        createDownClickListener()
    }
    override fun updateData() {
        if (countTitles < bookService.titles.size) {
            TextFields.bindAutoCompletion(title, bookService.titles)
            countTitles = bookService.titles.size
        }
        if (countAuthors < bookService.authors.size) {
            TextFields.bindAutoCompletion(author, bookService.authors)
            countAuthors = bookService.authors.size
        }
    }

    private fun createDownClickListener() {
    fieldsList.add(date_return)
    fieldsList.add(number)
    fieldsList.add(otdel)
    fieldsList.add(author)
    fieldsList.add(title)

    for (i in fieldsList.indices)
    {
    fieldsList[i].addEventHandler(KeyEvent.KEY_PRESSED ){ event ->
                if (event.code == KeyCode.DOWN) {
                    var t = i + 1
                    if (t >= fieldsList.size) t = 0
                    fieldsList[t].requestFocus()
                }
            }
    }
    }

    @FXML
    private fun save() {
        if (validation()) {
            if (refactor) {
            subscription!!.date_return = date_return.editor.text
            subscription!!.book.title = title.text
            subscription!!.book.author = author.text
            subscription!!.book.otdel = otdel.text
            subscription!!.book.number = number.text
            bookService.update(subscription!!.book)
            subscriptionService.update(subscription)
            } else {
                val newSub = Subscription()
                val book = bookService.findBuNumberOtdelAuthorTitle(number.text, otdel.text, author.text, title.text)
                newSub.reader = reader
                newSub.date_return = date_return.editor.text
                if (book == null) {
                    val newBook = Book()
                    newBook.number = number.text
                    newBook.otdel = otdel.text
                    newBook.author = author.text
                    newBook.title = title.text
                    newBook.status = Book.STATUS_ISSUED
                    if (showAddBook(newBook)) {
                        newSub.book = newBook
                    } else {
                        showAlert()
                    }
                } else {
                    newSub.book = book
                    newSub.book.title = title.text
                    newSub.book.author = author.text
                    newSub.book.otdel = otdel.text
                    newSub.book.number = number.text
                    newSub.book.status = Book.STATUS_ISSUED
                }
                bookService.update(newSub.book)
                subscriptionService.add(newSub)
                reader!!.subscriptions.add(newSub)
            }
            close()
        }
        else {
            showErrorValidation()
        }
    }

    private fun showErrorValidation() {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "Ошибка"
        alert.headerText = "Некоректные данные"
        alert.contentText = error_message
        alert.show()
    }

    private fun validation():Boolean {
        val date = utilDate.parseDate(date_return.editor.text)
        if (date == null) {
            error_message = "Некоректная дата возврата"
            return false
        }
        else if (!utilDate.compareDates(date, Date())) {
            error_message = "Дата возврата книги должна быть позднее нынешней даты"
            return false
        }
        return true
    }



    private fun showAlert() {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "Ошибка"
        alert.headerText = "Невозможно выдать несуществующую книгу"
        alert.showAndWait()
    }

    private fun showAddBook(book:Book):Boolean {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.headerText = "Этой книги еще нет в базе"
        alert.contentText = "Добавить ее в базу данных?"

        val ok = ButtonType("Да")
        val no = ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE)
        alert.buttonTypes.clear()
        alert.buttonTypes.addAll(ok, no)

        val optionalButtonType = alert.showAndWait()

        if (optionalButtonType.get() == ok) {
            bookService.add(book)
            return true
        }
        return false
    }

    @FXML
    private fun close() {
        otdel.scene.window.hide()
    }

    fun setSubscription(subscription:Subscription) {
        this.subscription = subscription

        date_return.editor.text = subscription.date_return
        number.text = subscription.book.number
        title.text = subscription.book.title
        author.text = subscription.book.author
        otdel.text = subscription.book.otdel
    }

    fun setRefactor(refactor:Boolean) {
        this.refactor = refactor
        //если это не редактирование - указываем в качестве текста в поле date_return текущую дату
        //обнуляем значения полей
        if (!refactor) {
            date_return.editor.text = utilDate.getDateText(Date())
            number.text = ""
            title.text = ""
            author.text = ""
            otdel.text = ""
        }
    }

    @FXML
    private fun addDay() {
        date_return.editor.text = utilDate.getDatePlusOrMinusDay(date_return.editor.text, true)
    }

    @FXML
    private fun addWeek() {
        date_return.editor.text = utilDate.getDatePlusOrMinusWeek(date_return.editor.text, true)
    }

    @FXML
    private fun addMounth() {
        date_return.editor.text = utilDate.getDatePlusOrMinusMonth(date_return.editor.text, true)
    }

}
