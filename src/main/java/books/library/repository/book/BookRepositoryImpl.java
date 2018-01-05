package books.library.repository.book;

import books.library.app.log.MyLogger;
import books.library.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MyLogger logger;

    @Override
    public List<String> getValues(String fieldName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        List list = null;
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            list = session.createQuery("SELECT b." + fieldName + " FROM Book b").list();
        } catch (Exception e) {
            logger.logError("Ошибка извлечения всех значений поля " + fieldName, e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return list;
    }

    @Override
    public int getCountRows(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            Object result = session.createCriteria(Book.class)
                    .setProjection(Projections.rowCount()).uniqueResult();
            return Integer.parseInt(result.toString());
        } catch (Exception e) {
            logger.logError("Ошибка извлечения количества записей записей", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return 0;
    }

    @Override
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List books = null;
        try {
            books = session.createQuery("from Book").setFirstResult(0).setMaxResults(100).list();
            logger.logInfo("Извлечение всех записей из таблицы books");
        } catch (Exception e) {
            logger.logError("Ошибка извлечения записей", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return books;
    }

    @Override
    public String add(Book book) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        String id = null;
        try {
            id = (String) session.save(book);
            logger.logInfo("Добавление книги в таблицу books с id " + id);
        } catch (Exception e) {
            logger.logError("Ошибка добвление книги", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return id;
    }

    @Override
    public void remove(Book book) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.delete(book);
        } catch (Exception e) {
            logger.logError("Ошибка удаления книги из таблицы books", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Удаление книги из таблицы books с id " + book.getId());
    }

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.createQuery("DELETE FROM Book").executeUpdate();
        } catch (Exception e) {
            logger.logError("Ошибка очистки таблицы books", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Очистка таблицы books завершена");
    }

    @Override
    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.update(book);
        } catch (Exception e) {
            logger.logError("Ошибка обновления книги", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Обновление книги в таблице books с id " + book.getId());
    }

    @Override
    public List<Book> findByField(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List books = null;
        try {
            if(value.isEmpty())
                books = session.createQuery("from Book r where r." + field_name + " like '%" + value + "%'")
                        .setFirstResult(0).setMaxResults(100).list();
            else
                books = session.createQuery("from Book r where r." + field_name + " like '%" + value + "%'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение книги по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return books;
    }

    @Override
    public List<Book> findByValue(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List books = null;
        try {
            books = session.createQuery("from Book r where r." + field_name + " = '" + value + "'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение книги по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return books;
    }

    @Override
    public Book findByFourValues(String paramName1, String value1,
                                 String paramName2, String value2,
                                 String paramName3, String value3,
                                 String paramName4, String value4) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        Book book = null;
        try {
            book = (Book) session.createQuery("from Book r where r." + paramName1 + " = '" + value1
                    + "' and r." + paramName2 + " = '" + value2
                    + "' and r." + paramName3 + " = '" + value3
                    + "' and r." + paramName4 + " = '" + value4 + "'").uniqueResult();
        }catch (Exception e){
            logger.logInfo("Извлечение читателя по полям не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return book;
    }
}
