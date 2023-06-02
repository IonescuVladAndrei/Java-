package packScoreboard.packComparator;

import java.util.Comparator;
import packScoreboard.PlayerWithOpp;

public class PlayerWithOppComp implements Comparator<PlayerWithOpp> {
    
    @Override
    public int compare(PlayerWithOpp p1, PlayerWithOpp p2) {
        if (p1.getScore() > p2.getScore())
            return -1;
        return 1;
    }
}
