package mz.examples.uowrepquery.api.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/persistence.properties")
public class PersistenceConfig {
  @Autowired org.springframework.core.env.Environment env;

  @Bean
  public LocalSessionFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setHibernateProperties(hibernateProperties());
    sessionFactory.setPackagesToScan("mz.examples.uowrepquery.service");

    return sessionFactory;
  }

  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public EntityManager entityManager(SessionFactory sessionFactory) {
    // It is important to use getCurrentSession (not openSession) so the session
    // is bound to the current transaction context
    // https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#orm-hibernate-straight
    return sessionFactory.getCurrentSession();
  }

  @Bean
  public PlatformTransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory.getObject());
    return transactionManager;
  }

  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();

    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.password"));

    return dataSource;
  }

  private Properties hibernateProperties() {
    var properties = new Properties();
    properties.setProperty(Environment.DIALECT, env.getProperty("hibernate.dialect"));
    properties.setProperty(Environment.HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
    properties.setProperty(Environment.SHOW_SQL, env.getProperty("hibernate.showSql"));

    return properties;
  }
}
