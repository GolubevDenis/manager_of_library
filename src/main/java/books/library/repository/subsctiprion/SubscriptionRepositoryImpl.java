package books.library.repository.subsctiprion;

import books.library.app.log.MyLogger;
import books.library.model.DateAndCount;
import books.library.model.Subscription;
import books.library.util.date.UtilDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Transformer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MyLogger logger;

    @Autowired
    private UtilDate utilDate;

    @Override
    public Map<String, Number> getCountSubscriptionByDates(Date date1, Date date2){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        Map<String, Number> map = new TreeMap<>();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            for(Object o : session.createQuery("SELECT s.date FROM Subscription s WHERE s.date " +
                    "BETWEEN " + date1.getTime() + " AND " + date2.getTime() + " GROUP BY s.date").list()){
                Date date = new Date(((Timestamp) o).getTime());

                map.put(utilDate.getDateText(date), (Number)
                        session.createQuery("SELECT COUNT(s.id) FROM Subscription s WHERE s.date = " + date.getTime()).uniqueResult());
            };
        } catch (Exception e) {
            logger.logError("Ошибка извлечения количества полей по датам поля ", e);
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("(REPOSITORY) SELECT CountSubscriptionByDates " + date1 + " " + date2 + "   data.size = " + map.size());
        return map;
    }

    @Override
    public List<String> getValues(String fieldName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        List list = null;
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            list = session.createQuery("SELECT b." + fieldName + " FROM Subscription b").list();
        } catch (Exception e) {
            logger.logError("Ошибка извлечения всех значений поля " + fieldName, e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return list;
    }

    @Override
    public List<Subscription> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List books = null;
        try {
            books = session.createQuery("from Subscription").list();
            logger.logInfo("Извлечение всех записей из таблицы subscriptions");
        } catch (Exception e) {
            logger.logError("Ошибка извлечения записей", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return books;
    }

    @Override
    public int getCountRows(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            Object result = session.createCriteria(Subscription.class)
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
    public String add(Subscription subscription) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        String id = null;
        try {
            subscription.setDate(utilDate.parseDate(utilDate.getDateText(new Date())));
            id = (String) session.save(subscription);
            logger.logInfo("Добавление книги в таблицу subscriptions с id " + id);
        } catch (Exception e) {
            logger.logError("Ошибка добвление подписки", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return id;
    }

    @Override
    public void remove(Subscription subscription) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.delete(subscription);
        } catch (Exception e) {
            logger.logError("Ошибка удаления подписки из таблицы subscriptions", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Удаление подписки из таблицы subscriptions с id " + subscription.getId());
    }

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.createQuery("DELETE FROM Subscription").executeUpdate();
        } catch (Exception e) {
            logger.logError("Ошибка очистки таблицы subscriptions", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Очистка таблицы subscriptions завершена");
    }

    @Override
    public void update(Subscription subscription) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.update(subscription);
        } catch (Exception e) {
            logger.logError("Ошибка обновления подписки", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Обновление подписки в таблице subscriptions с id " + subscription.getId());
    }

    @Override
    public List<Subscription> findByField(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List subscription = null;
        try {
            subscription = session.createQuery("from Subscription r where r." + field_name + " like '%" + value + "%'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение подписки по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return subscription;
    }

    @Override
    public List<Subscription> findByValue(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List subscription = null;
        try {
            subscription = session.createQuery("from Subscription r where r." + field_name + " = '" + value + "'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение подписки по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return subscription;
    }
}
