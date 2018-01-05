package books.library.repository;

import java.util.List;

public interface Repository<T> {
    String add(T reader);
    void update(T reader);
    void remove(T reader);
    void clear();
    List<T> findAll();
    List<T> findByField(String field_name, String value);
    List<T> findByValue(String field_name, String value);
    int getCountRows();
    List<String> getValues(String fieldName);
}
