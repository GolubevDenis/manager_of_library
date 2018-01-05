package books.library.service.subscription_history;

import books.library.model.Subscription;
import books.library.model.SubscriptionHistory;
import books.library.service.Service;
import javafx.collections.ObservableList;

import java.util.List;

public interface SubscriptionHistoryService extends Service<SubscriptionHistory> {

    ObservableList<SubscriptionHistory> insertData(List<SubscriptionHistory> list);

}
