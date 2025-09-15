package main;

import bot.Bot;
import sql.DatabaseInitializer;
import sql.DatabaseManager;

import java.sql.*;

public class Main {
    private static final String UNDERLINE = "\u001B[4m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        DatabaseInitializer.init();
        System.out.println();
        try {
            System.out.println(UNDERLINE + "Bot status:" + RESET);
            Bot.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}