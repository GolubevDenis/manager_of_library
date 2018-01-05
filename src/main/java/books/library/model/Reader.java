package books.library.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.util.List;

@Table(name = "readers")
@Entity
public class Reader extends Model{

    @Transient private StringProperty phone = new SimpleStringProperty();
    @Transient private StringProperty last_name = new SimpleStringProperty();
    @Transient private StringProperty first_name = new SimpleStringProperty();
    @Transient private StringProperty otchestvo = new SimpleStringProperty();
    @Transient private StringProperty id = new SimpleStringProperty();
    @Transient private StringProperty year_birth = new SimpleStringProperty();
    @Transient private StringProperty profession = new SimpleStringProperty();
    @Transient private StringProperty job = new SimpleStringProperty();
    @Transient private StringProperty education = new SimpleStringProperty();
    @Transient private StringProperty school = new SimpleStringProperty();
    @Transient private StringProperty address = new SimpleStringProperty();
    @Transient private StringProperty passport_series = new SimpleStringProperty();
    @Transient private StringProperty number_passport = new SimpleStringProperty();
    @Transient private StringProperty who_and_when = new SimpleStringProperty();
    @Transient private StringProperty date_entry = new SimpleStringProperty();
    @Transient private StringProperty number = new SimpleStringProperty();
    @Transient private List<Subscription> subscriptions = FXCollections.observableArrayList();
    @Transient private List<SubscriptionHistory> subscriptionsHistory = FXCollections.observableArrayList();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (id != null ? !id.equals(reader.id) : reader.id != null) return false;
        return true;
    }

    @Column(name = "school")
    public String getSchool() {
        return school.get();
    }

    public StringProperty schoolProperty() {
        return school;
    }

    public void setSchool(String school) {
        this.school.set(school);
    }

    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER, orphanRemoval=true)
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER, orphanRemoval=true)
    public List<SubscriptionHistory> getSubscriptionsHistory() {
        return subscriptionsHistory;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setSubscriptionsHistory(List<SubscriptionHistory> subscriptionsHistory) {
        this.subscriptionsHistory = subscriptionsHistory;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    @Column(name = "last_name")
    public String getLast_name() {
        return last_name.get();
    }

    public StringProperty last_nameProperty() {
        return last_name;
    }

    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name.get();
    }

    public StringProperty first_nameProperty() {
        return first_name;
    }

    @Column(name = "otchestvo")
    public String getOtchestvo() {
        return otchestvo.get();
    }

    public StringProperty otchestvoProperty() {
        return otchestvo;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    @Column(name = "year_birth")
    public String getYear_birth() {
        return year_birth.get();
    }

    public StringProperty year_birthProperty() {
        return year_birth;
    }

    @Column(name = "profession")
    public String getProfession() {
        return profession.get();
    }

    public StringProperty professionProperty() {
        return profession;
    }

    @Column(name = "job")
    public String getJob() {
        return job.get();
    }

    public StringProperty jobProperty() {
        return job;
    }

    @Column(name = "education")
    public String getEducation() {
        return education.get();
    }

    public StringProperty educationProperty() {
        return education;
    }

    @Column(name = "address")
    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    @Column(name = "passport_series")
    public String getPassport_series() {
        return passport_series.get();
    }

    public StringProperty passport_seriesProperty() {
        return passport_series;
    }

    @Column(name = "number_passport")
    public String getNumber_passport() {
        return number_passport.get();
    }

    public StringProperty number_passportProperty() {
        return number_passport;
    }

    @Column(name = "who_and_when")
    public String getWho_and_when() {
        return who_and_when.get();
    }

    public StringProperty who_and_whenProperty() {
        return who_and_when;
    }

    @Column(name = "date_entry")
    public String getDate_entry() {
        return date_entry.get();
    }

    @Column(name = "number")
    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public StringProperty date_entryProperty() {
        return date_entry;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo.set(otchestvo);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setYear_birth(String year_birth) {
        this.year_birth.set(year_birth);
    }

    public void setProfession(String profession) {
        this.profession.set(profession);
    }

    public void setJob(String job) {
        this.job.set(job);
    }

    public void setEducation(String education) {
        this.education.set(education);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPassport_series(String passport_series) {
        this.passport_series.set(passport_series);
    }

    public void setNumber_passport(String number_series) {
        this.number_passport.set(number_series);
    }

    public void setWho_and_when(String who_and_when) {
        this.who_and_when.set(who_and_when);
    }

    public void setDate_entry(String date_entry) {
        this.date_entry.set(date_entry);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "phone=" + phone +
                ", last_name=" + last_name +
                ", first_name=" + first_name +
                ", otchestvo=" + otchestvo +
                ", id=" + id +
                ", year_birth=" + year_birth +
                ", profession=" + profession +
                ", job=" + job +
                ", education=" + education +
                ", address=" + address +
                ", passport_series=" + passport_series +
                ", number_series=" + number_passport +
                ", who_and_when=" + who_and_when +
                ", date_entry=" + date_entry +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
