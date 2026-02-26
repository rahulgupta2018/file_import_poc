package com.lloyds.fileimport.common.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface — implemented by SQLiteRepo (dev) and Spanner/Firestore repos (prod).
 * See architecture.md Section 6.5.
 *
 * @param <T>  Entity type
 * @param <ID> Primary key type
 */
public interface Repository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findByTenantId(String tenantId);

    void delete(ID id);
}
