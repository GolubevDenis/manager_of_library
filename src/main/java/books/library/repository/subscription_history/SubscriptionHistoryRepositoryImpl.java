package books.library.repository.subscription_history;

import books.library.app.log.MyLogger;
import books.library.model.Subscription;
import books.library.model.SubscriptionHistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionHistoryRepositoryImpl implements SubscriptionHistoryRepository {

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
            list = session.createQuery("SELECT b." + fieldName + " FROM SubscriptionHistory b").list();
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
            Object result = session.createCriteria(SubscriptionHistory.class)
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
    public List<SubscriptionHistory> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List books = null;
        try {
            books = session.createQuery("from SubsctiprionHistory").list();
            logger.logInfo("Извлечение всех записей из таблицы subscription_history");
        } catch (Exception e) {
            logger.logError("Ошибка извлечения записей", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return books;
    }

    @Override
    public String add(SubscriptionHistory subscription) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        String id = null;
        try {
            id = (String) session.save(subscription);
            logger.logInfo("Добавление книги в таблицу subscription_history с id " + id);
        } catch (Exception e) {
            logger.logError("Ошибка добвление подписки", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return id;
    }

    @Override
    public void remove(SubscriptionHistory subscription) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.delete(subscription);
        } catch (Exception e) {
            logger.logError("Ошибка удаления подписки из таблицы subscription_history", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Удаление подписки из таблицы subscription_history с id " + subscription.getId());
    }

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        try {
            session.createQuery("DELETE FROM SubsctiprionHistory").executeUpdate();
        } catch (Exception e) {
            logger.logError("Ошибка очистки таблицы subscription_history", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        logger.logInfo("Очистка таблицы subscription_history завершена");
    }

    @Override
    public void update(SubscriptionHistory subscription) {
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
        logger.logInfo("Обновление подписки в таблице subscription_history с id " + subscription.getId());
    }

    @Override
    public List<SubscriptionHistory> findByField(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List subscription = null;
        try {
            subscription = session.createQuery("from SubsctiprionHistory r where r." + field_name + " like '%" + value + "%'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение подписки по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return subscription;
    }

    @Override
    public List<SubscriptionHistory> findByValue(String field_name, String value){
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.getTransaction();
        if (!tr.isActive()) {
            tr.begin();
        }
        List subscription = null;
        try {
            subscription = session.createQuery("from SubsctiprionHistory r where r." + field_name + " = '" + value + "'").list();
        }catch (Exception e){
            logger.logInfo("Извлечение подписки по полю " + field_name + " со значением " + value + " не удалось", e);
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return subscription;
    }
}
