package ru.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.cinema.model.Account;
import ru.cinema.model.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

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

    @Override
    public Account findAccByEmail(String email) {
        Account account = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from account where email = (?)")
        ) {
            ps.setString(1, email);
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    account = new Account(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone")
                            );
                }
            }
         } catch (SQLException sqle) {

        }
        return account;
    }

    @Override
    public Account findAccByPhone(String phone) {
        Account account = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from account where phone = (?)")
        ) {
            ps.setString(1, phone);
            ps.execute();
            try (ResultSet rs = ps.getResultSet()) {
                if (rs.next()) {
                    account = new Account(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }
        } catch (SQLException sqle) {

        }
        return account;
    }

    @Override
    public List<Ticket> findAllTickets() {
        List<Ticket> tickets = new CopyOnWriteArrayList<>();
        try(Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from ticket")
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs.getInt("id"),
                            rs.getInt("session_id"),
                            rs.getInt("row"),
                            rs.getInt("cell"),
                            rs.getInt("account_id")
                    ));
                }
            }
        } catch (SQLException sqle) {

        }
        return tickets;
    }

    @Override
    public List<Integer> getTakenList() {
        List<Integer> noEmpty = new CopyOnWriteArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(Ticket ticket : PsqlStore.instOf().findAllTickets()) {
            sb.append(ticket.getRow());
            sb.append(ticket.getCell());
            noEmpty.add(Integer.parseInt(sb.toString()));
            sb.delete(0, sb.length());
        }
        return noEmpty;
    }
}
