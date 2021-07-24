package com.zaxxer.hikari.benchmark.pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ConnectionPool implements Connection {
    private final Connection connection;
    private final boolean tmp;
    private boolean closed = false;

    public ConnectionPool(Connection connection, boolean tmp) {
        this.connection = connection;
        this.tmp = tmp;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isTmp() {
        return tmp;
    }

    public boolean isShadowed() {
        return closed;
    }

    @Override
    public String toString() {
        return "ConnectionPool{" + "connection=" + connection + ", tmp=" + tmp + ", closed=" + closed + '}';
    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.isWrapperFor(arg0);
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.unwrap(arg0);
    }

    @Override
    public void abort(Executor arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.abort(arg0);
    }

    @Override
    public void clearWarnings() throws SQLException {
        // TODO Auto-generated method stub
        connection.clearWarnings();
    }

    @Override
    public void commit() throws SQLException {
        // TODO Auto-generated method stub
        connection.commit();
    }

    @Override
    public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return connection.createArrayOf(arg0, arg1);
    }

    @Override
    public Blob createBlob() throws SQLException {
        // TODO Auto-generated method stub
        return connection.createBlob();
    }

    @Override
    public Clob createClob() throws SQLException {
        // TODO Auto-generated method stub
        return connection.createClob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        // TODO Auto-generated method stub
        return connection.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        // TODO Auto-generated method stub
        return connection.createSQLXML();
    }

    @Override
    public Statement createStatement() throws SQLException {
        // TODO Auto-generated method stub
        return connection.createStatement();
    }

    @Override
    public Statement createStatement(int arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return connection.createStatement(arg0, arg1);
    }

    @Override
    public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub
        return connection.createStatement(arg0, arg1, arg2);
    }

    @Override
    public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
        return connection.createStruct(arg0, arg1);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getAutoCommit();
    }

    @Override
    public String getCatalog() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getCatalog();
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getClientInfo();
    }

    @Override
    public String getClientInfo(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.getClientInfo(arg0);
    }

    @Override
    public int getHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getHoldability();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getMetaData();
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getNetworkTimeout();
    }

    @Override
    public String getSchema() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getSchema();
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getTransactionIsolation();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getTypeMap();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getWarnings();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        // TODO Auto-generated method stub
        return connection.isReadOnly();
    }

    @Override
    public boolean isValid(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.isValid(arg0);
    }

    @Override
    public String nativeSQL(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.nativeSQL(arg0);
    }

    @Override
    public CallableStatement prepareCall(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareCall(arg0, arg1, arg2);
    }

    @Override
    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareCall(arg0, arg1, arg2, arg3);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareStatement(arg0);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareStatement(arg0, arg1);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareStatement(arg0, arg1, arg2);
    }

    @Override
    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareStatement(arg0, arg1, arg2, arg3);
    }

    @Override
    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.releaseSavepoint(arg0);
    }

    @Override
    public void rollback() throws SQLException {
        // TODO Auto-generated method stub
        connection.rollback();
    }

    @Override
    public void rollback(Savepoint arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.rollback(arg0);
    }

    @Override
    public void setAutoCommit(boolean arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setAutoCommit(arg0);
    }

    @Override
    public void setCatalog(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setCatalog(arg0);
    }

    @Override
    public void setClientInfo(Properties arg0) throws SQLClientInfoException {
        // TODO Auto-generated method stub
        connection.setClientInfo(arg0);
    }

    @Override
    public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
        // TODO Auto-generated method stub
        connection.setClientInfo(arg0, arg1);
    }

    @Override
    public void setHoldability(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setHoldability(arg0);
    }

    @Override
    public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        connection.setNetworkTimeout(arg0, arg1);
    }

    @Override
    public void setReadOnly(boolean arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setReadOnly(arg0);
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        // TODO Auto-generated method stub
        return connection.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        return connection.setSavepoint(arg0);
    }

    @Override
    public void setSchema(String arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setSchema(arg0);
    }

    @Override
    public void setTransactionIsolation(int arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setTransactionIsolation(arg0);
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
        // TODO Auto-generated method stub
        connection.setTypeMap(arg0);
    }

    @Override
    public void close() throws SQLException {
        // TODO Auto-generated method stub
        PoolFactory.Instance.pool.release(this);
    }

    @Override
    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    public void shutdown() throws SQLException {
        connection.close();
        this.closed = true;
    }

    public boolean isShutdown() {
        return closed;
    }
}
