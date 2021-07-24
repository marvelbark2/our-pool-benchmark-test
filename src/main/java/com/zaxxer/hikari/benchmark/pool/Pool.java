package com.zaxxer.hikari.benchmark.pool;

import java.sql.Connection;

public interface Pool {
    // Asking for connection object if pool is empty wait until it will has a
    // connection
    Connection getConnection();

    // Return pbject to the pool
    void release(ConnectionPool connection);

    // Is connections returnable to pool
    void isReturnedTo(Boolean v);

    // Close all connections on the pool
    void shutdown();

}
