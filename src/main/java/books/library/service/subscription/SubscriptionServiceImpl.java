package books.library.service.subscription;

import books.library.app.log.MyLogger;
import books.library.model.Subscription;
import books.library.model.SubscriptionHistory;
import books.library.repository.subscription_history.SubscriptionHistoryRepository;
import books.library.repository.subsctiprion.SubscriptionRepository;
import books.library.service.subscription_history.SubscriptionHistoryService;
import books.library.util.collections.CollectionUtil;
import books.library.util.date.UtilDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private SubscriptionHistoryService historyService;

    @Autowired
    private CollectionUtil collectionUtil;

    private ObservableList<Subscription> data = FXCollections.observableArrayList();

    @Autowired
    private UtilDate utilDate;

    @Autowired
    private MyLogger logger;

    @Override
    public int getCountRows() {
        return repository.getCountRows();
    }

    @Override
    public ObservableList<Subscription> insertData(List<Subscription> list) {
        data.clear();
        data.addAll(list);
        return data;
    }

    @Override
    public ObservableList findExpired() {
        ObservableList<Subscription> list = FXCollections.observableArrayList();
        Date now = new Date();
        for(Subscription subscription : repository.findAll()){
            try{
                if(utilDate.compareDates(now, subscription.getDate_return())){
                    list.add(subscription);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public Map<String, Number> getCountSubscriptionByDates(Date date1, Date date2){
        logger.logInfo("SELECT CountSubscriptionByDates " + date1 + "   " + date2);
        return repository.getCountSubscriptionByDates(date1, date2);
    }

    @Override
    public void add(Subscription subscription) {
        subscription.setId(repository.add(subscription));
        data.add(subscription);
    }

    @Override
    public void update(Subscription subscription) {
        repository.update(subscription);
    }

    @Override
    public void remove(Subscription subscription) {
        historyService.add(new SubscriptionHistory(subscription));
        repository.remove(subscription);
        collectionUtil.deleteModelFromList(data, subscription);
        collectionUtil.deleteModelFromList(subscription.getReader().getSubscriptions(), subscription);
    }

    @Override
    public ObservableList<Subscription> findAll() {
        data = FXCollections.observableArrayList();
        data.addAll(repository.findAll());
        return data;
    }

    @Override
    public Subscription findById(String id) {
        return repository.findByValue("id", id).get(0);
    }
}
