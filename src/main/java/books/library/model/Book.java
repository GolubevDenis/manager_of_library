package books.library.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "books")
@Entity
public class Book extends Model implements Serializable{

    @Transient private StringProperty title = new SimpleStringProperty();
    @Transient private StringProperty author = new SimpleStringProperty();
    @Transient private StringProperty count_pages = new SimpleStringProperty();
    @Transient private StringProperty year = new SimpleStringProperty();
    @Transient private StringProperty id = new SimpleStringProperty();
    @Transient private StringProperty publisher = new SimpleStringProperty();
    @Transient private StringProperty language = new SimpleStringProperty();
    @Transient private StringProperty genre = new SimpleStringProperty();
    @Transient private StringProperty description = new SimpleStringProperty();
    @Transient private StringProperty status = new SimpleStringProperty(STATUS_IS_NOT_ISSUED);
    @Transient private StringProperty number = new SimpleStringProperty();
    @Transient private StringProperty otdel = new SimpleStringProperty();
    @Transient private ObjectProperty<Subscription> subscription = new SimpleObjectProperty<>();
    @Transient private ObjectProperty<SubscriptionHistory> subscriptionHistory = new SimpleObjectProperty<>();

    public static final String STATUS_ISSUED = "Выдана";
    public static final String STATUS_IS_NOT_ISSUED = "Не выдана";

    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, orphanRemoval=true)
    public Subscription getSubscription() {
        return subscription.get();
    }

    public ObjectProperty<Subscription> subscriptionProperty() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription.set(subscription);
    }

    //

    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, orphanRemoval=true)
    public SubscriptionHistory getSubscriptionHistory() {
        return subscriptionHistory.get();
    }

    public ObjectProperty<SubscriptionHistory> subscriptionHistoryProperty() {
        return subscriptionHistory;
    }

    public void setSubscriptionHistory(SubscriptionHistory subscriptionHistory) {
        this.subscriptionHistory.set(subscriptionHistory);
    }
    //

    @Column(name = "otdel")
    public String getOtdel() {
        return otdel.get();
    }

    public void setOtdel(String otdel) {
        this.otdel.set(otdel);
    }

    @Column(name = "number")
    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    @Column(name = "title")
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @Column(name = "author")
    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    @Column(name = "count_pages")
    public String getCount_pages() {
        return count_pages.get();
    }

    public void setCount_pages(String count_pages) {
        this.count_pages.set(count_pages);
    }

    @Column(name = "year")
    public String getYear() {
        return year.get();
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public String getId() {
        return id.get();
    }


    public void setId(String id) {
        this.id.set(id);
    }

    @Column(name = "publisher")
    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    @Column(name = "language")
    public String getLanguage() {
        return language.get();
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    @Column(name = "genre")
    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    @Column(name = "description")
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Column(name = "status")
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        if (id != book.id) return false;
        else return true;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public StringProperty count_pagesProperty() {
        return count_pages;
    }

    public StringProperty yearProperty() {
        return year;
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty publisherProperty() {
        return publisher;
    }

    public StringProperty languageProperty() {
        return language;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty numberProperty() {
        return number;
    }

    public StringProperty otdelProperty() {
        return otdel;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", count_pages=" + getCount_pages() +
                ", year=" + getYear() +
                ", id=" + getId()+
                ", publisher='" + getPublisher() + '\'' +
                ", language='" + getLanguage() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", otdel='" + getOtdel() + '\'' +
                ", number=" + getNumber() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
