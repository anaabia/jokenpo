package com.brq.Jokenpo.domain.repository;

import com.brq.Jokenpo.domain.model.Player;

import java.util.Collection;
import java.util.Optional;

public interface PersisteRepository<T, I> {

    T save(Player name);

    Optional<T> find(I id);

    Optional<T> findName(String Name);

    boolean delete(I player);

    Collection<T> findAll();
}
