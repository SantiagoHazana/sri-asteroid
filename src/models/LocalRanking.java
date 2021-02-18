package models;

import javafx.collections.FXCollections;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LocalRanking {

    public static LocalRanking instance = new LocalRanking();
    private static String fileLocation;

    private LocalRanking(){
        fileLocation = "rank.txt";
    }

    public static void addToRank(String playerName, int score){
        File file = new File(fileLocation);
        try {
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String line = String.format("%s\t%d\t%s", playerName, score, currentTime);
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Rank> getRank(){
        List<Rank> rank = FXCollections.observableArrayList();
        File file = new File(fileLocation);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea = bufferedReader.readLine();
            while (linea != null){
                String[] values = linea.split("\t");
                Rank oneRank = new Rank(values[0], Integer.parseInt(values[1]), values[2]);
                rank.add(oneRank);
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(rank, Collections.reverseOrder());
        rank.forEach(System.out::println);
        return rank;
    }

}
