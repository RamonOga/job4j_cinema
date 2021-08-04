package ru.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PsqlStore implements Store{
    private final BasicDataSource pool =new BasicDataSource();

    private PsqlStore() {
        Properties prop = new Properties();

        try(BufferedReader bf = new BufferedReader(new FileReader("db.properties"))) {
            prop.load(bf);

        } catch (Exception e) {
            throw new IllegalStateException();
        }
        try {
            Class.forName(prop.getProperty("jdbc.driver"));

        } catch (Exception e) {
            throw new IllegalStateException();
        }
        pool.setDriverClassName(prop.getProperty("jdbc.driver"));
        pool.setUrl(prop.getProperty("jdbc.url"));
        pool.setUsername("jdbc.username");
        pool.setPassword("jdbc.password");
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);

    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public Store instOf() {
        return Lazy.INST;
    }
}
