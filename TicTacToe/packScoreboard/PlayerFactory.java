package packScoreboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Set;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import packScoreboard.packComparator.*;

public class PlayerFactory {

    private static final HashMap<String, Object> playerMap = new HashMap<String, Object>();
    private String filePath;

    public PlayerFactory() {
        // finding the file path or if the file doesnt't exist, we create one
        boolean fileIsNew = false;
        Path path = Paths.get("scoreboardData/data.txt");
        if (!Files.exists(path)) {
            System.out.println("Couldn't find the previous scoreboard file so I'll create one.");
            Path newFilePath = Paths.get(path.toAbsolutePath().toString());
            fileIsNew = true;
            try {
                Files.createFile(newFilePath);
            } catch (Exception e) {
                System.out.println("Error: couldn't create the scoreboard file!");
                System.out.println(e);
            }
        }
        this.filePath = path.toAbsolutePath().toString();
        if (!fileIsNew)
            try {
                Scanner myReader = new Scanner(path.toAbsolutePath());
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    // initializing the hashmap with previous scores if there are any
                    // by counting the nr of spaces,
                    if (data.chars().filter(c -> c == (int) ' ').count() == 1) {
                        // one space so we have a Player object
                        String[] arrOfStr = data.split(" ", 2);
                        Player player = new Player(arrOfStr[0], Integer.parseInt(arrOfStr[1]));
                        playerMap.put(arrOfStr[0], player);
                        //for debugging
                        //System.out.println("Obj saved with: Name: " + player.getName() + " Score: " + player.getScore());
                    } else if (data.chars().filter(c -> c == (int) ' ').count() == 2) {
                        // two spaces so we have a PlayerWithOpp object
                        String[] arrOfStr = data.split(" ", 3);
                        PlayerWithOpp player = new PlayerWithOpp(arrOfStr[0], arrOfStr[1], Integer.parseInt(arrOfStr[2]));
                        playerMap.put(arrOfStr[0].concat(arrOfStr[1]), player);
                        //for debugging
                        //System.out.println("Obj saved with: Name: " + player.getName() + " Opponent: " + player.getOpp() + " Score: " + player.getScore());
                    } else
                        System.out.println("One record wasn't taken into consideration because of too many spaces");
                }
                myReader.close();
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
    }

    // printing the elements that are part of the hashmap as well as sorting them
    // descending by score
    // sorting takes place only in the linked lists, the hashmap remains unchanged
    public void printScoreBoard() {
        Set<String> keys = playerMap.keySet();
        List<Player> playerList = new LinkedList<Player>();
        PlayerComparator playerComparator = new PlayerComparator();
        List<PlayerWithOpp> playerWithOppList = new LinkedList<PlayerWithOpp>();
        PlayerWithOppComp playerWithOppComparator = new PlayerWithOppComp();
        for (String key : keys) {
            if (playerMap.get(key) instanceof Player && !(playerMap.get(key) instanceof PlayerWithOpp))
                playerList.add((Player) PlayerFactory.playerMap.get(key));
            if (playerMap.get(key) instanceof PlayerWithOpp)
                playerWithOppList.add((PlayerWithOpp) PlayerFactory.playerMap.get(key));
        }
        if (playerList.size() > 1)
            playerList.sort(playerComparator);
        if (playerWithOppList.size() > 1)
            playerWithOppList.sort(playerWithOppComparator);
        System.out.println("\n\n\n\tScoreboard\n\n-----Top players by xp-----\n");
        for (Player player : playerList)
            System.out.println(" " + player.getName() + "  " + player.getScore());
        System.out.println("\n-----Top players by xp against opponents-----\n");
        for (PlayerWithOpp playerWithOpp : playerWithOppList)
            System.out.println(" " + playerWithOpp.getName() + "  " + playerWithOpp.getOpp() + "  " + playerWithOpp.getScore());
    }

    public void saveScoreBoard() {
        Set<String> keys = playerMap.keySet();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
            for (String key : keys) {
                if (playerMap.get(key) instanceof Player && !(playerMap.get(key) instanceof PlayerWithOpp))
                    writer.write(((Player) playerMap.get(key)).getName().concat(" ").concat(Integer.toString(((Player) playerMap.get(key)).getScore())) + "\n");
                else if (playerMap.get(key) instanceof PlayerWithOpp)
                    writer.write(((PlayerWithOpp) playerMap.get(key)).getName().concat(" ").concat(((PlayerWithOpp) playerMap.get(key)).getOpp()).concat(" ").concat(Integer.toString(((Player) playerMap.get(key)).getScore())) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: couldn't save the scoreboard");
            System.out.println(e);
        }
    }

    // if the player exists, then we update their score. Otherwise, a new player
    // gets added
    public void addPlayerToScoreBoard(String name, int score) {
        Player player = (Player) playerMap.get(name);
        if (player == null) {
            player = new Player(name, score);
            playerMap.put(name, player);
        } else {
            player.setScore(player.getScore() + score);
            playerMap.put(name, player);
        }
    }

    public void addPlayerWithOppToScoreBoard(String name, String oppName, int score) {
        String id = name.concat(oppName);
        System.out.println("ID: " + id);
        PlayerWithOpp player = (PlayerWithOpp) playerMap.get(id);
        if (player == null) {
            player = new PlayerWithOpp(name, oppName, score);
            playerMap.put(id, player);
        } else {
            player.setScore(player.getScore() + score);
            playerMap.put(id, player);
        }
    }
}
