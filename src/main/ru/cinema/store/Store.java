package ru.cinema.store;

import ru.cinema.model.Account;
import ru.cinema.model.Ticket;

import java.util.List;

public interface Store {

    int addAccount(Account account);

    int addTicket(Ticket ticket);

    Account findAccByEmail(String email);

    Account findAccByPhone(String phone);

    List<Ticket> findAllTickets();

    List<Integer> getTakenList();

}
