package mz.examples.uowrepquery.api.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import mz.examples.uowrepquery.api.customer.CustomerQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

@Configuration
public class QueryConfig {
  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public CustomerQuery customerQuery(JPAQueryFactory jpaQueryFactory) {
    return new CustomerQuery(jpaQueryFactory);
  }
}
