package com.zaxxer.hikari.benchmark.pool;

import com.zaxxer.hikari.benchmark.pool.config.DBConfig;

public enum PoolFactory {
    Instance;

    public BlockingPool pool;
    boolean isNotReturnable;
    PoolFactory() {
        pool = new ConnectionPoolManager(DBConfig.Instance.min, DBConfig.Instance.max);
    }

    public boolean isNotReturnable() {
        return isNotReturnable;
    }

    public void setNotReturnable(boolean notReturnable) {
        isNotReturnable = notReturnable;
    }

}
