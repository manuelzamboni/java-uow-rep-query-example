package mz.examples.uowrepquery.service.customer;

import mz.examples.uowrepquery.service.exception.ValidationException;
import mz.examples.uowrepquery.service.region.RegionRepository;

import javax.transaction.Transactional;
import java.util.Objects;

public class CustomerService {
  private final CustomerRepository customerRepository;
  private final RegionRepository regionRepository;

  public CustomerService(CustomerRepository customerRepository, RegionRepository regionRepository) {
    this.customerRepository = customerRepository;
    this.regionRepository = regionRepository;
  }

  @Transactional
  public Customer createCustomer(NewCustomerData data) {
    Objects.requireNonNull(data, "No data specified");

    NewCustomerNormalData normalData = normalizeNewCustomerData(data);

    Customer customer = new Customer();
    customer.initialize(normalData);

    checkUniqueCode(customer.getCode());

    customerRepository.add(customer);
    return customer;
  }

  private NewCustomerNormalData normalizeNewCustomerData(NewCustomerData data) {
    String regionId = data.getRegionId();
    if (regionId == null) {
      throw new ValidationException("You must pass a region id");
    }
    var region = regionRepository.findById(regionId);
    if (region == null) {
      throw new ValidationException(String.format("No region with ID = \"%s\"", regionId));
    }

    // We could use ModelMapper to avoid some of this boilerplate code
    var normalData = new NewCustomerNormalData();
    normalData.setCode(data.getCode());
    normalData.setName(data.getName());
    normalData.setRegion(region);

    return normalData;
  }

  private void checkUniqueCode(String code) {
    if (customerRepository.findByCode(code) != null) {
      throw new ValidationException("There is already a customer with code " + code);
    }
  }
}
