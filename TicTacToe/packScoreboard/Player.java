package packScoreboard;

public class Player {
    protected String name;
    protected int score;

    public Player(){
        this.name = null;
        this.score = 0;
    }

    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }

    protected void setName(String name){
        this.name = name;
    }

    protected void setScore(int score){
        this.score = score;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }
}
