package mz.examples.uowrepquery.service.customer;

import mz.examples.uowrepquery.service.exception.ValidationException;
import mz.examples.uowrepquery.service.region.Region;

import javax.persistence.*;
import java.util.regex.Pattern;

@Entity
@Table(name = "customers")
public class Customer {
  private static Pattern CODE_PATTERN = Pattern.compile("[A-Z]{3}-[0-9]{3}");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String code;
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "region_id")
  private Region region;

  void initialize(NewCustomerNormalData data) {
    setCode(validateCode(data.getCode()));
    setName(validateName(data.getName()));
    setRegion(validateRegion(data.getRegion()));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  private static String validateCode(String code) {
    if (code == null || code.isEmpty()) {
      throw new ValidationException("A customer must have a code");
    }
    if (!CODE_PATTERN.matcher(code).matches()) {
      throw new ValidationException("Customer code is not valid");
    }
    return code;
  }

  private static String validateName(String name) {
    if (name == null || name.isEmpty()) {
      throw new ValidationException("A customer must have a name");
    }
    return name;
  }

  private static Region validateRegion(Region region) {
    if (region == null) {
      throw new ValidationException("A customer must belong to a region");
    }
    return region;
  }
}
