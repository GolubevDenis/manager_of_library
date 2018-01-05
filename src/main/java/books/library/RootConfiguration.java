package books.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@Import({DaoConfiguration.class, ViewConfiguration.class})
@ComponentScan
@Configuration
public class RootConfiguration{

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("MyLogger");
    }
}
