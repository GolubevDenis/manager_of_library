package books.library.service.book

import books.library.model.Book
import books.library.repository.book.BookRepository
import books.library.util.collections.CollectionUtil
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class BookServiceImpl : BookService {

    @Autowired private lateinit var repository: BookRepository
    @Autowired private val collectionUtil: CollectionUtil? = null

    //Данные кешируются в этих коллекциях для автозаполнения полей
    override var titles: ObservableList<String> = FXCollections.observableArrayList()
    override var authors: ObservableList<String> = FXCollections.observableArrayList()

    //хранит все книги, отображаемые на главном экране
    private var data: ObservableList<Book> = FXCollections.observableArrayList()

    @PostConstruct
    private fun postConstruct(){
        titles.addAll(repository.getValues("title"))
        authors.addAll(repository.getValues("author"))
    }


    //метод добавляет, если необходимо обновленные данные в кеш (titles, authors)
    private fun addRequired(book: Book) {
        titles.add(book.title)
        authors.add(book.author)
    }

    override fun findAll(): ObservableList<Book> {
        data.addAll(repository.findAll())
        return data
    }

    override fun getCountRows(): Int {
        return repository.countRows
    }

    override fun add(book: Book) {
        book.id = repository.add(book)
        data.add(book)
        addRequired(book)
    }

    override fun update(book: Book) {
        repository.update(book)
    }

    override fun remove(book: Book) {
        repository.remove(book)
        collectionUtil!!.deleteModelFromList(data, book)
    }

    override fun findById(id: String): Book {
        return repository.findByValue("id", id)[0]
    }

    override fun findBooksByName(name: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("title", name))
        return data
    }

    override fun findBooksByAuthor(author: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("author", author))
        return data
    }

    override fun findBooksByCount_pages(count_pages: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByValue("count_pages", count_pages))
        return data
    }

    override fun findBooksByYear(year: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByValue("year", year))
        return data
    }

    override fun findBooksByPublisher(publisher: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("publisher", publisher))
        return data
    }

    override fun findBooksByLanguage(language: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("language", language))
        return data
    }

    override fun findBooksByGenre(genre: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("genre", genre))
        return data
    }

    override fun findBooksByDescription(description: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("description", description))
        return data
    }

    override fun findBooksByNumber(number: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByValue("number", number))
        return data
    }

    override fun findBooksByStatus(status: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByField("status", status))
        return data
    }

    override fun findBooksByOtdel(otdel: String): ObservableList<Book> {
        data.clear()
        data.addAll(repository.findByValue("otdel", otdel))
        return data
    }

    override fun findBuNumberOtdelAuthorTitle(number: String, otdel: String, author: String, title: String): Book {
        return repository.findByFourValues(
                "number", number,
                "otdel", otdel,
                "author", author,
                "title", title)
    }
}
