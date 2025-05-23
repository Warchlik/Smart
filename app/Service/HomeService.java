package app.Service;

import app.Models.Home;

import java.util.ArrayList;
import java.util.List;

public class HomeService {
    private final List<Home> homelist = new ArrayList<>();

    private static final HomeService INSTANCE = new HomeService();

    public static HomeService getInstance() {
        return INSTANCE;
    }

    private HomeService(){}

    public List<Home> getHomelist() {
        return this.homelist;
    }

//    public void addRule(Home rule) {
//        this.homelist.add(rule);
//    }
//
//    public void removeRule(Home rule) {
//        this.homelist.remove(rule);
//    }
}