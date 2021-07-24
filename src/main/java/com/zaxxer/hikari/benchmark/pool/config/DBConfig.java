package com.zaxxer.hikari.benchmark.pool.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.zaxxer.hikari.benchmark.pool.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public enum DBConfig {
    Instance;
    private final Logger logger = LoggerFactory.getLogger(DBConfig.class.getName());
    public String HOST;
    public String USER;
    public String PASS;
    public String DRIVER;
    public int min;
    public int max;

    DBConfig() {
        try {
            JsonNode dbNode = Utils.getConfigNode();
            JsonNode db = dbNode.get("db");
            JsonNode jsonNode = db.get("info");
            HOST = jsonNode.get("url").asText();
            USER = jsonNode.get("user").asText();
            PASS = jsonNode.get("pass").asText();
            DRIVER = db.get("driverClass").asText();
            min = db.get("min").asInt();
            max = db.get("max").asInt();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
