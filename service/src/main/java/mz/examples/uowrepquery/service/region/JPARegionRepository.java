package mz.examples.uowrepquery.service.region;

import mz.examples.uowrepquery.data.jpa.JPARepository;

import javax.persistence.EntityManager;

public class JPARegionRepository extends JPARepository<Region, String> implements RegionRepository {
  public JPARegionRepository(EntityManager entityManager) {
    super(Region.class, entityManager);
  }
}
