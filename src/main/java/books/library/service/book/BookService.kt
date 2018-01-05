package books.library.service.book

import books.library.model.Book
import books.library.service.Service
import javafx.collections.ObservableList

interface BookService : Service<Book> {

    val titles: List<String>
    val authors: List<String>

    fun findBooksByName(name: String): ObservableList<Book>
    fun findBooksByAuthor(author: String): ObservableList<Book>
    fun findBooksByCount_pages(count_pages: String): ObservableList<Book>
    fun findBooksByYear(year: String): ObservableList<Book>
    fun findBooksByPublisher(publisher: String): ObservableList<Book>
    fun findBooksByLanguage(language: String): ObservableList<Book>
    fun findBooksByGenre(genre: String): ObservableList<Book>
    fun findBooksByDescription(description: String): ObservableList<Book>
    fun findBooksByNumber(number: String): ObservableList<Book>
    fun findBooksByStatus(status: String): ObservableList<Book>
    fun findBooksByOtdel(otdel: String): ObservableList<Book>
    fun findBuNumberOtdelAuthorTitle(number: String, otdel: String, author: String, title: String): Book?
}
