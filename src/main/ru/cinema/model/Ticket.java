package ru.cinema.model;

public class Ticket {
    private int id;
    private int sessionID;
    private int row;
    private int cell;
    private int account_Id;

    public Ticket(int id, int sessionID, int row, int cell, int account_Id) {
        this.id = id;
        this.sessionID = sessionID;
        this.row = row;
        this.cell = cell;
        this.account_Id = account_Id;
    }

    public int getId() {
        return id;
    }

    public int getSessionID() {
        return sessionID;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    public int getAccount_Id() {
        return account_Id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", sessionID=" + sessionID +
                ", row=" + row +
                ", cell=" + cell +
                ", account_Id=" + account_Id +
                '}';
    }
}
