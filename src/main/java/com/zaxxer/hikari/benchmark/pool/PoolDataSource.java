package com.zaxxer.hikari.benchmark.pool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class PoolDataSource implements DataSource {
  private Logger logger;
  private final BlockingPool pool = PoolFactory.Instance.pool;
  private PrintWriter writer = new PrintWriter(System.err);

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return logger;
  }

  @Override
  public boolean isWrapperFor(Class<?> arg0) throws SQLException {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> arg0) throws SQLException {
    return null;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return pool.getConnection();
  }

  @Override
  public Connection getConnection(String arg0, String arg1) throws SQLException {
    // TODO Auto-generated method stub
    return pool.getConnection();
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return writer;
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 8000;
  }

  @Override
  public void setLogWriter(PrintWriter arg0) throws SQLException {
    writer = arg0;
  }

  @Override
  public void setLoginTimeout(int arg0) throws SQLException {

  }

  public void close() {
    pool.shutdown();
  }

}
