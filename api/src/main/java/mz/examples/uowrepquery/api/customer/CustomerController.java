package mz.examples.uowrepquery.api.customer;

import com.querydsl.core.types.Predicate;
import mz.examples.uowrepquery.service.customer.CustomerService;
import mz.examples.uowrepquery.service.customer.NewCustomerData;
import mz.examples.uowrepquery.service.customer.QCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  @Autowired CustomerQuery customerQuery;
  @Autowired CustomerService customerService;

  @GetMapping
  public Iterable<CustomerDTO> getCustomers(
      @RequestParam(required = false) String namePrefix,
      @RequestParam(required = false) Optional<Integer> offset,
      @RequestParam(required = false) Optional<Integer> size) {
    Predicate filter =
        namePrefix != null && !namePrefix.isEmpty()
            ? QCustomer.customer.name.startsWith(namePrefix)
            : null;
    return customerQuery.fetchCustomers(filter, offset.orElse(0), size.orElse(0));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public int postCustomer(@RequestBody NewCustomerData data) {
    var customer = customerService.createCustomer(data);
    return customer.getId();
  }
}
