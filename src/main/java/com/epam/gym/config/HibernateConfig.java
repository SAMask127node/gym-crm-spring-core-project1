package com.epam.gym.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@ComponentScan("com.epam.gym")  // picks up @Repository, @Service, @Component
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        ds.setJdbcUrl(        env.getRequiredProperty("jdbc.url"));
        ds.setUsername(       env.getRequiredProperty("jdbc.username"));
        ds.setPassword(       env.getRequiredProperty("jdbc.password"));
        return ds;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource ds) {
        return Flyway.configure()
                .dataSource(ds)
                .locations(env.getProperty("flyway.locations"))
                .load();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(ds);
        sfb.setPackagesToScan("com.epam.gym.domain");
        Properties props = new Properties();
        props.put("hibernate.dialect",     env.getProperty("hibernate.dialect"));
        props.put("hibernate.show_sql",    env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
        sfb.setHibernateProperties(props);
        return sfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sf) {
        return new HibernateTransactionManager(sf);
    }
}
