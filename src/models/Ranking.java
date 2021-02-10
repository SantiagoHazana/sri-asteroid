package models;

import javafx.collections.FXCollections;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class Ranking {

    public static Ranking ranking = new Ranking();
    public static Connection db;

    private Ranking(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver");
        }
        try {
            db = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7392068", "sql7392068", "x7BRGYx8we");
        } catch (SQLException throwables) {
            System.out.println("Can't connect to database\n" + throwables.getMessage());
        }
    }

    public static void addRankingPoints(String playerName, int score){
        try {
            PreparedStatement ps = db.prepareStatement("insert into ranking value (?, ?)");
            ps.setString(1, playerName);
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Can not insert values into ranking\n" + throwables.getMessage());
        }


    }

    public static List<Rank> getRank() {
        List<Rank> rank = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = db.createStatement().executeQuery("select * from ranking");
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");
                Rank rank1 = new Rank(name, score);
                rank.add(rank1);

            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        Collections.sort(rank, Collections.reverseOrder());
        return rank;
    }

    public static Ranking getInstance(){
        return ranking;
    }

    public static void closeRanking(){
        try {
            db.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
