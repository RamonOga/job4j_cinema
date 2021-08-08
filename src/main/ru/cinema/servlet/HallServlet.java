package ru.cinema.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.cinema.model.Account;
import ru.cinema.model.Ticket;
import ru.cinema.store.PsqlStore;
import ru.cinema.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


public class HallServlet extends HttpServlet {

   private static final List<String> inputs = new CopyOnWriteArrayList<>();

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Store store = PsqlStore.instOf();
        int row = Integer.parseInt(req.getParameter("row"));
        int place = Integer.parseInt(req.getParameter("place"));
        String userName = req.getParameter("username");
        String phone = req.getParameter("phone");
        String email = req.getParameter("phone");
        int id = store.addAccount(new Account(0, userName, phone, email));
        store.addTicket(new Ticket(0,  1, row, place, id));
    /*    String tmp = req.getReader()
                .lines()
                .collect(Collectors
                        .joining(System.lineSeparator()));
        System.out.println(tmp);
        System.out.println("POST");
        inputs.add(tmp.split("=")[1]);*/
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(inputs);
        String input = GSON.fromJson(req.getReader(), String.class);
        System.out.println(input);
        System.out.println("GET");
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(inputs);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
