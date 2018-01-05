package books.library.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Date;

@Table(name = "subscription")
@Entity
public class Subscription extends Model {

    @Transient private ObjectProperty<Book> book = new SimpleObjectProperty<>();
    @Transient private ObjectProperty<Reader> reader = new SimpleObjectProperty<>();
    @Transient private StringProperty date_return = new SimpleStringProperty();
    @Transient private StringProperty id = new SimpleStringProperty();

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    @ManyToOne()
    @JoinColumn(name = "reader_id")
    public Reader getReader() {
        return reader.get();
    }

    public ObjectProperty<Reader> readerProperty() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader.set(reader);
    }

    @OneToOne()
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public ObjectProperty<Book> bookProperty() {
        return book;
    }

    public String getDate_return() {
        return date_return.get();
    }

    public StringProperty date_returnProperty() {
        return date_return;
    }


    public void setBook(Book book) {
        this.book.set(book);
    }

    public void setDate_return(String date_return) {
        this.date_return.set(date_return);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                ", date_return=" + date_return +
                '}';
    }
}
