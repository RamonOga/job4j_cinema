package ru.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.cinema.model.Account;
import ru.cinema.model.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class PsqlStore implements Store{
    private final BasicDataSource pool =new BasicDataSource();

    private PsqlStore() {
        Properties prop = new Properties();

        try(BufferedReader bf = new BufferedReader(new FileReader("recources/db.properties"))) {
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
        pool.setUsername(prop.getProperty("jdbc.username"));
        pool.setPassword(prop.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);

    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public int addAccount(Account account) {
        int id = -1;
        try(Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into account (username, email, phone) values (?, ?, ?)"
                                                                    , PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, account.getUserName());
            ps.setString(2,account.getPhone());
            ps.setString(3,account.getEmail());
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (Exception e) {
        e.printStackTrace();
        }
    return id;
    }

    @Override
    public int addTicket(Ticket ticket) {
        int id = -1;
        try(Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into ticket (session_id, row, cell, account_id) values (?, ?, ?, ?)"
                    , PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getSessionID());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getAccount_Id());
            ps.execute();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
