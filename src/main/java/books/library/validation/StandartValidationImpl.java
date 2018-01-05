package books.library.validation;

import org.springframework.stereotype.Component;

@Component
public class StandartValidationImpl implements StandartValidator{


    @Override
    public boolean validNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
