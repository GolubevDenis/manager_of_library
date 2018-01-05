package books.library.util.collections;

import books.library.model.Model;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class CollectionHelper implements CollectionUtil{

    @Override
    public boolean deleteModelFromList(List<? extends Model> list, Model model) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Model m = (Model) iterator.next();
            if(m.getId().equals(model.getId())){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
