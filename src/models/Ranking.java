package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ranking {

    public Ranking ranking = new Ranking();
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
    }

    public int addRankingPoints(String playerName, int score){
        
    }

    public Ranking getInstance(){
        return ranking;
    }

}
