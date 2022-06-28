package me.metropanties.sodiumplugin.database;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface CrudOperations<T, ID> {

    T save(@NotNull T entity);

    boolean delete(@NotNull T entity);

    boolean deleteByID(ID id);

    Optional<T> findByID(ID id);

    List<T> findAll();

}
