package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Ranking {

    public Ranking ranking = new Ranking();
    private ArrayList<ArrayList<Object>> rank;
    private Connection db;

    private Ranking(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver");
        }

        try {
            db = DriverManager.getConnection("jdbc:mysql://localhost/dioretsa", "root", "");
        } catch (SQLException throwables) {
            System.out.println("Can't connect to database");
        }
        rank = new ArrayList<>();
    }

    public int addRankingPoints(String playerName, int score){
        try {
            return db.createStatement().executeUpdate("insert into ranking value(playerName, score)");
        } catch (SQLException throwables) {
            System.out.println("Can not insert values into ranking");
        }
        return -1;
    }

    public Ranking getInstance(){
        return ranking;
    }

}
