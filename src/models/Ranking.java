package models;

import java.sql.*;
import java.util.ArrayList;

public class Ranking {

    public static Ranking ranking = new Ranking();

    private Ranking(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver");
        }
    }

    public static void addRankingPoints(String playerName, int score){
        Connection db = null;

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

    public static ArrayList<ArrayList<Object>> getRank() {
        Connection db = null;
        ArrayList<ArrayList<Object>> rank = new ArrayList<>();
        try {
            db = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7392068", "sql7392068", "x7BRGYx8we");
        } catch (SQLException throwables) {
            System.out.println("Can't connect to database\n" + throwables.getMessage());

        }

        try {
            ResultSet resultSet = db.createStatement().executeQuery("select * from ranking");
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");
                ArrayList<Object> oneScore = new ArrayList<>();
                oneScore.add(name);
                oneScore.add(score);
                rank.add(oneScore);
                db.close();
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return rank;
    }

    public static Ranking getInstance(){
        return ranking;
    }

}
