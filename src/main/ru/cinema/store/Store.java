package ru.cinema.store;

import ru.cinema.model.Account;
import ru.cinema.model.Ticket;

public interface Store {

    int addAccount(Account account);

    int addTicket(Ticket ticket);

    Account findAccByEmail(String email);

    Account findAccByPhone(String phone);

}
