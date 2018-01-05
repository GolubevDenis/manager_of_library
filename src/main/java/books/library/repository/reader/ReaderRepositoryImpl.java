package books.library.repository.reader;

import books.library.app.log.MyLogger;
import books.library.model.Book;
import books.library.model.Reader;
import books.library.model.Subscription;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReaderRepositoryImpl implements ReaderRepository{

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    protected MyLogger logger;

    @Override
    public List<String> getValues(String fieldName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        List list = null;
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            list = session.createQuery("SELECT b." + fieldName + " FROM Reader b").list();
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
            Object result = session.createCriteria(Reader.class)
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
    public String add(Reader reader) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        String id = null;
        try {
            id = (String) session.save(reader);
            logger.logInfo("Добавление читателя в таблицу readers с id " + id);
        } catch (Exception e) {
            logger.logError("Ошибка добвление читателя", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return String.valueOf(id);
    }

    @Override
    public void update(Reader reader) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.update(reader);
        } catch (Exception e) {
            logger.logError("Ошибка обновления читателя", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Обновление читателя в таблице readers с id " + reader.getId());
    }

    @Override
    public void remove(Reader reader) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.delete(reader);
        } catch (Exception e) {
            logger.logError("Ошибка удаления читателя из таблицы readers", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Удаление читателя из таблицы readers с id " + reader.getId());
    }

    @Override
    public List<Reader> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List readers = null;
        try {
            readers = session.createQuery("from Reader").setFirstResult(0).setMaxResults(100).list();
            logger.logInfo("Извлечение всех записей из таблицы readers");
        } catch (Exception e) {
            logger.logError("Ошибка извлечения записей", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return readers;
    }

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.createQuery("DELETE FROM Reader").executeUpdate();
        } catch (Exception e) {
            logger.logError("Ошибка очистки таблицы readers", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Очистка таблицы readers завершена");
    }

    public List<Reader> findByField(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List readers = null;
        try {
            if(value.isEmpty())
            readers = session.createQuery("from Reader r where r." + field_name + " like '%" + value + "%'")
                    .setFirstResult(0).setMaxResults(100).list();
            else
                readers = session.createQuery("from Reader r where r." + field_name + " like '%" + value + "%'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение читателя по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return readers;
    }

    public List<Reader> findByValue(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List readers = null;
        try {
            readers = session.createQuery("from Reader r where r." + field_name + " = '" + value + "'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение читателя по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return readers;
    }
}
