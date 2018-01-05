package books.library.service;

import books.library.model.Book;
import javafx.collections.ObservableList;

import java.util.List;

public interface Service<T> {

    void add(T t);
    void update(T t);
    void remove(T t);
    ObservableList<T> findAll();
    T findById(String id);
    int getCountRows();
}
