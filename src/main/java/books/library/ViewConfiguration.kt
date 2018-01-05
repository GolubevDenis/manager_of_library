package books.library

import books.library.controller.*
import javafx.fxml.FXMLLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import java.io.IOException
import java.io.InputStream

@Configuration
open class ViewConfiguration {

    @Lazy @Bean(name = arrayOf("mainView")) open fun mainView() = loadView("fxml/main.fxml")
    @Lazy @Bean(name = arrayOf("subscriptionsView")) open fun subscriptionsView() = loadView("fxml/subscription.fxml")
    @Lazy @Bean(name = arrayOf("subscriptionsHistoryView")) open fun subscriptionsHistoryView() = loadView("fxml/subscriptions_history.fxml")
    @Lazy @Bean(name = arrayOf("editSubscriptionsView")) open fun editSubscriptionsView() = loadView("fxml/edit_subscription.fxml")
    @Lazy @Bean(name = arrayOf("expiredView")) open fun expiredView() = loadView("fxml/expired.fxml")
    @Lazy @Bean(name = arrayOf("installPageView")) open fun installPageView() = loadView("fxml/install_page.fxml")
    @Lazy @Bean(name = arrayOf("statisticsSubscriptionView")) open fun statisticsSubscriptionView() = loadView("fxml/statistics_subscription.fxml")
    @Lazy @Bean(name = arrayOf("statisticsGeneralView")) open fun statisticsGeneralView() = loadView("fxml/statistics_general.fxml")
    @Lazy @Bean(name = arrayOf("booksView")) open fun booksView() = loadView("fxml/books.fxml")
    @Lazy @Bean(name = arrayOf("editBookView")) open fun editBookView() = loadView("fxml/edit_book.fxml")
    @Lazy @Bean(name = arrayOf("editReaderView")) open fun editReaderView() = loadView("fxml/edit_reader.fxml")
    @Lazy @Bean(name = arrayOf("readersView")) open fun readersView() = loadView("fxml/readers.fxml")
    @Lazy @Bean(name = arrayOf("settingView")) open fun settingView() = loadView("fxml/setting.fxml")
    @Lazy @Bean(name = arrayOf("splashScreenView")) open fun splashScreenView() = loadView("fxml/splash_screen.fxml")

    @Bean open fun mainController() = mainView().controller as MainController
    @Bean open fun installPageController() = installPageView().controller as InstallPageController
    @Bean open fun statisticsSubscriptionController() = statisticsSubscriptionView().controller as StatisticsSubscriptionController
    @Bean open fun statisticsGeneralController() = statisticsGeneralView().controller as StatisticsGeneralController
    @Bean open fun installBooksController() = booksView().controller as BooksController
    @Bean open fun editBookController() = editBookView().controller as EditBookController
    @Bean open fun editReaderController() = editReaderView().controller as EditReaderController
    @Bean open fun addReadersController() = readersView().controller as ReadersController
    @Bean open fun settingController() = settingView().controller as SettingController
    @Bean open fun splashScreenController() = splashScreenView().controller as SplashScreenController
    @Bean open fun controllerSubscription() = subscriptionsView().controller as SubscriptionController
    @Bean open fun controllerSubscriptionHistory() = subscriptionsHistoryView().controller as SubscriptionHistoryController
    @Bean open fun controllerExpired() = expiredView().controller as ExpiredController
    @Bean open fun controllerEditSubscriptions() = editSubscriptionsView().controller as EditSubscriptionController

    @Throws(IOException::class)
    private fun loadView(url: String) : ViewHolder{
        var fxmlStream: InputStream? = null
        try {
            fxmlStream = javaClass.classLoader.getResourceAsStream(url)
            val loader = FXMLLoader()
            loader.load<Void>(fxmlStream)
            return ViewHolder(loader.getRoot(), loader.getController())
        }finally {
            if(fxmlStream != null)
                fxmlStream.close()
        }
    }
}