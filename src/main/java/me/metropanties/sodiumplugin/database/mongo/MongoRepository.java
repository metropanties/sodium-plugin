package me.metropanties.sodiumplugin.database.mongo;

import com.mongodb.client.result.DeleteResult;
import dev.morphia.Datastore;
import dev.morphia.query.experimental.filters.Filters;
import me.metropanties.sodiumplugin.database.CrudOperations;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public abstract class MongoRepository<T, ID> implements CrudOperations<T, ID> {

    private final Datastore datastore;
    private final Class<T> typeClass;

    public MongoRepository(Datastore datastore, Class<T> typeClass) {
        this.datastore = datastore;
        this.typeClass = typeClass;
    }

    private T getByID(ID id) {
        return datastore.find(typeClass)
                .filter(Filters.eq("_id", id))
                .first();
    }

    @Override
    public T save(@NotNull T entity) {
        return datastore.save(entity);
    }

    @Override
    public boolean delete(@NotNull T entity) {
        DeleteResult result = datastore.delete(entity);
        return result.wasAcknowledged();
    }

    @Override
    public boolean deleteByID(ID id) {
        T entity = getByID(id);
        if (entity == null) return false;

        DeleteResult result = datastore.delete(entity);
        return result.wasAcknowledged();
    }

    @Override
    public Optional<T> findByID(ID id) {
        return Optional.ofNullable(getByID(id));
    }

    @Override
    public List<T> findAll() {
        return datastore.find(typeClass).stream()
                .toList();
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

}
