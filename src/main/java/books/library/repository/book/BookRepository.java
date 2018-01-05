package books.library.repository.book;

import books.library.model.Book;
import books.library.repository.Repository;

import java.util.List;

public interface BookRepository extends Repository<Book>{
    Book findByFourValues(String paramName1, String value1,
                          String paramName2, String value2,
                          String paramName3, String value3,
                          String paramName4, String value4);
}
