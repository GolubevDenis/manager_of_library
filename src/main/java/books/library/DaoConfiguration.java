package books.library;

import books.library.app.file.ProjectFilesManager;
import books.library.model.*;
import books.library.util.file.UtilFile;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class DaoConfiguration extends MyConfiguration {

    @Autowired
    private ProjectFilesManager projectFilesManager;

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");

        try {
            dataSource.setUrl("jdbc:sqlite:" + projectFilesManager.dataBaseFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(getContext().getBean(DriverManagerDataSource.class));
        bean.setAnnotatedClasses(Book.class,
                Reader.class,
                Subscription.class,
                SubscriptionHistory.class,
                DateAndCount.class);
        bean.setHibernateProperties((Properties) getContext().getBean("hibernateProperties"));
        return bean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager h = new HibernateTransactionManager();
        h.setSessionFactory(getContext().getBean(SessionFactory.class));
        return h;
    }

    @Bean("hibernateProperties")
    public Properties hibernateProperties(){
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("db/hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

