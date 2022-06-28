package me.metropanties.sodiumplugin.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import me.metropanties.sodiumplugin.database.Database;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MongoDatabase implements Database {

    private final String databaseName;
    private final String connectionURI;
    private final boolean indexes;

    private MongoClient client;
    private Datastore datastore;

    public MongoDatabase(String databaseName, String connectionURI, boolean indexes) {
        this.databaseName = databaseName;
        this.connectionURI = connectionURI;
        this.indexes = indexes;
    }

    @Override
    public void connect() {
        if (connectionURI == null) {
            this.client = MongoClients.create();
        } else {
            this.client = MongoClients.create(connectionURI);
        }

        this.datastore = Morphia.createDatastore(client, databaseName);
        if (indexes) {
            datastore.ensureIndexes();
        }
    }

    @Override
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
    }

    public MongoClient getClient() {
        return client;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public static class Builder {

        private final String databaseName;

        private String connectionURI;
        private boolean indexes;

        public Builder(@NotNull String databaseName) {
            this.databaseName = databaseName;
        }

        public Builder connectionURI(@NotNull String connectionURI) {
            this.connectionURI = connectionURI;
            return this;
        }

        public Builder ensureIndexes(boolean indexes) {
            this.indexes = indexes;
            return this;
        }

        public MongoDatabase build() {
            return new MongoDatabase(databaseName, connectionURI, indexes);
        }

    }

}
