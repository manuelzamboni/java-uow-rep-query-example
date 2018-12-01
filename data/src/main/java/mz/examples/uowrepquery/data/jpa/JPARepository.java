package mz.examples.uowrepquery.data.jpa;

import mz.examples.uowrepquery.data.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

/**
 * JPA implementation of {@link Repository}.
 *
 * @param <T> type of entity.
 * @param <ID> type of the ID of the entities.
 */
public abstract class JPARepository<T, ID> implements Repository<T, ID> {
  private final Class<T> entityClass;
  private final EntityManager entityManager;

  public JPARepository(Class<T> entityClass, EntityManager entityManager) {
    this.entityClass = Objects.requireNonNull(entityClass, "Must pass the entity class");
    this.entityManager = Objects.requireNonNull(entityManager, "Must pass the entity manager");
  }

  protected Class<T> getEntityClass() {
    return entityClass;
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  public <U extends T> U add(U entity) {
    entityManager.persist(requireEntity(entity));
    return entity;
  }

  @Override
  public <U extends T> Iterable<U> addMany(Iterable<U> entities) {
    var entityManager = this.entityManager;
    for (U entity : requireEntities(entities)) {
      entityManager.persist(entity);
    }
    return entities;
  }

  @Override
  public T findById(ID id) {
    return entityManager.find(entityClass, requireId(id));
  }

  @Override
  public Iterable<T> findAll() {
    var entityManager = getEntityManager();
    var builder = entityManager.getCriteriaBuilder();
    var entityClass = getEntityClass();
    var criteriaQuery = builder.createQuery(entityClass);
    criteriaQuery.from(entityClass);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  @Override
  public boolean existsById(ID id) {
    return this.findById(requireId(id)) != null;
  }

  @Override
  public void remove(T entity) {
    entityManager.remove(requireEntity(entity));
  }

  @Override
  public void removeMany(Iterable<? extends T> entities) {
    var entityManager = this.entityManager;
    for (var entity : requireEntities(entities)) {
      entityManager.remove(entity);
    }
  }

  @Override
  public void removeById(ID id) {}

  private static <T> T requireEntity(T entity) {
    return Objects.requireNonNull(entity, "Must pass an entity");
  }

  private static <T> Iterable<T> requireEntities(Iterable<T> entities) {
    return Objects.requireNonNull(entities, "Must pass an entities list");
  }

  private static <ID> ID requireId(ID id) {
    return Objects.requireNonNull(id, "Must pass an entity ID");
  }
}
