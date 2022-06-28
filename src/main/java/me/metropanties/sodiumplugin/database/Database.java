package me.metropanties.sodiumplugin.database;

import java.io.Closeable;

public interface Database extends Closeable {

    void connect();

}
