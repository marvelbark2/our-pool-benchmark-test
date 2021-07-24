package com.zaxxer.hikari.benchmark.pool;

import com.zaxxer.hikari.benchmark.pool.config.DBConfig;

import org.openjdk.jmh.runner.RunnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.*;

public class ConnectionPoolManager extends AbstractPool implements BlockingPool {
    private final Logger logger = LoggerFactory.getLogger(ConnectionPoolManager.class.getName());
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private BlockingQueue<ConnectionPool> mountedConnection;
    private volatile boolean shutdownPool;
    private boolean isReturned = true;
    private int maxConnection;
    private int currentConnection = 0;

    public ConnectionPoolManager(int nPool, int maxConnection) {
        shutdownPool = false;
        logger.info("Pool Init...ted");
        this.maxConnection = maxConnection;
        mountedConnection = new LinkedBlockingQueue<>(nPool);
        this.init(nPool);
    }

    private void init(int nPool) {

        for (int i = 0; i < nPool; i++) {
            ConnectionPool connection = connectionFactory(false);
            mountedConnection.add(connection);
        }
        logger.info("Pool connections : {}", mountedConnection.size());
    }

    private ConnectionPool connectionFactory(Boolean tmp) {
        Connection connection = null;
        DBConfig config = DBConfig.Instance;
        String url = config.HOST;
        String user = config.USER;
        String pass = config.PASS;
        try {
            DriverManager.setLoginTimeout(4000);
            connection = DriverManager.getConnection(url, user, pass);
            ++currentConnection;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        }

        return new ConnectionPool(connection, tmp);
    }

    @Override
    protected void handleInvalidReturn(ConnectionPool con) {
        throw new IllegalStateException("Error! Cannot put this Connection: " + con + " to the pool");
    }

    @Override
    protected void returnToPool(ConnectionPool connection) {
        if (isValid(connection))
            executor.submit(new ObjectReturner(mountedConnection, connection));
    }

    @Override
    protected boolean isValid(ConnectionPool connection) {
        try {
            if (connection == null || connection.isClosed())
                return false;
            else {
                Connection con = connection.getConnection();
                if (con == null) {
                    return false;
                } else
                    return !con.isClosed();
            }
        } catch (SQLException throwables) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public ConnectionPool getConnection(long time, TimeUnit unit) {
        if (!shutdownPool) {
            ConnectionPool connectionPool = null;
            try {
                 connectionPool = mountedConnection.poll(time, unit);
                if(!isValid(connectionPool)) connectionPool = connectionFactory(true);
            } catch (Exception ie) {
                logger.error(ie.getMessage(), ie);
            }
            return connectionPool;

        } else {
            throw new IllegalStateException("Pool is shutdown");
        }
    }

    private ConnectionPool getConnection(ConnectionPool connectionPool) {
        if (!isValid(connectionPool)) {
            mountedConnection.remove(connectionPool);
            connectionPool = connectionFactory(false);
        }
        return connectionPool;
    }

    @Override
    public int poolSize() {
        return mountedConnection.size();
    }

    @Override
    public Connection getConnection() {
        if (!shutdownPool) {
            return this.getConnection(500, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalStateException("Pool is shutdown");
        }
    }

    @Override
    public void isReturnedTo(Boolean v) {
        this.isReturned = v;
    }

    @Override
    public void shutdown() {
        logger.info("Closing the pool");
        shutdownPool = true;
        executor.shutdownNow();
        closeConnections();
    }

    private void closeConnections() {
        mountedConnection.forEach(connection -> {
            try {
                connection.shutdown();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private class ObjectReturner implements Runnable {
        private final BlockingQueue<ConnectionPool> queue;
        private final ConnectionPool connection;

        ObjectReturner(BlockingQueue<ConnectionPool> queue, ConnectionPool connection) {
            this.queue = queue;
            this.connection = connection;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (isReturned && !connection.isTmp() && !connection.isClosed()) {
                        queue.put(connection);
                        logger.info("Connection returned to pool  {}", connection);
                    } else {
                        connection.shutdown();
                        logger.info("Connection closed: {}", connection);
                    }
                    --currentConnection;
                    logger.info("Recalcul pool size {}, size fun : {}", mountedConnection.size(), queue.size());
                    break;
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }

            }
        }
    }
}
