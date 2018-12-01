package mz.examples.uowrepquery.service.customer;

import mz.examples.uowrepquery.data.jpa.JPARepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

public class JPACustomerRepository extends JPARepository<Customer, Integer>
    implements CustomerRepository {
  public JPACustomerRepository(EntityManager entityManager) {
    super(Customer.class, entityManager);
  }

  @Override
  public Customer findByCode(String code) {
    Objects.requireNonNull(code, "Must pass a customer code");
    try {
      return (Customer)
          getEntityManager()
              .createQuery("select c from Customer c where c.code = ?1")
              .setParameter(1, code)
              .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
