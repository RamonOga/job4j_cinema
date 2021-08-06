package ru.cinema.store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("script");

       String s = list.stream().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(s);
    }
}
