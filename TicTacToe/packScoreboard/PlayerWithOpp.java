package packScoreboard;

public class PlayerWithOpp  extends Player{
    private String opp;

    public PlayerWithOpp() {
        super();
        this.opp = null;
    }

    public PlayerWithOpp(String name, String opp, int score) {
        super(name, score);
        this.opp = opp;
    }

    protected void setOpp(String opp){
        this.opp = opp;
    }

    public String getOpp(){
        return this.opp;
    }
}
