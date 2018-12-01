package mz.examples.uowrepquery.api.customer;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import mz.examples.uowrepquery.service.customer.QCustomer;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CustomerQuery {
  private final JPAQueryFactory jpaQueryFactory;

  public CustomerQuery(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  public Iterable<CustomerDTO> fetchCustomers(@Nullable Predicate filter, int offset, int size) {
    var customer = QCustomer.customer;
    var query =
        jpaQueryFactory
            .selectFrom(customer)
            .where(filter)
            .select(projection(customer))
            .offset(offset);
    if (size > 0) {
      query = query.limit(size);
    }
    return query.fetch();
  }

  private QBean<CustomerDTO> projection(QCustomer customer) {
    return Projections.bean(
        CustomerDTO.class,
        customer.id,
        customer.code,
        customer.name,
        customer.region.name.as("regionName"));
  }
}
