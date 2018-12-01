package mz.examples.uowrepquery.service.customer;

import mz.examples.uowrepquery.data.Repository;

public interface CustomerRepository extends Repository<Customer, Integer> {
  Customer findByCode(String code);
}
