package models;

import javafx.collections.FXCollections;

import java.sql.*;
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

    }

    public static void addRankingPoints(String playerName, int score){
        try {
            db = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7392068", "sql7392068", "x7BRGYx8we");
        } catch (SQLException throwables) {
            System.out.println("Can't connect to database\n" + throwables.getMessage());
        }
        try {
            PreparedStatement ps = db.prepareStatement("insert into ranking value (?, ?)");
            ps.setString(1, playerName);
            ps.setInt(2, score);
            ps.executeUpdate();
            db.close();
        } catch (SQLException throwables) {
            System.out.println("Can not insert values into ranking\n" + throwables.getMessage());
        }


    }

    public static List<Rank> getRank() {
        List<Rank> rank = FXCollections.observableArrayList();
        try {
            db = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7392068", "sql7392068", "x7BRGYx8we");
        } catch (SQLException throwables) {
            System.out.println("Can't connect to database\n" + throwables.getMessage());
        }
        try {
            ResultSet resultSet = db.createStatement().executeQuery("select * from ranking order by score desc limit 10");
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");
                Rank rank1 = new Rank(name, score);
                rank.add(rank1);

            }
            db.close();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
//        Collections.sort(rank, Collections.reverseOrder());
        for (Rank r:
             rank) {
            System.out.println(r);
        }
        return rank;
    }

    public Ranking getInstance(){
        return ranking;
    }

//    public static void closeRanking(){
//        try {
//            db.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

}
