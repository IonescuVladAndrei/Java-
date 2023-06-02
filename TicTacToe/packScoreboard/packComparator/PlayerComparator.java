package packScoreboard.packComparator;

import java.util.Comparator;
import packScoreboard.Player;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getScore() > p2.getScore())
            return -1;
        return 1;
    }

}
