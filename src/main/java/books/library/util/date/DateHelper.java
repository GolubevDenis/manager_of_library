package books.library.util.date;

import org.apache.commons.validator.routines.DateValidator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateHelper implements UtilDate {

    private final String DATE_FORMAT = "dd.MM.yyyy";

    private DateValidator dateValidator;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Calendar calendar;

    @PostConstruct
    public void init(){
        dateValidator = DateValidator.getInstance();
        calendar = new Calendar.Builder().build();
    }

    @Override
    public boolean compareDates(String date1, String date2) throws Exception {
        Date d1 = dateValidator.validate(date1);
        Date d2 = dateValidator.validate(date2);
        if(d1 == null || d2 == null)
            throw new Exception("Даты не валидны");
        return compareDates(d1, d2);
    }

    @Override
    public boolean compareDates(Date date1, Date date2) {
        int i = dateValidator.compareDates(date1, date2, TimeZone.getDefault());
        return i > 0;
    }

    @Override
    public boolean compareDates(Date date1, String date2) throws Exception {
        Date d2 = dateValidator.validate(date2);
        if(d2 == null || date1 == null)
            throw new Exception("Даты не валидны");
        return compareDates(date1, d2);
    }

    @Override
    public Date parseDate(String sDate) {
        return dateValidator.validate(sDate, DATE_FORMAT);
    }

    @Override
    public String getDateText(Date date) {
        return dateFormat.format(date);
    }

    @Override
    public String getDatePlusOrMinusDay(String date, boolean isPlus){
        return getDateText(getDatePlusOrMinus(parseDate(date), Calendar.DATE, 1, isPlus ));
    }

    @Override
    public String getDatePlusOrMinusWeek(String date, boolean isPlus){
        return getDateText(getDatePlusOrMinus(parseDate(date), Calendar.DATE, 7, isPlus ));
    }

    @Override
    public String getDatePlusOrMinusMonth(String date, boolean isPlus){
        return getDateText(getDatePlusOrMinus(parseDate(date), Calendar.MONTH, 1, isPlus ));
    }

    @Override
    public Date getDatePlusOrMinusDay(Date date, boolean isPlus){
        return getDatePlusOrMinus(date, Calendar.DATE, 1, isPlus );
    }

    @Override
    public Date getDatePlusOrMinusWeek(Date date, boolean isPlus){
        return getDatePlusOrMinus(date, Calendar.DATE, 7, isPlus );
    }

    @Override
    public Date getDatePlusOrMinusMonth(Date date, boolean isPlus){
        return getDatePlusOrMinus(date, Calendar.MONTH, 1, isPlus );
    }

    private Date getDatePlusOrMinus(Date date, int period, int offset, boolean isPlus){
        calendar.setTime(date);
        if(isPlus)
            calendar.set(period, calendar.get(period) + offset);
        else
            calendar.set(period, calendar.get(period) - offset);
        return calendar.getTime();
    }
}
