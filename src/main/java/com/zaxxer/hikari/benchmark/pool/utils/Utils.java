package com.zaxxer.hikari.benchmark.pool.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class Utils {

    public static String configVar = "SMARTBUILDCONFIG";
    public static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    public static final ObjectMapper jsonMapper = new ObjectMapper();
    public static Map<String, String> toMap(String value) {
        value = value.substring(1, value.length() - 1);
        String[] keyValuePairs = value.split(",");
        Map<String, String> map = new HashMap<>();

        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            map.put(entry[0].trim(), entry[1].trim());
        }
        return map;
    }

    public static String generateStringId(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        final Random RANDOM = new SecureRandom();
        final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static File getFileContent() {
        String varValue = System.getenv(Properties.configVar);
        return new File(varValue);
    }

    public static JsonNode getConfigNode() throws IOException {
        return yamlMapper.readTree(new File("/home/prospeak/Documents/projects/java/HikariCP-benchmark/config.yaml"));
    }

    public static Map<String, Object> responseFactory(Object msg, String event) {
        Map<String, Object> message = new HashMap<>();
        message.put("success", true);
        message.put("message", msg);
        message.put("event", event);
        if (msg instanceof List) {
            message.put("dataType", "list");
        } else if (msg instanceof Map) {
            message.put("dataType", "map");
        } else if (msg instanceof String) {
            message.put("dataType", "string");
        } else {
            message.put("dataType", "object");
        }
        return message;
    }

    public static LocalDate localDateParser(String date) {
        String[] dateArr = date.split("/");
        return LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
    }

    public static String generateSerialNumber() {
        String randomString = generateStringId(20).toUpperCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder(randomString);
        for (int i = 0; i < builder.length(); i++) {
            if (i % 5 == 0 && i != 0)
                builder.insert(i, "-");
        }
        return builder.toString();
    }
}
