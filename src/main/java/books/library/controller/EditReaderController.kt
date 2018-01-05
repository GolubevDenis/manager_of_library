package books.library.controller

import books.library.model.Reader
import books.library.service.reader.ReaderService
import books.library.util.fx.UtilFx
import javafx.fxml.FXML
import javafx.scene.control.TextField
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

class EditReaderController {

    @FXML private lateinit var number:TextField
    @FXML private lateinit var first_name:TextField
    @FXML private lateinit var last_name:TextField
    @FXML private lateinit var profession:TextField
    @FXML private lateinit var year_birth:TextField
    @FXML private lateinit var otchestvo:TextField
    @FXML private lateinit var job:TextField
    @FXML private lateinit var education:TextField
    @FXML private lateinit var school:TextField
    @FXML private lateinit var address:TextField
    @FXML private lateinit var phone:TextField
    @FXML private lateinit var passport_series:TextField
    @FXML private lateinit var number_passport:TextField
    @FXML private lateinit var who_and_when:TextField
    @FXML private lateinit var date_entry:TextField

    @Autowired private lateinit var readerService:ReaderService
    @Autowired private lateinit var utilFx:UtilFx

    private var reader:Reader? = null
    private var refactor:Boolean = false

    @PostConstruct
    fun init() {
        createDownClickListener()
    }

    private fun createDownClickListener() {
        utilFx.arrowClickFocusListener(
                number
                ,first_name
                ,last_name
                ,otchestvo
                ,year_birth
                ,profession
                ,job
                ,education
                ,school
                ,address
                ,phone
                ,passport_series
                ,number_passport
                ,who_and_when
                ,date_entry
        )
    }

    @FXML
    private fun save() {
        val thisReader = if(refactor) reader else Reader()
        thisReader!!.number = number.text
        thisReader.first_name = first_name.text
        thisReader.last_name = last_name.text
        thisReader.profession = profession.text
        thisReader.year_birth = year_birth.text
        thisReader.otchestvo = otchestvo.text
        thisReader.job = job.text
        thisReader.education = education.text
        thisReader.school = school.text
        thisReader.address = address.text
        thisReader.phone = phone.text
        thisReader.passport_series = passport_series.text
        thisReader.number_passport = number_passport.text
        thisReader.who_and_when = who_and_when.text
        thisReader.date_entry = date_entry.text

        if(refactor)readerService.update(thisReader)
        else readerService.add(thisReader)

        date_entry.scene.window.hide()
    }

    fun setReader(reader:Reader) {
        this.reader = reader

        number.text = reader.number
        first_name.text = reader.first_name
        last_name.text = reader.last_name
        profession.text = reader.profession
        year_birth.text = reader.year_birth
        otchestvo.text = reader.otchestvo
        job.text = reader.job
        education.text = reader.education
        school.text = reader.school
        address.text = reader.address
        phone.text = reader.phone
        passport_series.text = reader.passport_series
        number_passport.text = reader.number_passport
        who_and_when.text = reader.who_and_when
        date_entry.text = reader.date_entry
    }

    fun setRefactor(refactor:Boolean) {
        this.refactor = refactor
    }

    @FXML
    private fun close() {
        date_entry.scene.window.hide()
    }
}
