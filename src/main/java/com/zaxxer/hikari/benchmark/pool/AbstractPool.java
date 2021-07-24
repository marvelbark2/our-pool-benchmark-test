package com.zaxxer.hikari.benchmark.pool;

public abstract class AbstractPool implements Pool {

    @Override
    public void release(ConnectionPool connection) {
        if (isValid(connection)) {
            returnToPool(connection);
        } else {
            handleInvalidReturn(connection);
        }
    }


    protected abstract void handleInvalidReturn(ConnectionPool t);

    protected abstract void returnToPool(ConnectionPool t);

    protected abstract boolean isValid(ConnectionPool t);
}
