package books.library.service.reader;

import books.library.model.Book;
import books.library.model.Reader;
import books.library.repository.reader.ReaderRepository;
import books.library.util.collections.CollectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository repository;

    @Override
    public int getCountRows() {
        return repository.getCountRows();
    }

    @Autowired
    private CollectionUtil collectionUtil;

    private ObservableList<Reader> data;

    @Override
    public void add(Reader reader) {
        reader.setId(repository.add(reader));
        data.add(reader);
    }

    @Override
    public void update(Reader reader) {
        repository.update(reader);
    }

    @Override
    public void remove(Reader reader) {
        repository.remove(reader);
        collectionUtil.deleteModelFromList(data, reader);
    }

    @Override
    public ObservableList<Reader> findAll() {
        List<Reader> readers = repository.findAll();
        data = FXCollections.observableArrayList(readers);
        return data;
    }

    @Override
    public Reader findById(String id) {
        return repository.findByValue("id", id).get(0);
    }

    @Override
    public ObservableList<Reader> findByFirstName(String first_name) {
        data.clear();
        data.addAll(repository.findByField("first_name", first_name));
        return data;
    }

    @Override
    public ObservableList<Reader> findByLastName(String last_name) {
        data.clear();
        data.addAll(repository.findByField("last_name", last_name));
        return data;
    }

    @Override
    public ObservableList<Reader> findByOtchestvo(String otchestvo) {
        data.clear();
        data.addAll(repository.findByField("otchestvo", otchestvo));
        return data;
    }

    @Override
    public ObservableList<Reader> findByPhone(String phone) {
        data.clear();
        data.addAll(repository.findByField("phone", phone));
        return data;
    }

    @Override
    public ObservableList<Reader> findByProfession(String profession) {
        data.clear();
        data.addAll(repository.findByField("profession", profession));
        return data;
    }

    @Override
    public ObservableList<Reader> findByJob(String job) {
        data.clear();
        data.addAll(repository.findByField("job", job));
        return data;
    }

    @Override
    public ObservableList<Reader> findByEducation(String education) {
        data.clear();
        data.addAll(repository.findByField("education", education));
        return data;
    }

    @Override
    public ObservableList<Reader> findBySchool(String school) {
        data.clear();
        data.addAll(repository.findByField("school", school));
        return data;
    }

    @Override
    public ObservableList<Reader> findByAddress(String address) {
        data.clear();
        data.addAll(repository.findByField("address", address));
        return data;
    }

    @Override
    public ObservableList<Reader> findByPassportSeries(String passport_series) {
        data.clear();
        data.addAll(repository.findByValue("passport_series", passport_series));
        return data;
    }

    @Override
    public ObservableList<Reader> findByNumberPasport(String number_pasport) {
        data.clear();
        data.addAll(repository.findByValue("number_pasport", number_pasport));
        return data;
    }

    @Override
    public ObservableList<Reader> findByWhoAndWhen(String who_and_when) {
        data.clear();
        data.addAll(repository.findByField("who_and_when", who_and_when));
        return data;
    }

    @Override
    public ObservableList<Reader> findByDateEntry(String date_entry) {
        data.clear();
        data.addAll(repository.findByField("date_entry", date_entry));
        return data;
    }

    @Override
    public ObservableList<Reader> findByNumber(String number) {
        data.clear();
        data.addAll(repository.findByField("number", number));
        return data;
    }
}
