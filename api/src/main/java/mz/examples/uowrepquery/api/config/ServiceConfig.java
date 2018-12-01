package mz.examples.uowrepquery.api.config;

import mz.examples.uowrepquery.service.customer.CustomerRepository;
import mz.examples.uowrepquery.service.customer.CustomerService;
import mz.examples.uowrepquery.service.region.RegionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
  @Bean
  public CustomerService customerService(
      CustomerRepository customerRepository, RegionRepository regionRepository) {
    return new CustomerService(customerRepository, regionRepository);
  }
}
