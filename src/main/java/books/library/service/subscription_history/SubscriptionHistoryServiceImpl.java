package books.library.service.subscription_history;

import books.library.model.Subscription;
import books.library.model.SubscriptionHistory;
import books.library.repository.subscription_history.SubscriptionHistoryRepository;
import books.library.repository.subsctiprion.SubscriptionRepository;
import books.library.util.collections.CollectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionHistoryServiceImpl implements SubscriptionHistoryService {

    @Autowired
    private SubscriptionHistoryRepository repository;

    @Autowired
    private CollectionUtil collectionUtil;

    private ObservableList<SubscriptionHistory> data = FXCollections.observableArrayList();

    @Override
    public int getCountRows() {
        return repository.getCountRows();
    }

    @Override
    public ObservableList<SubscriptionHistory> insertData(List<SubscriptionHistory> list) {
        data.clear();
        data.addAll(list);
        return data;
    }

    @Override
    public void add(SubscriptionHistory subscription) {
        subscription.setId(repository.add(subscription));
        data.add(subscription);
    }

    @Override
    public void update(SubscriptionHistory subscription) {
        repository.update(subscription);
    }

    @Override
    public void remove(SubscriptionHistory subscription) {
        repository.remove(subscription);
        collectionUtil.deleteModelFromList(data, subscription);
    }

    @Override
    public ObservableList<SubscriptionHistory> findAll() {
        data = FXCollections.observableArrayList();
        data.addAll(repository.findAll());
        return data;
    }

    @Override
    public SubscriptionHistory findById(String id) {
        return repository.findByValue("id", id).get(0);
    }
}
