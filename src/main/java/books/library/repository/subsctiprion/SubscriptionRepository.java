package books.library.repository.subsctiprion;

import books.library.model.Subscription;
import books.library.repository.Repository;

import java.util.Date;
import java.util.Map;

public interface SubscriptionRepository extends Repository<Subscription> {

    Map<String, Number> getCountSubscriptionByDates(Date date1, Date date2);
}
