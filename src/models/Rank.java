package models;

public class Rank implements Comparable{

    private String name;
    private int score;

    public Rank(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


    @Override
    public int compareTo(Object o) {
        Rank other = (Rank) o;
        return Integer.compare(score, other.getScore());
    }
}
