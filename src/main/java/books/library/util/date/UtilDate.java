package books.library.util.date;

import java.util.Date;

public interface UtilDate {

    boolean compareDates(String date1, String date2) throws Exception;
    boolean compareDates(Date date1, Date date2);
    boolean compareDates(Date date1, String date2) throws Exception;

    Date parseDate(String sDate);

    String getDateText(Date date);

    String getDatePlusOrMinusDay(String date, boolean isPlus);

    String getDatePlusOrMinusWeek(String date, boolean isPlus);

    String getDatePlusOrMinusMonth(String date, boolean isPlus);

    Date getDatePlusOrMinusDay(Date date, boolean isPlus);

    Date getDatePlusOrMinusWeek(Date date, boolean isPlus);

    Date getDatePlusOrMinusMonth(Date date, boolean isPlus);
}
