package books.library.service.subscription;

import books.library.model.Subscription;
import books.library.service.Service;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SubscriptionService extends Service<Subscription> {

    ObservableList<Subscription> insertData(List<Subscription> list);

    ObservableList findExpired();

    Map<String, Number> getCountSubscriptionByDates(Date date1, Date date2);
}
