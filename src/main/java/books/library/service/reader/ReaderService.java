package books.library.service.reader;

import books.library.model.Reader;
import books.library.service.Service;
import javafx.collections.ObservableList;

public interface ReaderService extends Service<Reader> {

    ObservableList<Reader> findByFirstName(String value);
    ObservableList<Reader> findByLastName(String value);
    ObservableList<Reader> findByOtchestvo(String value);
    ObservableList<Reader> findByPhone(String value);
    ObservableList<Reader> findByProfession(String value);
    ObservableList<Reader> findByJob(String value);
    ObservableList<Reader> findByEducation(String value);
    ObservableList<Reader> findBySchool(String value);
    ObservableList<Reader> findByAddress(String value);
    ObservableList<Reader> findByPassportSeries(String value);
    ObservableList<Reader> findByNumberPasport(String value);
    ObservableList<Reader> findByWhoAndWhen(String value);
    ObservableList<Reader> findByDateEntry(String value);
    ObservableList<Reader> findByNumber(String text);
}
