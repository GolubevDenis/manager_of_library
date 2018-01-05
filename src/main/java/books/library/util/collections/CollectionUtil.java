package books.library.util.collections;

import books.library.model.Model;

import java.util.List;

public interface CollectionUtil {

    boolean deleteModelFromList(List<? extends Model> list, Model tmodel);
}
