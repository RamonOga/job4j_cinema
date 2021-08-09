package ru.cinema.store;

import ru.cinema.model.Account;
import ru.cinema.model.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        Store psqlStore = PsqlStore.instOf();
    }
}
