package mz.examples.uowrepquery.api.config;

import mz.examples.uowrepquery.service.customer.CustomerRepository;
import mz.examples.uowrepquery.service.customer.JPACustomerRepository;
import mz.examples.uowrepquery.service.region.JPARegionRepository;
import mz.examples.uowrepquery.service.region.RegionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class RepositoryConfig {
  @Bean
  public CustomerRepository customerRepository(EntityManager entityManager) {
    return new JPACustomerRepository(entityManager);
  }

  @Bean
  public RegionRepository regionRepository(EntityManager entityManager) {
    return new JPARegionRepository(entityManager);
  }
}
