package mz.examples.uowrepquery.data;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Repository<T, ID> {
  /**
   * Adds a given entity to the repository. The returned instance could be different to the given
   * one.
   *
   * @param entity must not be {@literal null}.
   * @return the added entity that should be used for any further operations.
   */
  <U extends T> U add(U entity);

  /**
   * Adds the given entities to the repository. The returned instances could be different to the
   * given ones.
   *
   * @param entities must not be {@literal null}.
   * @return the added entities entities will never be {@literal null}.
   * @throws NullPointerException in case the given entity is {@literal null}.
   */
  <U extends T> Iterable<U> addMany(Iterable<U> entities);

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal null} if none found
   * @throws NullPointerException if {@code id} is {@literal null}.
   */
  T findById(ID id);

  /**
   * Returns all instances with the given IDs.
   *
   * @param ids ids must not be {@literal null}.
   * @return the entities found. If no entities are found, it will be an empty iterable.
   */
  default Iterable<T> findByIds(Iterable<ID> ids) {
    // Fetch entities on demand
    var self = this;
    Iterator<ID> idsIterator = ids.iterator();
    Iterator<T> fetchIterator =
        new Iterator<>() {
          private T nextEntity;

          @Override
          public boolean hasNext() {
            return idsIterator.hasNext() && (nextEntity = fetchNextEntity()) != null;
          }

          @Override
          public T next() {
            // This is a private implementation and this should never happen, but no harm in being
            // strict
            if (nextEntity == null) {
              throw new NoSuchElementException();
            }
            return nextEntity;
          }

          private T fetchNextEntity() {
            ID nextId;
            while (idsIterator.hasNext()) {
              T entity = self.findById(idsIterator.next());
              if (entity != null) {
                return entity;
              }
            }
            return null;
          }
        };
    return () -> fetchIterator;
  }

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  Iterable<T> findAll();

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws NullPointerException if {@code id} is {@literal null}.
   */
  boolean existsById(ID id);

  /**
   * Removes a given entity from the repository.
   *
   * @param entity entity to remove.
   * @throws NullPointerException in case the given entity is {@literal null}.
   */
  void remove(T entity);

  /**
   * Removes the given entities from the repository.
   *
   * @param entities entities to remove.
   * @throws NullPointerException in case the given {@link Iterable} is {@literal null}.
   */
  void removeMany(Iterable<? extends T> entities);

  /**
   * Removes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   * @throws NullPointerException in case the given {@code id} is {@literal null}
   */
  void removeById(ID id);
}
