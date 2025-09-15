package data;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    private final String id;
    private ArrayList<String> names;
    private String rank;
    private final LocalDateTime firstResignation;
    private ArrayList<Registrations> registrations;
    public User(String id, ArrayList<String> names, String rank, LocalDateTime firstResignation, ArrayList<Registrations> registrations) {
        this.id = id;
        this.names = names;
        this.rank = rank;
        this.firstResignation = firstResignation;
        this.registrations = registrations;
    }
}
