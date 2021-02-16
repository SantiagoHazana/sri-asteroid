package models;

public class Rank implements Comparable{

    private String name;
    private int score;
    private String dateTime;

    public Rank(String name, int score, String dateTime) {
        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDateTime(){
        return dateTime;
    }


    @Override
    public int compareTo(Object o) {
        Rank other = (Rank) o;
        return Integer.compare(score, other.getScore());
    }

    @Override
    public String toString() {
        return String.format("%s: %d - %s", name, score, dateTime);
    }
}
