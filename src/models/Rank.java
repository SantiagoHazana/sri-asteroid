package models;

public class Rank implements Comparable{

    // *************************
    // Attributes
    // *************************

    private String name;
    private int score;
    private String dateTime;

    // *************************
    // Constructors
    // *************************

    /**
     * Constructs a new ranking with the given values
     * @param name name of the player
     * @param score score of the player
     * @param dateTime date when the player achieved the score
     */

    public Rank(String name, int score, String dateTime) {
        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }

    // *************************
    // Public methods
    // *************************

    /**
     * Returns the player name
     * @return player name
     */

    public String getName() {
        return name;
    }

    /**
     * Returns the player score
     * @return player score
     */

    public int getScore() {
        return score;
    }

    /**
     * Returns the date and time when the player achieved the score
     * @return date and time
     */

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
