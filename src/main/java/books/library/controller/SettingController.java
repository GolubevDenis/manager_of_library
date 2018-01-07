package books.library.controller;

import books.library.app.log.MyLogger;
import books.library.app.setting.Setting;
import books.library.interfaces.UpdateData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

public class SettingController implements UpdateData{

    @FXML private CheckBox number;
    @FXML private CheckBox otdel;
    @FXML private CheckBox title;
    @FXML private CheckBox author;
    @FXML private CheckBox year;
    @FXML private CheckBox status;
    @FXML private CheckBox publisher;
    @FXML private CheckBox language;
    @FXML private CheckBox genre;
    @FXML private CheckBox description;
    @FXML private CheckBox count_pages;

    @FXML private CheckBox first_name;
    @FXML private CheckBox last_name;
    @FXML private CheckBox birth_year;
    @FXML private CheckBox phone;
    @FXML private CheckBox otchestvo;
    @FXML private CheckBox address;
    @FXML private CheckBox education;
    @FXML private CheckBox scool;
    @FXML private CheckBox profession;
    @FXML private CheckBox job;
    @FXML private CheckBox number_passport;
    @FXML private CheckBox seria_passport;
    @FXML private CheckBox entry_date;
    @FXML private CheckBox who_and_when;
    @FXML private CheckBox number_reader;

    @Autowired
    private Setting setting;

    @Autowired
    private MyLogger logger;

    @Override
    public void updateData(){
        number.setSelected(setting.getBooleanProperty("book_colunmt_number"));
        otdel.setSelected(setting.getBooleanProperty("book_colunmt_otdel"));
        title.setSelected(setting.getBooleanProperty("book_colunmt_title"));
        author.setSelected(setting.getBooleanProperty("book_colunmt_author"));
        year.setSelected(setting.getBooleanProperty("book_colunmt_year"));
        status.setSelected(setting.getBooleanProperty("book_colunmt_status"));
        publisher.setSelected(setting.getBooleanProperty("book_colunmt_publisher"));
        language.setSelected(setting.getBooleanProperty("book_colunmt_language"));
        genre.setSelected(setting.getBooleanProperty("book_colunmt_genre"));
        description.setSelected(setting.getBooleanProperty("book_colunmt_description"));
        count_pages.setSelected(setting.getBooleanProperty("book_colunmt_count_pages"));

        number_reader.setSelected(setting.getBooleanProperty("reader_column_number"));
        first_name.setSelected(setting.getBooleanProperty("reader_column_first_name"));
        last_name.setSelected(setting.getBooleanProperty("reader_column_last_name"));
        birth_year.setSelected(setting.getBooleanProperty("reader_column_year_birth"));
        phone.setSelected(setting.getBooleanProperty("reader_column_phone"));
        otchestvo.setSelected(setting.getBooleanProperty("reader_column_otchestvo"));
        address.setSelected(setting.getBooleanProperty("reader_column_address"));
        education.setSelected(setting.getBooleanProperty("reader_column_education"));
        scool.setSelected(setting.getBooleanProperty("reader_column_school"));
        profession.setSelected(setting.getBooleanProperty("reader_column_profession"));
        job.setSelected(setting.getBooleanProperty("reader_column_job"));
        number_passport.setSelected(setting.getBooleanProperty("reader_column_number_passport"));
        seria_passport.setSelected(setting.getBooleanProperty("reader_column_passport_series"));
        entry_date.setSelected(setting.getBooleanProperty("reader_column_date_entry"));
        who_and_when.setSelected(setting.getBooleanProperty("reader_column_who_and_when"));
    }

    @FXML
    private void reader_save(){
        setting.setProperty("reader_column_number", number_reader.isSelected());
        setting.setProperty("reader_column_first_name", first_name.isSelected());
        setting.setProperty("reader_column_last_name", last_name.isSelected());
        setting.setProperty("reader_column_year_birth", birth_year.isSelected());
        setting.setProperty("reader_column_phone", phone.isSelected());
        setting.setProperty("reader_column_otchestvo", otchestvo.isSelected());
        setting.setProperty("reader_column_address", address.isSelected());
        setting.setProperty("reader_column_education", education.isSelected());
        setting.setProperty("reader_column_school", scool.isSelected());
        setting.setProperty("reader_column_profession", profession.isSelected());
        setting.setProperty("reader_column_job", job.isSelected());
        setting.setProperty("reader_column_number_passport", number_passport.isSelected());
        setting.setProperty("reader_column_passport_series", seria_passport.isSelected());
        setting.setProperty("reader_column_date_entry", entry_date.isSelected());
        setting.setProperty("reader_column_who_and_when", who_and_when.isSelected());
    }

    @FXML
    private void book_save(){
        setting.setProperty("book_colunmt_number", number.isSelected());
        setting.setProperty("book_colunmt_otdel", otdel.isSelected());
        setting.setProperty("book_colunmt_title", title.isSelected());
        setting.setProperty("book_colunmt_author", author.isSelected());
        setting.setProperty("book_colunmt_year", year.isSelected());
        setting.setProperty("book_colunmt_status", status.isSelected());
        setting.setProperty("book_colunmt_publisher", publisher.isSelected());
        setting.setProperty("book_colunmt_language", language.isSelected());
        setting.setProperty("book_colunmt_genre", genre.isSelected());
        setting.setProperty("book_colunmt_description", description.isSelected());
        setting.setProperty("book_colunmt_count_pages", count_pages.isSelected());
    }
}
