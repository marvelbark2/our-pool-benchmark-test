package com.zaxxer.hikari.benchmark.pool;

import java.util.concurrent.TimeUnit;

public interface BlockingPool extends Pool {
    ConnectionPool getConnection(long time, TimeUnit unit);
    int poolSize();
}
