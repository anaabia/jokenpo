package com.brq.Jokenpo.domain.repository;

import java.util.Collection;
import java.util.Optional;

public interface PersisteRepository<T, I> {

    T save(T name);

    Optional<T> find(I id);

    Optional<T> findName(String Name);

    boolean delete(I player);

    Collection<T> findAll();
}
