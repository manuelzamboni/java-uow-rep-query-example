package mz.examples.uowrepquery.service.customer;

import mz.examples.uowrepquery.service.region.Region;

public class NewCustomerNormalData {
  private String name;
  private String code;
  private Region region;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }
}
